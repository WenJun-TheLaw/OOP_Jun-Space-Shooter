package Jun.view

import scalafxml.core.macros.sfxml
import scalafx.scene.canvas.Canvas

@sfxml
class GameController(
    private val _canvas : Canvas){

    def canvas = _canvas
}