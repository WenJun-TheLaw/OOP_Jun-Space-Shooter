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
import java.util.Timer
import java.util.TimerTask
import scalafx.scene.paint.Color
import scalafx.scene.text.TextAlignment
import scalafx.geometry.VPos
import scalafx.scene.text.Font
import Jun.model.EnemySpawner
import scalafx.stage.Modality

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


  var shootTimer : Timer = null
  var animTimer : AnimationTimer = null
  var player : Player = null
  /**
   * Initialize the game and start game loop
   */
  def initGame() = {
    val resource = getClass.getResourceAsStream("view/Game.fxml")
    val loader = new FXMLLoader(null, NoDependencyResolver)
    loader.load(resource);

    //Setting up GraphicsContext
    val scene = stage.getScene()
    val canvas = new Canvas(stage.getWidth, stage.getHeight())
    scene.root = new AnchorPane(){
      children = List(canvas)
    } 
    val gc : GraphicsContext = canvas.graphicsContext2D

    //Player Sprites
    val playerShip = new Image(getClass.getResourceAsStream("/Images/player_ship.png"))
    val playerSprite = new Sprite(playerShip, 100, 200, 0, 0, playerShip.getWidth(), playerShip.getHeight())
    player = new Player(100, 10, playerSprite, 250.0)
    player.sprite.render(gc)

    //List of stuffs (Bullets, enemies etc)
    var laserListB : ListBuffer[Laser] = ListBuffer()
    var enemyListB : ListBuffer[Enemy] = ListBuffer()

    //EnemySpawner
    val enemySpawner = new Thread(new EnemySpawner(enemyListB))
    //Spawning enemies
    enemySpawner.start()

    //Input
    var leftPress = false
    var rightPress = false
    var upPress = false 
    var downPress = false
    var isShooting = false
    var readyToShoot = true
    shootTimer = new Timer()
    var shootCD : TimerTask = null
    var shoot : TimerTask = null
    scene.onKeyPressed = (key : KeyEvent) => {
      if(key.code == KeyCode.W) upPress = true
      if(key.code == KeyCode.A) leftPress = true
      if(key.code == KeyCode.S) downPress = true
      if(key.code == KeyCode.D) rightPress = true
      if(key.code == KeyCode.Space){
        if(readyToShoot){
          //A TimerTask to inform that shoot cooldown is over, player can shoot again
          shootCD = new TimerTask{
            override def run(): Unit = readyToShoot = true
          }
          //Check if player is already shooting
          if(!isShooting){
            //A TimerTask that repeats to continuously shoot
            shoot = new TimerTask{
              override def run(): Unit = laserListB += player.shoot
            }
            shootTimer.scheduleAtFixedRate(shoot, 0, player.atkSpeed.toLong)
            shootTimer.schedule(shootCD, player.atkSpeed.toLong)
            readyToShoot = false
            isShooting = true
          }
        }
      }
    }

    scene.onKeyReleased = (key : KeyEvent) => {
      if(key.code == KeyCode.W) upPress = false
      if(key.code == KeyCode.A) leftPress = false
      if(key.code == KeyCode.S) downPress = false
      if(key.code == KeyCode.D) rightPress = false
      if(key.code == KeyCode.Space){
        if(isShooting){
          shoot.cancel()
          isShooting = false
        }
      }
    }

    var lastNanoTime = 0D
    animTimer = AnimationTimer( currentNanoTime => {
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
      
      //Updating position, checking collisions
      //Player
      player.sprite.update(elapsedTime)
      //Bullets & Enemies
      for(enemy <- enemyListB){
        enemy.sprite.update(elapsedTime)
      }

      for(laser <- laserListB){
        laser.sprite.update(elapsedTime)
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
        enemy.sprite.render(gc)
      }
      //Graphical UI (GUI)
      gc.setFill(Color.Black)
      gc.setTextAlign(TextAlignment.Left);
      gc.setTextBaseline(VPos.CENTER)
      gc.font = new Font(30)
      val healthStr : String = "Health: " + player.health + " / " + player.maxHealth
      val expStr : String = "Exp: " +  player.exp + " / " + player.LevelUpEXP + " (Level " + player.level + ")"
      gc.fillText(healthStr, 0, 15)
      gc.fillText(expStr, 0, 55)

    })
    animTimer.start()
    
  }

  def endGame(){
    //Stopping timers
    animTimer.stop
    shootTimer.cancel
  }

  def levelUpDialog(player : Player){
    val resource = getClass.getResourceAsStream("view/LevelUpDialog.fxml")
    val loader = new FXMLLoader(null, NoDependencyResolver)
    loader.load(resource);
    val roots2  = loader.getRoot[jfxs.Parent]
    val control = loader.getController[LevelUpDialogController#Controller]

    val dialog = new Stage() {
      initModality(Modality.ApplicationModal)
      initOwner(stage)
      scene = new Scene {
        root = roots2      
      }
    }
    control.dialogStage = dialog
    control.player = player
    dialog.showAndWait()
    control.okClicked
  }
}
