package Jun.model

import Jun.MainApp
import scala.collection.mutable.ListBuffer
import scalafx.scene.image.Image
import scala.util.Random
import Jun.JunMath

class EnemySpawner() extends Runnable{

    var waves = 1
    val waveDmgScaling = 2
    val waveHpScaling = 20
    val waveExpScaling = 5
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
                        val hp = (100 + waves * waveHpScaling)
                        val dmg = (5 + waves * waveDmgScaling)
                        val exp = (20 + waves * waveExpScaling)
                        val enemy = new Enemy(hp, dmg, enemySprite, 250.0, exp)

                        //Generate Position, with checks to ensure enemy don't stack on top of each other
                        var validXLocation = false
                        var validYLocation = false
                        val usedX : ListBuffer[Int] = ListBuffer()
                        val usedY : ListBuffer[Int] = ListBuffer()
                        var tryX : Int = 0
                        var tryY : Int = 0
                        do{
                            //X : Between (1600-200+100) || 100 - 1500
                            //Y : Between (900*0.3) || 0 - 360
                            tryX = random.nextInt(Max_X - 200) + 100
                            tryY = random.nextInt((Max_Y * 0.4).toInt)
                            
                            //Checking if enemy already exists here
                            if(!validXLocation){
                                if(!usedX.isEmpty){
                                    for(xCor <- usedX){
                                        val minX = xCor - enemyShip.getWidth.toInt
                                        val maxX = xCor + enemyShip.getWidth.toInt
                                        if(!JunMath.range(tryX, minX, maxX)){
                                            validXLocation = true
                                            usedX += tryX
                                        }
                                    }
                                }
                                else{
                                    validXLocation = true
                                    usedX += tryX
                                }
                            }

                            if(!validYLocation){
                                if(!usedY.isEmpty){
                                    for(yCor <- usedY){
                                        val minY = yCor - enemyShip.getHeight.toInt
                                        val maxY = yCor + enemyShip.getHeight.toInt
                                        if(!JunMath.range(tryY, minY, maxY)){
                                            validYLocation = true
                                            usedY += tryY
                                        }
                                    }
                                }
                                else{
                                    validYLocation = true
                                    usedY += tryY
                                }
                            }

                        }while(!validXLocation || !validYLocation)
                        //Assign position
                        enemy.sprite.positionX = tryX
                        enemy.sprite.positionY = tryY

                        //Initilize enemy in List and start its functions
                        MainApp.enemyListB += enemy
                        enemy.chase
                        enemy.shoot

                        println("Enemies spawned: " + i + "|| HP: " + hp + " || DMG: " + dmg + " || EXP: " + exp + " || X: " + tryX + " || Y: " + tryY )
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