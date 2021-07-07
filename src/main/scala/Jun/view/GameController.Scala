package Jun.view

import scalafx.scene.canvas.Canvas
import scalafx.stage.Stage
import _root_.Jun.MainApp
import scalafx.scene.canvas.GraphicsContext
import scalafx.animation.AnimationTimer
import scalafx.application.JFXApp

class GameController extends JFXApp{
    var canvas = new Canvas()

    var gc : GraphicsContext = canvas.graphicsContext2D
    var lastTime = 0L

    val timer = AnimationTimer(t => {
        if(lastTime > 0){
            val delta = (t-lastTime)/1e9
        }
    })

    

    
}

