package Jun.view

import scalafxml.core.macros.sfxml
import scalafx.stage.Stage
import scalafx.scene.control.Slider
import Jun.MainApp
import Jun.controller.AudioController

@sfxml
class ControlDialogController(
    private val volumeSlider : Slider
){
    var dialogStage : Stage = null
    var audioController : AudioController = null

    volumeSlider.value_=(MainApp.volume)

    def handleVolume(){
        if(audioController != null){
            audioController.volume_=(volumeSlider.getValue)
        }
        //Else: This means the audio controller is not yet created
        MainApp.volume = volumeSlider.getValue
    }

    def handleConfirm(){
        dialogStage.close
        MainApp.resume
    }
}