package Jun.model

import scala.collection.mutable.ListBuffer
import org.apache.derby.impl.tools.sysinfo.Main
import Jun.MainApp
import java.util.Timer
import java.util.TimerTask
import scalafx.scene.image.Image

class Enemy(
    var _health : Int, 
    var _damage : Int, 
    var _sprite : Sprite,
    var _speed  : Double) 
    extends Spaceship (_health, _damage, _sprite, _speed){
    
    private var _exp : Int = 25
    private var _atkSpeed : Double = 1.2
    private val _enemyShootTimer = new Timer()

    //Accessor
    def exp = _exp  
    def atkSpeed = _atkSpeed
    def enemyShootTimer = _enemyShootTimer

    //Mutator
    def exp_=(newExp : Int){
        _exp = newExp
    }
    def atkSpeed_=(newAtkSpeed : Double){
        _atkSpeed = newAtkSpeed
    }

    //Functions
    override def death() {
        _enemyShootTimer.cancel()
        MainApp.player.getEXP(_exp)
        MainApp.enemyListB -= this 
    }

    def shoot() = {
        //A TimerTask that repeats to continuously shoot
        val shootTask = new TimerTask{
            override def run(): Unit = {
                //Laser sprites
                val atkImg = new Image(getClass.getResourceAsStream("/Images/small_laser_red.png"))
                val atkSprite = new Sprite(atkImg, 0, 0, 0, 0, atkImg.getWidth(), atkImg.getHeight())
                val laser_ = new Laser(atkSprite, _damage, false)
                laser_.sprite.velocityX = 0 
                laser_.sprite.velocityY = 700 
                laser_.sprite.positionX = _sprite.positionX + (_sprite.width / 2)  //Center laser horizontally on enemy sprite
                laser_.sprite.positionY = _sprite.positionY + _sprite.height       //Slight offset to be a bit lower than the enemy sprite
                MainApp.laserListB += laser_
            }
        }
        enemyShootTimer.scheduleAtFixedRate(shootTask, 0, (1000 / _atkSpeed).toLong)
    }      

    
          
}