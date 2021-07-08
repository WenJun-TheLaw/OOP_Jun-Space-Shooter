package Jun
import scalafx.application.JFXApp
import scalafx.application.JFXApp.PrimaryStage
import scalafx.scene.Scene
import scalafx.Includes._
import scalafxml.core.{NoDependencyResolver, FXMLView, FXMLLoader}
import javafx.{scene => jfxs}
import scalafx.stage.{Stage}
import scalafx.scene.image.Image
import Jun.util.Database
import Jun.model.Score
import javafx.{scene => jfxs}
import scalafx.scene.canvas.GraphicsContext
import scalafx.scene.canvas.Canvas
import Jun.model.Sprite
import Jun.model.Player
import scalafx.scene.input.KeyEvent
import scalafx.scene.input.KeyCode
import scalafx.animation.AnimationTimer

object MainApp extends JFXApp {
  //initialize database
  Database.setupDB()
  Score.scoreData ++= Score.AllScores

  // transform path of RootLayout.fxml to URI for resource location.
  val rootResource = getClass.getResourceAsStream("view/RootLayout.fxml")
  // initialize the loader object.
  val loader = new FXMLLoader(null, NoDependencyResolver)
  // Load root layout from fxml file.
  loader.load(rootResource)
  // retrieve the root component BorderPane from the FXML 
  val roots = loader.getRoot[jfxs.layout.BorderPane]
  // initialize stage
  stage = new PrimaryStage {
    title = "Jun Space Shooter"
      scene = new Scene(roots)
    icons += new Image(getClass.getResourceAsStream("/Images/icon.png"))
  }

  def showMainMenu() = {
    val resource = getClass.getResourceAsStream("view/MainMenu.fxml")
    val loader = new FXMLLoader(null, NoDependencyResolver)
    loader.load(resource);
    val roots = loader.getRoot[jfxs.layout.AnchorPane]
    MainApp.roots.setCenter(roots)
    initGame()
  } 

  def showGame() = {
    val resource = getClass.getResourceAsStream("view/Game.fxml")
    val loader = new FXMLLoader(null, NoDependencyResolver)
    loader.load(resource);
    val roots = loader.getRoot[jfxs.layout.AnchorPane]
    MainApp.roots.setCenter(roots)
  }


  // call to display Welcome when app start
  showMainMenu()

  /**
   * Initialize the player and start game loop
   */
  def initGame() = {
    //Setting up GraphicsContext
    val scene = stage.getScene()
    val canvas = 
    var gc : GraphicsContext = canvas.graphicsContext2D
    var lastTime = 0L
    //val scene = MainApp.mainScene

    //Player
    val playerShip = new Image(getClass.getResourceAsStream("/Images/player_ship.png"))
    val playerSprite = new Sprite(playerShip, 100, 200, 0, 0, playerShip.width.toDouble, playerShip.height.toDouble)
    val player = new Player(100, 10, playerSprite, 1.0)
    player.sprite.render(gc)

    //Input
    var leftPress = false
    var rightPress = false
    var upPress = false 
    var downPress = false
    scene.onKeyPressed = (key : KeyEvent) => {
        if(key.code == KeyCode.W) upPress = true
        if(key.code == KeyCode.A) leftPress = true
        if(key.code == KeyCode.S) rightPress = true
        if(key.code == KeyCode.D) downPress = true
    }

    scene.onKeyReleased = (key : KeyEvent) => {
        if(key.getCode == KeyCode.W) upPress = false
        if(key.getCode == KeyCode.A) leftPress = false
        if(key.getCode == KeyCode.S) rightPress = false
        if(key.getCode == KeyCode.D) downPress = false
    }

    val timer = AnimationTimer( t => {
      def handle(currentNanoTime : Long){
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
