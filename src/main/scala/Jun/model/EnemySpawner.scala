package Jun.model

import Jun.MainApp
import scala.collection.mutable.ListBuffer
import scalafx.scene.image.Image
import scala.util.Random

class EnemySpawner() extends Runnable{

    var waves = 1
    val waveScaling = 0.4
    val waveHpScaling = 0.2
    val random = new Random()
    val Max_X = MainApp.stage.getWidth.toInt
    val Max_Y = MainApp.stage.getHeight.toInt
    var numberOfEnemies : Int = 0

    override def run(): Unit = {
        while(MainApp.spawnEnemy){
            try{
                //If less than 1 enemy remaining
                numberOfEnemies = 0
                for(enemy <- MainApp.enemyListB){
                    numberOfEnemies += 1
                }

                if(numberOfEnemies < 1){
                    //Spawn between 3 to 6 enemies, with 1 additional each wave
                    val maxSpawn = random.nextInt(3) + 2 + waves
                    for(i <- 1 to maxSpawn){
                        val enemyShip = new Image(getClass.getResourceAsStream("/Images/enemy_ship_1.png"))
                        val enemySprite = new Sprite(enemyShip, 0, 0, 0, 0, enemyShip.getWidth(), enemyShip.getHeight())
                        val hp = (500 * (waves * waveHpScaling)).toInt
                        val dmg = (5 * (waves * waveScaling)).toInt
                        val exp = (25 * (waves * waveScaling)).toInt
                        val enemy = new Enemy(hp, dmg, enemySprite, 325.0)
                        //X : Between 100 to eg: (1600-200+100) || 100 - 1700
                        //Y : Between 100 to eg: (900*0.4) || 0 - 360
                        enemy.sprite.positionX = random.nextInt(Max_X - 200) + 100
                        enemy.sprite.positionY = random.nextInt((Max_Y * 0.4).toInt)
                        enemy.exp = exp
                        MainApp.enemyListB += enemy
                        enemy.shoot()
                        println("Enemies spawned: " + i + "|| HP: " + hp + " || DMG: " + dmg +" || EXP: " + exp)
                    }
                    waves += 1
                }
                Thread.sleep(2000)
            } catch {
                case e : InterruptedException => e.printStackTrace()
                case _ : Throwable => println("Unexpected error when spawning enemies")
            }
        }
        
    }
  
}