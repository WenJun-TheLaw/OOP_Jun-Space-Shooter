package Jun.view

import scalafxml.core.macros.sfxml
import scalafx.application.Platform
import _root_.Jun.MainApp
import scalafx.scene.control.Alert
import _root_.Jun.model.Score
import scalikejdbc.ConnectionPool

@sfxml
class RootLayoutController(){
    def handleClose(){
      MainApp.endGame()
      Platform.exit()
      System.exit(0)
    }


    def showAbout(){
      val alert = new Alert(Alert.AlertType.Information){
        initOwner(MainApp.stage)
        title       = "About"
        headerText  = "Jun Space Fighter Alpha Ver 0.1"
        contentText = "Find out more over at https://github.com/WenJun-TheLaw/OOP_Jun-Space-Shooter"
      }.showAndWait()
    }

    def showControls(){
      val alert = new Alert(Alert.AlertType.Information){
        initOwner(MainApp.stage)
        title       = "About"
        headerText  = "Jun Space Fighter Alpha Ver 0.1"
        contentText = "Find out more over at https://github.com/WenJun-TheLaw/OOP_Jun-Space-Shooter"
      }.showAndWait()
    }

}

