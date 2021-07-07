package Jun.model

class Player(
    private var _health : Int, 
    private var _damage : Int, 
    private var _sprite : Sprite,
    private var _speed : Double) 
    extends Spaceship (_health, _damage, _sprite, _speed){
    
    private var _maxHealth : Int = 100
    private var _healthRegen : Double = 0.5
    private var _level : Int = 0
    private var _exp : Int = 0
    val LevelUpEXP = 100

    //Accessor
    def maxHealth = _maxHealth 
    def healthRegen = _healthRegen

    //Mutator
    def maxHealth_=(maxHealth : Int){
        _maxHealth = maxHealth
    }
    def healthRegen_=(healthRegen : Double){
        _healthRegen = healthRegen
    }


    //Functions
    def getEXP(exp : Int){
        _exp += exp
        if(_exp > LevelUpEXP){
            levelUp()
        }
    }

    def levelUp(){
        _exp -= LevelUpEXP
        _level += 1
        //Pop up level up 
    }

}