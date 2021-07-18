package Jun.view

import scalafxml.core.macros.sfxml
import scalafx.scene.control.Label
import scalafx.scene.control.Button
import _root_.Jun.MainApp
import scalafx.application.Platform
import scalafx.scene.text.Text
import _root_.Jun.model.Score
import scalafx.scene.control.TableView
import scalafx.scene.control.TableColumn
import java.time.LocalDate

@sfxml
class GameOverController(
    private val title           : Label,
    private val restartButton   : Button,
    private val quitButton      : Button,
    private val summary         : Text,
    private val scoreTable      : TableView[Score],
    private val nameCol         : TableColumn[Score, String],
    private val scoreCol        : TableColumn[Score, Integer],
    private val dateCol         : TableColumn[Score, String]
){
    //Initilization
    private val player = MainApp.player

    scoreTable.items = Score.scoreData
    nameCol.cellValueFactory = {_.value.name}
    scoreCol.cellValueFactory = {_.value.score}
    dateCol.cellValueFactory = {_.value.date}
    title.setText("Game Over!")
    summary.setText("Another space fighter has fallen... \nYour score was: " + player.exp)
    restartButton.focusTraversable = false
    quitButton.focusTraversable = false


    def addScore(){
        val score = new Score(player.name, player.exp)
        score.save()
        Score.scoreData += score
    }

    def handleClose(){
        MainApp.endGame()
        Platform.exit()
        System.exit(0)
    }

    def handleRestart(){
        MainApp.showMainMenu()
    }

}