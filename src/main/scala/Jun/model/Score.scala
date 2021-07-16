package Jun.model

import scalafx.beans.property.{StringProperty, IntegerProperty, ObjectProperty}
import java.time.LocalDate
import Jun.util.Database
import Jun.util.DateUtil._
import scalikejdbc._
import scalafx.collections.ObservableBuffer
import scala.util.{ Try, Success, Failure }

class Score (val nameS : String, val scoreI : Int) extends Database {
	def this()  = this(null, 0)
	var id      = ObjectProperty[Long](-1)
	var name    = new StringProperty(nameS)
    var score   = IntegerProperty(scoreI)
	var date    = ObjectProperty[LocalDate](LocalDate.of(2021, 1, 1))


	def save() : Try[Long] = {
		if (!isExist) {
			Try(DB autoCommit { implicit session => 
				id.value = sql"""
					insert into score (name, score, date) values 
						(${name.value}, ${score.value}, ${date.value.asString})
				""".updateAndReturnGeneratedKey.apply()
				id.value
			})
		} else {
			Try(DB autoCommit { implicit session => 
				sql"""
				update score 
				set 
				name  = ${name.value} ,
				score = ${score.value},
				date  = ${date.value.asString}
				where id = ${id.value} 
				""".update.apply().toLong
			})
		}
			
	}
	def delete() : Try[Int] = {
		if (isExist) {
			Try(DB autoCommit { implicit session => 
			sql"""
				delete from score where  
					id = ${id.value}
				""".update.apply()
			})
		} else 
			throw new Exception("This score does not exist in the database")		
	}

	def isExist : Boolean =  {
		DB readOnly { implicit session =>
			sql"""
				select * from score where 
				id = ${id.value}
			""".map(rs => rs.string("name")).single.apply()
		} match {
			case Some(_) => true
			case None => false
		}

	}
}

object Score extends Database{
    val scoreData = new ObservableBuffer[Score]()
    def apply (
		_id: Long,
		nameS : String, 
        scoreI : Int,
		dateS : String
		) : Score = {

		new Score(nameS, scoreI) {
			id.value    = _id
			date.value  = dateS.parseLocalDate
		}
		
	}
	def initializeTable() = {
		DB autoCommit { implicit session => 
			sql"""
			create table score (
			  id int not null GENERATED ALWAYS AS IDENTITY (START WITH 1, INCREMENT BY 1), 
			  name varchar(64), 
              score int,
			  date varchar(64)
			)
			""".execute.apply()
		}
	}
	
	def AllScores : List[Score] = {
		DB readOnly { implicit session =>
			sql"select * from score".map(rs => Score(rs.long("id"),rs.string("name"),rs.int("score"),rs.string("date"))).list.apply()
		}
	}
}
