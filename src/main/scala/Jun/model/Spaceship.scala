package Jun.model

import java.util.UUID

abstract class Spaceship(
    private var _health : Int, 
    private var _damage : Int, 
    private val _sprite : Sprite,
    private var _speed : Double){

    private val _id : UUID = UUID.randomUUID()

    //Accessor
    def health = _health 
    def damage = _damage
    def sprite = _sprite
    def speed = _speed
    def id = _id

    //Mutator
    def damage_=(newDamage : Int){
        _damage = newDamage
    }
    def speed_=(speed : Double){
        _speed = speed
    }

    //Functions
    def takeDamage(damageTaken : Int){
        _health -= damageTaken
        if(_health <= 0){
            death()
        }
    }

    def death()


}