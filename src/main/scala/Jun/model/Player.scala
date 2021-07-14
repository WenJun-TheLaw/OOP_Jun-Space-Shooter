package Jun.model

import scalafx.scene.image.Image

class Player(
    private var _health : Int, 
    private var _damage : Int, 
    private val _sprite : Sprite,
    private var _speed : Double) 
    extends Spaceship (_health, _damage, _sprite, _speed){
    
    //Initializing default variables
    private var _atkSpeed : Double = 700
    private var _maxHealth : Int = 100
    private var _healthRegen : Double = 0.5
    private var _level : Int = 1
    private var _exp : Int = 0
    private val _LevelUpEXP = 100

    //Accessor
    def maxHealth = _maxHealth 
    def healthRegen = _healthRegen
    def atkSpeed = _atkSpeed
    def exp = _exp
    def LevelUpEXP = _LevelUpEXP
    def level = _level

    //Mutator
    def maxHealth_=(maxHealth : Int){
        _maxHealth = maxHealth
    }
    def healthRegen_=(healthRegen : Double){
        _healthRegen = healthRegen
    }
    def atkSpeed_=(atkSpeed : Double){
        _atkSpeed = atkSpeed
    }

    //Functions
    def getEXP(exp : Int){
        _exp += exp
        if(_exp > LevelUpEXP){
            levelUp()
        }
    }

    def heal(heal : Int){
        _health += heal
        if(_health > maxHealth){
            _health = maxHealth
        }
    }

    def levelUp(){
        _exp -= LevelUpEXP
        _level += 1
        //Pop up level up 
    }

    def shoot() = {
        //Laser sprites
        val atkImg = new Image(getClass.getResourceAsStream("/Images/small_laser_blue.png"))
        val atkSprite = new Sprite(atkImg, 0, 0, 0, 0, atkImg.getWidth(), atkImg.getHeight())
        val laser_ = new Laser(atkSprite, _damage, true)
        laser_.sprite.velocityX = 0 
        laser_.sprite.velocityY = -600 
        laser_.sprite.positionX = _sprite.positionX + (_sprite.width / 2)  //Center laser horizontally on player sprite
        laser_.sprite.positionY = _sprite.positionY - 10                   //Slight offset to be a bit higher than the player sprite
        laser_
    }

    def death(){

    }

}