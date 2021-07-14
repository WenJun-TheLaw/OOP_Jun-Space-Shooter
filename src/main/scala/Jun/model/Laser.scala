package Jun.model

import scalafx.scene.image.Image
import java.util.UUID

class Laser(
    private val _sprite : Sprite,
    private val _damage : Int,
    private val _isPlayer : Boolean) {

    private val _id : UUID = UUID.randomUUID()

    //Accessor
    def sprite = _sprite 
    def damage = _damage
    def isPlayer = _isPlayer
    def id = _id
}