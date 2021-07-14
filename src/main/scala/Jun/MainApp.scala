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
import scalafx.scene.layout.AnchorPane
import Jun.model.Enemy
import Jun.model.Laser
import scala.collection.mutable.ListBuffer

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
  } 

  def showGame() = {
    val resource = getClass.getResourceAsStream("view/Game.fxml")
    val loader = new FXMLLoader(null, NoDependencyResolver)
    loader.load(resource);
    val roots = loader.getRoot[jfxs.layout.AnchorPane]
    MainApp.roots.setCenter(roots)
    initGame()
  }


  // call to display Welcome when app start
  showMainMenu()

  /**
   * Initialize the player and start game loop
   */
  def initGame() = {
    val resource = getClass.getResourceAsStream("view/Game.fxml")
    val loader = new FXMLLoader(null, NoDependencyResolver)
    loader.load(resource);

    //Setting up GraphicsContext
    val scene = stage.getScene()
    val canvas = new Canvas(stage.width.value, stage.height.value)
    scene.root = new AnchorPane(){
      children = List(canvas)
    } 
    val gc : GraphicsContext = canvas.graphicsContext2D

    //Player Sprites
    val playerShip = new Image(getClass.getResourceAsStream("/Images/player_ship.png"))
    val playerSprite = new Sprite(playerShip, 100, 200, 0, 0, playerShip.getWidth(), playerShip.getHeight())
    val player = new Player(100, 10, playerSprite, 250.0)
    player.sprite.render(gc)

    //Input
    var leftPress = false
    var rightPress = false
    var upPress = false 
    var downPress = false
    var shootPress = false
    scene.onKeyPressed = (key : KeyEvent) => {
      if(key.code == KeyCode.W) upPress = true
      if(key.code == KeyCode.A) leftPress = true
      if(key.code == KeyCode.S) downPress = true
      if(key.code == KeyCode.D) rightPress = true
      if(key.code == KeyCode.Space) shootPress = true
    }

    scene.onKeyReleased = (key : KeyEvent) => {
      if(key.code == KeyCode.W) upPress = false
      if(key.code == KeyCode.A) leftPress = false
      if(key.code == KeyCode.S) downPress = false
      if(key.code == KeyCode.D) rightPress = false
      if(key.code == KeyCode.Space) shootPress = false
    }

    //List of stuffs (Bullets, enemies etc)
    var laserListB : ListBuffer[Laser] = ListBuffer()
    var enemyListB : ListBuffer[Enemy] = ListBuffer()

    var lastNanoTime = 0D
    var lastShootNano = 0D
    val timer = AnimationTimer( currentNanoTime => {
      //Calculating time since last frame for frame independant rendering
      val elapsedTime : Double = (currentNanoTime - lastNanoTime) / 1000000000.0;
      lastNanoTime = currentNanoTime;

      //Input check
      player.sprite.velocityX = 0
      player.sprite.velocityY = 0
      if(upPress){
        player.sprite.velocityY += -1 * player.speed
      }
      if(leftPress){
        player.sprite.velocityX += -1 * player.speed
      }
      if(rightPress){
        player.sprite.velocityX += 1 * player.speed
      }
      if(downPress){
        player.sprite.velocityY += 1 * player.speed
      }
      if(shootPress){
        val now = System.nanoTime()
        //Checking for atkSpeed cooldwon
        if((lastShootNano <= 0L) || ((now - lastShootNano) >= player.atkSpeed)){
          laserListB += player.shoot
        }
        lastShootNano = now
      }
      
      //Updating position, checking collisions
      //Player
      player.sprite.update(elapsedTime)
      //Bullets & Enemies
      for(enemy <- enemyListB){
        enemy.sprite.update(elapsedTime)
      }

      for(laser <- laserListB){
        laser.sprite.update(elapsedTime)
        println("Updating (" + laserListB.indexOf(laser) + ") :" + laser.sprite.positionX.toInt + " || " + laser.sprite.positionY.toInt)
        //Collision check
        if(laser.isPlayer){
          for(enemy <- enemyListB){
            if(laser.sprite.intersects(enemy.sprite)){
              enemy.takeDamage(laser.damage)
              laserListB -= laser
            }
          }
        }
        else{
          if(laser.sprite.intersects(player.sprite)){
            player.takeDamage(laser.damage)
            laserListB -= laser
          }
        }

        //Didnt hit anything, check if its not in the scene anymore
        if(!laser.sprite.getBoundary().intersects(0, 0 ,scene.getWidth(), scene.getHeight())){
          laserListB -= laser
        }
      }
      

      //Rendering
      //Player
      gc.clearRect(0, 0, scene.getWidth(), scene.getHeight())
      player.sprite.render(gc)

      //Bullets
      val laserList = laserListB.toList
      for(laser <- laserList){
        laser.sprite.render(gc)
      }
      //Enemies
      for(enemy <- enemyListB){

      }

    })
    timer.start()
    
  }
}
