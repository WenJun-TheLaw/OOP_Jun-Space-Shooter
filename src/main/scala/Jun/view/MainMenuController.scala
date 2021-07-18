package Jun.view

import scalafxml.core.macros.sfxml
import _root_.Jun.MainApp
import scalafx.scene.control.TextField
import scalafx.scene.control.Alert

@sfxml
class MainMenuController(
    private val nameField : TextField
){
    private var ok = false

    def handleStart(){
        checkName()
        if(ok) MainApp.showGame()
       
    }

    //TODO: Add a text field for user to add name
    def checkName(){
        //Not null
        if(nameField.text.value != null && nameField.text.value.length > 0){
            //Not more than 64 char
            if(nameField.text.value.length <= 64){
                //Set player name
                MainApp.playerName = nameField.text.value
                ok = true
            }
            else{
                val alert = new Alert(Alert.AlertType.Warning){
                    initOwner(MainApp.stage)
                    title       = "Error"
                    headerText  = "Name is too long"
                    contentText = "Please ensure your name is 64 characters or less officer."
                }.showAndWait()
            }
        }
        else{
            val alert = new Alert(Alert.AlertType.Warning){
                initOwner(MainApp.stage)
                title       = "Error"
                headerText  = "Name is empty"
                contentText = "Please enter your name in the text field officer."
            }.showAndWait()
        }
    }

}
