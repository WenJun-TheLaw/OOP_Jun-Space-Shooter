package Jun.view

import scalafxml.core.macros.sfxml
import scalafx.event.ActionEvent
import _root_.Jun.model.Player
import scalafx.stage.Stage

@sfxml
class MainMenuController(){
    private var _player : Player = null
    var dialogStage : Stage = null
    var okClicked = false

    //Accessor
    def player = _player

    //Mutator
    def player_= (player : Player){
        _player = player
    }

    def handleAtkUp(action : ActionEvent){
        player.damage += 10
        handleConfirm
    }

    def handleAtkSpeedUp(action : ActionEvent){
        player.atkSpeed -= 100
        handleConfirm
    }

    def handleMaxHpUp(action : ActionEvent){
        player.maxHealth += 20
        handleConfirm
    }

    def handleConfirm(){
        player.heal(100)
        okClicked = true;
        dialogStage.close()
    }

}
