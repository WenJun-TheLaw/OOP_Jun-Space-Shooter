package Jun.view

import scalafxml.core.macros.sfxml
import scalafx.application.Platform
import _root_.Jun.MainApp

@sfxml
class RootLayoutController(){
    def handleClose(){
        MainApp.endGame()
        Platform.exit()
    }
}

