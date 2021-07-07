package Jun.view

import scalafxml.core.macros.sfxml
import scalafx.application.Platform

@sfxml
class RootLayoutController(){
    def handleClose(){
        Platform.exit()
    }

    
}

