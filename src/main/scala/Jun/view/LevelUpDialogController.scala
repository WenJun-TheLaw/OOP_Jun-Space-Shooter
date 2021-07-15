package Jun.view

import scalafxml.core.macros.sfxml
import scalafx.event.ActionEvent
import _root_.Jun.model.Player
import scalafx.stage.Stage
import scalafx.scene.control.Button
import scalafx.scene.text.Text

@sfxml
class LevelUpDialogController(
    private val atkUpButton       : Button,
    private val atkSpeedUpButton  : Button,
    private val maxHpUpButton     : Button,
    private val levelUpText       : Text 
){
    private var _player : Player = null
    private var _level : Int = 0
    var dialogStage : Stage = null
    //Set it so the buttons don't get focus on display
    //Most times player will still be holding a button, causing the dialog to close almost imemadiately
    atkUpButton.setFocusTraversable(false)
    atkSpeedUpButton.setFocusTraversable(false)
    maxHpUpButton.setFocusTraversable(false)

    //Accessor
    def player = _player
    def level = _level

    //Mutator
    def player_= (player : Player){
        _player = player
    }
    def level_= (level : Int){
        _level = level
    }

    //Functions
    def handleAtkUp(action : ActionEvent){
        player.damage += 10
        handleConfirm
    }

    def handleAtkSpeedUp(action : ActionEvent){
        player.atkSpeed += 0.4
        handleConfirm
        //TODO: Change atk speed to 1000 / atkSpeed 
        //Eg: atkSpeed = 2 means 1 atk every 500 ms
    }

    def handleMaxHpUp(action : ActionEvent){
        player.maxHealth += 20
        handleConfirm
    }

    def handleConfirm(){
        dialogStage.close()
    }

    def setText(){
        levelUpText.text = "Level " + _level + "! You have leveled up! Choose an upgrade"
    }

}
