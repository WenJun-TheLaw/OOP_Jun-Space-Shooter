package Jun.model

import scala.collection.mutable.ListBuffer
import org.apache.derby.impl.tools.sysinfo.Main
import Jun.MainApp

class Enemy(
    private var _health : Int, 
    private var _damage : Int, 
    private var _sprite : Sprite,
    private var _speed: Double,
    private var _enemyListB : ListBuffer[Enemy]) 
    extends Spaceship (_health, _damage, _sprite, _speed){
    
    private var _exp : Int = 25

    //Accessor
    def exp = _exp  

    //Mutator
    def exp_=(exp : Int){
        _exp = exp
    }

    //Functions
    def death(){
        MainApp.player.getEXP(_exp)
        _enemyListB -= this
    }
}