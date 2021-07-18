package Jun.model

import scalafx.scene.image.Image
import Jun.MainApp

class Player(
    var health_ : Int, 
    var damage_ : Int, 
    val sprite_ : Sprite,
    var speed_  : Double) 
    extends Spaceship (health_, damage_, sprite_, speed_){
    
    //Initializing default variables
    private var _atkSpeed    : Double = 1.5
    private var _maxHealth   : Int    = 200
    private var _level       : Int    = 1
    private var _exp         : Int    = 0
    private var _LevelUpEXP  : Int    = 100
    private var _name        : String = "TestName"
    private var dead         : Boolean= false

    //Accessor
    def maxHealth   = _maxHealth 
    def atkSpeed    = _atkSpeed
    def exp         = _exp
    def LevelUpEXP  = _LevelUpEXP
    def level       = _level
    def name        = _name

    //Mutator
    def maxHealth_=(newMaxHealth : Int){
        _maxHealth = newMaxHealth
    }
    def atkSpeed_=(newAtkSpeed : Double){
        _atkSpeed = newAtkSpeed
    }
    def name_=(newName : String){
        _name = newName
    }

    //Functions
    def getEXP(exp : Int){
        _exp += exp
        //While instead of if in the event that the player needs to level up twice, eg: >=200 EXP
        while(_exp >= _LevelUpEXP){
            levelUp()
        }
    }

    def heal(healInt : Int){
        health += healInt
        if(health > _maxHealth){
            health = _maxHealth
        }
        println("Player health: " + health)
    }

    def levelUp(){
        _exp -= _LevelUpEXP
        _level += 1
        _LevelUpEXP += 50
        //Pop up level up 
        MainApp.showLevelUpDialog(this)
    }

    def shoot() = {
        //Laser sprites
        val atkImg = new Image(getClass.getResourceAsStream("/images/small_laser_blue.png"))
        val atkSprite = new Sprite(atkImg, 0, 0, 0, 0, atkImg.getWidth(), atkImg.getHeight())
        val laser_ = new Laser(atkSprite, damage, true)
        laser_.sprite.velocityX = 0 
        laser_.sprite.velocityY = -900 
        laser_.sprite.positionX = sprite.positionX + (sprite.width / 2)  //Center laser horizontally on player sprite
        laser_.sprite.positionY = sprite.positionY - 10                  //Slight offset to be a bit higher than the player sprite
        laser_
    }

    override def death(){
        if(!dead){
            dead = true
            println("You ded :(")
            MainApp.endGame
            MainApp.showEnd
        }
        
    }



    

}