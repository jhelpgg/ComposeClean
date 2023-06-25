package fr.jhelp.android.library.animations.interpolation

import fr.jhelp.android.library.math.EPSILON_FLOAT
import kotlin.math.max

/**
 * Interpolation that overshoot.
 *
 * That is to say it goes to far and then go back to the good place
 * @param tension Effect factor
 */
class OvershootInterpolation(tension: Float = 1f) : Interpolation
{
    /**Effect factor*/
    private val tension = max(EPSILON_FLOAT, tension)

    /**
     * Interpolate value with overshoot effect
     *
     * @param percent Value to interpolate
     * @return Interpolate value
     */
    override operator fun invoke(percent: Float): Float
    {
        val value = percent - 1f
        return (this.tension + 1f) * value * value * value + this.tension * value * value + 1f
    }
}