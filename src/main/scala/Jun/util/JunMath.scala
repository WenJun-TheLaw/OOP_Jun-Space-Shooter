package Jun

object JunMath{
    //My clamp function modelled from C++
    def clamp (value : Double, min : Double, max : Double ) = {
        var result = value
        if(value <= min){
        result = min
        }
        else if (value >= max){
        result = max
        }

        result
    }
}