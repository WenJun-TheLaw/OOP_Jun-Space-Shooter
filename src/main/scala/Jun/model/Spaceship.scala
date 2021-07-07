package Jun.model

abstract class Spaceship(
    private var _health : Int, 
    private var _damage : Int, 
    private var _centerX : Long, 
    private var _centerY : Long){

    //Accessor
    def health = _health 
    def damage = _damage
    def centerX = _centerX
    def centerY = _centerY


    //Mutator
    def damage_=(newDamage : Int){
        _damage = newDamage
    }
    def centerX_=(X : Long){
        _centerX = X
    }
    def centerY_=(Y : Long){
        _centerY = Y
    }

    def takeDamage(damageTaken : Int){
        _health -= damageTaken
    }

    def heal(heal : Int){
        _health += heal
    }


}