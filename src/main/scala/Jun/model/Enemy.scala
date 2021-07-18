package Jun.model

import scala.collection.mutable.ListBuffer
import org.apache.derby.impl.tools.sysinfo.Main
import java.util.Timer
import java.util.TimerTask
import scalafx.scene.image.Image
import scala.util.Random
import Jun.MainApp

class Enemy(
    var health_ : Int, 
    var damage_ : Int, 
    var sprite_ : Sprite,
    var speed_ : Double,
    var _exp : Int) 
    extends Spaceship (health_, damage_, sprite_, speed_){
    
    private var _atkSpeed : Double = 1.2
    private var _enemyTimer = new Timer()
    private var dead = false

    //Accessor
    def exp = _exp  
    def atkSpeed = _atkSpeed
    def enemyTimer = _enemyTimer

    //Mutator
    def exp_=(newExp : Int){
        _exp = newExp
    }
    def atkSpeed_=(newAtkSpeed : Double){
        _atkSpeed = newAtkSpeed
    }
    def enemyTimer_=(newTimer : Timer){
        _enemyTimer = newTimer
    }

    //Functions
    override def death() {
        dead = true
        _enemyTimer.cancel()
        MainApp.player.getEXP(_exp)
        MainApp.enemyListB -= this 
    }

    def shoot() = {
        //A TimerTask that repeats to continuously shoot
        val shootTask = new TimerTask{
            override def run(): Unit = {
                if(!dead){
                    //Laser sprites
                    val atkImg = new Image(getClass.getResourceAsStream("/images/small_laser_red.png"))
                    val atkSprite = new Sprite(atkImg, 0, 0, 0, 0, atkImg.getWidth(), atkImg.getHeight())
                    val laser_ = new Laser(atkSprite, damage, false)
                    laser_.sprite.velocityX = 0 
                    laser_.sprite.velocityY = 700 
                    laser_.sprite.positionX = sprite.positionX + (sprite.width / 2)  //Center laser horizontally on enemy sprite
                    laser_.sprite.positionY = sprite.positionY + sprite.height       //Slight offset to be a bit lower than the enemy sprite
                    MainApp.laserListB += laser_
                }
                else{
                    this.cancel
                }
            }
        }
        
        enemyTimer.scheduleAtFixedRate(shootTask, 0, (1000 / _atkSpeed).toLong)
    }      

    /**
     * This will make the enemy move slightly towards the player, with a bit of randomness
    */
    def chase(){
        val chaseTask = new TimerTask{
            override def run(): Unit = {
                if(!dead){
                    val player  = MainApp.player
                    val playerX = player.sprite.positionX
                    val random  = new Random()
                    //Random number from -100 to 100
                    val randomNum = random.nextInt(200) - 100

                    //Resetting velocity
                    sprite.velocityX = 0
                    sprite.velocityY = 0
                
                    //Target X to move to, based on player's X + a random amount
                    val maxX = MainApp.stage.getWidth
                    val dX = playerX + randomNum
                    //If within acceptable X limits
                    if(dX < maxX || dX > 0){
                        if(dX < sprite.positionX){
                            sprite.velocityX = -1 * speed
                        }
                        else{
                            sprite.velocityX = 1 * speed
                        }
                    }
                
                    //Target Y to move to
                    val dY = randomNum + sprite.positionY
                    //40% of stage height is maximum
                    val maxY = MainApp.stage.getHeight * 0.4
                    //If within acceptable Y limits
                    if(dY > 0){
                        if(dY < sprite.positionY || sprite.positionY > maxY){
                            sprite.velocityY = -1 * speed
                        }
                        else{
                            sprite.velocityY = 1 * speed
                        }
                    }
                }
                else{
                    this.cancel
                }
            }
        }
        //Only do once per second to avoid a "vibrating effect" compared to running it 60 times a sec xD
        enemyTimer.scheduleAtFixedRate(chaseTask, 0, 1000)
        
    }

    
          
}