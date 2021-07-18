package Jun.view

import scalafxml.core.macros.sfxml
import scalafx.scene.canvas.Canvas
import scalafx.scene.media.MediaView

@sfxml
class GameController(
    private var _canvas : Canvas,
    private var _mediaView : MediaView
){
    //Accessors
    def canvas = _canvas
    def mediaView = _mediaView

    //Mutators
    def mediaView_=(newView : MediaView){
        _mediaView = newView
    }
    def canvas_=(newCanvas : Canvas){
        _canvas = newCanvas
    }

}