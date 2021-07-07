package Jun.view

import scalafx.scene.canvas.Canvas
import scalafx.stage.Stage
import _root_.Jun.MainApp
import scalafx.scene.canvas.GraphicsContext
import scalafx.animation.AnimationTimer
import scalafx.application.JFXApp
import _root_.Jun.model.Player
import _root_.Jun.model.Sprite
import scalafx.scene.image.Image
import scalafx.scene.shape.Circle
import scalafxml.core.macros.sfxml
import scalafx.scene.paint.Color
import scala.io.Source
import scalafx.scene.Scene
import scalafx.scene.input.KeyEvent
import scalafx.scene.input.KeyCode
import scalafx.event.EventHandler

@sfxml
class GameController(private val canvas : Canvas){
    MainApp.stage.scene = new Scene {
        //Setting up GraphicsContext
        var gc : GraphicsContext = canvas.graphicsContext2D
        var lastTime = 0L
        //val scene = MainApp.mainScene

        //Player
        val playerShip = new Image(getClass.getResourceAsStream("/Images/player_ship.png"))
        val playerSprite = new Sprite(playerShip, 100, 200, 0, 0, playerShip.width.toDouble, playerShip.height.toDouble)
        val player = new Player(100, 10, playerSprite, 1.0)
        gc.drawImage(playerShip, 500, 500)

        //Input
        var leftPress = false
        var rightPress = false
        var upPress = false 
        var downPress = false
        onKeyPressed = (e: KeyEvent) =>{
            if(e.code == KeyCode.W) upPress = true 
            if(e.code == KeyCode.A) leftPress = true 
            if(e.code == KeyCode.S) rightPress = true 
            if(e.code == KeyCode.D) downPress = true 
        }
        
        onKeyReleased = (e: KeyEvent) => {
            if(e.code == KeyCode.W) upPress = false 
            if(e.code == KeyCode.A) leftPress = false 
            if(e.code == KeyCode.S) rightPress = false 
            if(e.code == KeyCode.D) downPress = false 
        }
        

        val timer = AnimationTimer( t => {
            def handle(currentNanoTime : Long)
            {
                if(lastTime > 0){
                    val delta = (t - lastTime)/ 1000000000.0; 

                    player.sprite.update(delta) 
                    player.sprite.render(gc)

                    if(upPress){
                        player.sprite.velocityY = 1 * player.speed
                        player.sprite.update(delta)
                    }
                    if(leftPress){
                        player.sprite.velocityX = -1 * player.speed
                        player.sprite.update(delta)
                    }
                    if(rightPress){
                        player.sprite.velocityX = 1 * player.speed
                        player.sprite.update(delta)
                    }
                    if(downPress){
                        player.sprite.velocityY = -1 * player.speed
                        player.sprite.update(delta)
                    }
                }
            }
            lastTime = t;
        })
        timer.start()
    }

}

