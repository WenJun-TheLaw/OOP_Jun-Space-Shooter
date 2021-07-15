package Jun.model

import scalafx.scene.image.Image
import Jun.MainApp

class Player(
    var _health : Int, 
    var _damage : Int, 
    val _sprite : Sprite,
    var _speed  : Double) 
    extends Spaceship (_health, _damage, _sprite, _speed){
    
    //Initializing default variables
    private var _atkSpeed    : Double = 1.2
    private var _maxHealth   : Int    = 100
    private var _level       : Int    = 1
    private var _exp         : Int    = 0
    private var _LevelUpEXP  : Int    = 100

    //Accessor
    def maxHealth   = _maxHealth 
    def atkSpeed    = _atkSpeed
    def exp         = _exp
    def LevelUpEXP  = _LevelUpEXP
    def level       = _level

    //Mutator
    def maxHealth_=(newMaxHealth : Int){
        _maxHealth = newMaxHealth
    }
    def atkSpeed_=(newAtkSpeed : Double){
        _atkSpeed = newAtkSpeed
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
        heal(100)
        //Pop up level up 
        MainApp.showLevelUpDialog(this)
    }

    def shoot() = {
        //Laser sprites
        val atkImg = new Image(getClass.getResourceAsStream("/Images/small_laser_blue.png"))
        val atkSprite = new Sprite(atkImg, 0, 0, 0, 0, atkImg.getWidth(), atkImg.getHeight())
        val laser_ = new Laser(atkSprite, _damage, true)
        laser_.sprite.velocityX = 0 
        laser_.sprite.velocityY = -700 
        laser_.sprite.positionX = _sprite.positionX + (_sprite.width / 2)  //Center laser horizontally on player sprite
        laser_.sprite.positionY = _sprite.positionY - 10                   //Slight offset to be a bit higher than the player sprite
        laser_
    }

    override def death(){
        //Game over
    }



    

}