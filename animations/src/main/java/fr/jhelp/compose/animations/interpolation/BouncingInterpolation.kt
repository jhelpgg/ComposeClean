package fr.jhelp.compose.animations.interpolation

import fr.jhelp.android.library.math.extensions.compare
import fr.jhelp.android.library.math.extensions.nul
import fr.jhelp.android.library.math.square
import kotlin.math.max
import kotlin.math.min
import kotlin.math.sqrt

/**
 * Interpolation that bounce
 * @param numberBounce Number of bounce
 */
class BouncingInterpolation(numberBounce: Int = 2) : Interpolation
{
    /**Number of bounce*/
    private val numberBounce = max(0, numberBounce)

    /**
     * Interpolate value with bounce effect
     *
     * @param percent Value to interpolate
     * @return Interpolate value
     */
    override operator fun invoke(percent: Float): Float
    {
        if (this.numberBounce == 0)
        {
            return square(percent)
        }

        var amplitude = 1f / (this.numberBounce + 1)

        if (percent.compare(amplitude) < 0)
        {
            return square(percent / amplitude)
        }

        var free = 1f - amplitude * 0.56789f
        var minimum = 0.56789f
        var percentReal = percent - amplitude
        var left = this.numberBounce - 1

        while (percentReal.compare(
                amplitude) >= 0 && !amplitude.nul && !minimum.nul && !percentReal.nul && left > 0)
        {
            minimum *= 0.56789f
            percentReal -= amplitude
            free -= amplitude
            amplitude = free * 0.56789f
            left--
        }

        if (left == 0)
        {
            amplitude = free / 2f
        }

        val squareRoot = sqrt(minimum.toDouble()).toFloat()
        percentReal = (percentReal - amplitude / 2f) * (squareRoot * 2f / amplitude)
        return min(square(percentReal) + 1 - minimum, 1f)
    }
}
