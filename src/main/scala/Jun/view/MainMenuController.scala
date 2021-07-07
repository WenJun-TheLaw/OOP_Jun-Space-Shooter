package Jun.view

import scalafxml.core.macros.sfxml
import _root_.Jun.MainApp

@sfxml
class MainMenuController(){
    def handleStart(){
        MainApp.showGame()
    }

}
