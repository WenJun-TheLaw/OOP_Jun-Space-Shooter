package Jun.model

class Enemy(
    private var _health : Int, 
    private var _damage : Int, 
    private var _sprite : Sprite,
    private var _speed: Double) 
    extends Spaceship (_health, _damage, _sprite, _speed){
    
}