package Jun.model

import Jun.MainApp
import scala.collection.mutable.ListBuffer
import scalafx.scene.image.Image
import scala.util.Random

class EnemySpawner(
    private var _enemyListB : ListBuffer[Enemy]
) extends Runnable{

    var waves = 1
    val random = new Random()
    val Max_X = MainApp.stage.getWidth.toInt
    val Max_Y = MainApp.stage.getHeight.toInt

    override def run(): Unit = {
        println("Spawning enemies: Wave : " + waves)
        //If less than 1 enemy remaining
        while(_enemyListB.size < 1){
            //Spawn between 2 to 6 enemies
            val maxSpawn = random.nextInt(4) + 2
            for(i <- 1 to maxSpawn){
                val enemyShip = new Image(getClass.getResourceAsStream("/Images/enemy_ship_1.png"))
                val enemySprite = new Sprite(enemyShip, 0, 0, 0, 0, enemyShip.getWidth(), enemyShip.getHeight())
                val enemy = new Enemy(100 * waves, 10 * waves, enemySprite, 200.0, _enemyListB)
                //X : Between 100 to (1600-200+100) || 100 - 1700
                //Y : Between 100 to (900*0.4+100) || 100 - 460
                enemy.sprite.positionX = random.nextInt(Max_X - 200) + 100
                enemy.sprite.positionY = random.nextInt((Max_Y * 0.4).toInt) + 100
                _enemyListB += enemy
                println("Enemies spawned: " + i)
            }
            waves += 1
            
            try{
                Thread.sleep(10000)
            } catch {
                case e : InterruptedException => e.printStackTrace()
                case _ : Throwable => println("Unexpected error when spawning enemies")
            }
        }
    }
  
}