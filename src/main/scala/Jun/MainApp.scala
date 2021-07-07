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
import scalafx.scene.canvas.Canvas

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
      scene = new Scene {
      root = roots
    }
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
  }


  // call to display Welcome when app start
  showMainMenu()
}
