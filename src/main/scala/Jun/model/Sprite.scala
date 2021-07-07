package Jun.model

import scalafx.scene.image.Image
import scalafx.scene.canvas.GraphicsContext
import scalafx.geometry.Rectangle2D

class Sprite(
    private var _image : Image,
    private var _positionX : Double,
    private var _positionY : Double,
    private var _velocityX : Double,
    private var _velocityY : Double,
    private var _width : Double,
    private var _height : Double){

    //Accessor
    def image = _image 
    def positionX = _positionX 
    def positionY = _positionY 
    def velocityX = _velocityX 
    def velocityY = _velocityY 
    def width = _width 
    def height = _height 

    //Mutators
    def image_=(image : Image){
        _image = image
    }  
    def positionX_=(positionX : Double){
        _positionX = positionX
    }  
    def positionY_=(positionY :Double){
        _positionY = positionY
    }
    def velocityX_=(velocityX : Double){
        _velocityX = velocityX
    }
    def velocityY_=(velocityY : Double){
        _velocityY = velocityY
    }  
    def width_=(width : Double){
        _width = width
    }  
    def height_=(height : Double){
        _height = height
    }  

    //Functions
    def update(time : Double){
        _positionX += _velocityX * time;
        _positionY += _velocityY * time;
    }
 
    def render(gc : GraphicsContext){
        gc.drawImage(_image, _positionX, _positionY );
    }
 
    def getBoundary() : Rectangle2D = {
       new Rectangle2D(_positionX, _positionY, _width, _height);
    }
 
    def intersects(s : Sprite) : Boolean = {
        s.getBoundary().intersects(this.getBoundary());
    }
}