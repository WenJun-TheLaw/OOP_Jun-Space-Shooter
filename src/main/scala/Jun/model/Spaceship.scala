package Jun.model

abstract class Spaceship(
    private var _health : Int, 
    private var _damage : Int, 
    private var _sprite : Sprite,
    private var _speed : Double){

    //Accessor
    def health = _health 
    def damage = _damage
    def sprite = _sprite
    def speed = _speed

    //Mutator
    def damage_=(newDamage : Int){
        _damage = newDamage
    }
    def sprite_=(sprite : Sprite){
        _sprite = sprite
    }
    def speed_=(speed : Double){
        _speed = speed
    }

    //Functions
    def takeDamage(damageTaken : Int){
        _health -= damageTaken
    }

    def heal(heal : Int){
        _health += heal
    }


}