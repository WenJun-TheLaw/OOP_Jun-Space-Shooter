package Jun

object JunMath{
    /**
     * My clamp function modelled from C++, returns the double that is clamped between the min and max specified
     * @param   value   The number to be clamped
     * @param   min     The minimum value, inclusive
     * @param   max     The maximum value, inclusive
     * @return          The number clamped          
    */
    def clamp(value : Double, min : Double, max : Double) = {
        var result = value
        if(value <= min){
            result = min
        }
        else if (value >= max){
            result = max
        }

        result
    }

    /**
     * My range function, checks if the integer is between the range specified
     * @param   value   The number to be checked
     * @param   min     The minimum value, inclusive
     * @param   max     The maximum value, inclusive
     * @return          Whether the number is within the range
    */
    def range(value: Int, min : Int, max : Int) = {
        var result : Boolean = false
        if(value < min || value > max){
            result = false
        }
        else{
            result = true
        }

        result
    }
}