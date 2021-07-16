package Jun.view

import scalafxml.core.macros.sfxml
import scalafx.scene.control.Label
import scalafx.scene.control.Button
import _root_.Jun.MainApp
import scalafx.application.Platform
import scalafx.scene.text.Text
import _root_.Jun.model.Score

@sfxml
class GameOverController(
    private val title        : Label,
    private val restartButton: Button,
    private val quitButton   : Button,
    private val summary      : Text,
    private val name1        : Label,
    private val name2        : Label,
    private val name3        : Label,
    private val name4        : Label,
    private val name5        : Label,
    private val name6        : Label,
    private val name7        : Label,
    private val name8        : Label,
    private val name9        : Label,
    private val name10       : Label,
    private val score1       : Label,
    private val score2       : Label,
    private val score3       : Label,
    private val score4       : Label,
    private val score5       : Label,
    private val score6       : Label,
    private val score7       : Label,
    private val score8       : Label,
    private val score9       : Label,
    private val score10      : Label,
    private val date1        : Label,
    private val date2        : Label,
    private val date3        : Label,
    private val date4        : Label,
    private val date5        : Label,
    private val date6        : Label,
    private val date7        : Label,
    private val date8        : Label,
    private val date9        : Label,
    private val date10       : Label
){
    //Initilization
    private val player = MainApp.player
    val nameList  = List(name1, name2, name3, name4, name5, name6, name7, name8, name9, name10)
    val scoreList = List(score1, score2, score3, score4, score5, score6, score7, score8, score9, score10)
    val dateList = List(date1, date2, date3, date4, date5, date6, date7, date8, date9, date10)
    for(name <- nameList){
        name.setText("-")
    }
    for(score <- scoreList){
        score.setText("-")
    }
    for(date <- dateList){
        date.setText("-")
    }
    title.setText("Game Over!")
    summary.setText("Another space fighter has fallen... \nYour score was: " + player.exp)

    def addScore(){
        val score = new Score("Test Name", player.exp)
        score.save()
        Score.scoreData += score
    }

    def handleClose(){
        MainApp.endGame()
        Platform.exit()
    }

    def handleRestart(){
        MainApp.showMainMenu()
    }

}