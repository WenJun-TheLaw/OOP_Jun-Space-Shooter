package Jun.controller

import scalafx.scene.media.MediaPlayer
import scalafx.scene.media.Media
import java.io.File
import Jun.JunMath
import scala.util.Random
import Jun.MainApp
import scalafx.scene.media.MediaView

class AudioController(){
    private var _volume = 50.0
    private val VolumeMultiplier = 0.01
    private var songList : List[Media] = null
    private var _mediaPlayer : MediaPlayer = null

    //Accessor
    def volume = _volume
    def mediaPlayer = _mediaPlayer

    //Mutator
    /**
     * Set the volume between 0 to 1. <br>
     * NEVER directly access mediaPlayer.volume, this will multiply the volume by 100 fold if done incorrectly
    */
    def volume_=(newVolume : Double){
        _volume = JunMath.clamp(newVolume, 0 , 100)
        mediaPlayer.volume = _volume * VolumeMultiplier
    }

    //Functions
    /**
     * Initializes the songs and media players
    */
    def init(){
        val song1 = new Media(getClass.getResource("/audio/TheFatRat - Arcadia Chapter 2.mp3").toURI().toString())
        val song2 = new Media(getClass.getResource("/audio/TheFatRat - Jackpot.mp3").toURI().toString())
        val song3 = new Media(getClass.getResource("/audio/TheFatRat - Prelude.mp3").toURI().toString())
        val song4 = new Media(getClass.getResource("/audio/TheFatRat - Unity.mp3").toURI().toString())
        val song5 = new Media(getClass.getResource("/audio/TheFatRat - Windfall.mp3").toURI().toString())
        val song6 = new Media(getClass.getResource("/audio/TheFatRat - Xenogenesis.mp3").toURI().toString())
        songList = List(song1, song2, song3, song4, song5, song6)
    }

    def start(){
        println("Muziko starto")

        //Random index
        var random = new Random()
        var index = random.nextInt(songList.length)
        _mediaPlayer = new MediaPlayer(songList.apply(index))
        _mediaPlayer.play()
        _mediaPlayer.volume = _volume  * VolumeMultiplier
            
        //Increment index, reset if more than length, set new song
        _mediaPlayer.onEndOfMedia = {
            index += 1
            if(index >= songList.length){
                index = 0
            }

            println("Changing track")
            _mediaPlayer = new MediaPlayer(songList.apply(index))
            _mediaPlayer.play
            MainApp.updateMediaView(_mediaPlayer)
        }
        
    }

    def pause(){
        _mediaPlayer.pause
    }

    def resume(){
        _mediaPlayer.play()
    }
    
}