package fr.jhelp.android.library.animations.interpolation

import fr.jhelp.android.library.math.EPSILON_FLOAT
import fr.jhelp.android.library.math.extensions.compare
import kotlin.math.max

/**
 * Interpolation with anticipate and overshoot effect
 *
 * Anticipate : Like if it take a run-up
 *
 * Overshoot : Goes to far and return back
 * @param tension Effect factor
 */
class AnticipateOvershootInterpolation(tension: Float = 1f) : Interpolation
{
    /**Effect factor*/
    private val tension = max(EPSILON_FLOAT, tension)

    /**
     * Interpolate value with anticipation and overshoot effect
     *
     * @param percent Value to interpolate
     * @return Interpolate value
     */
    override operator fun invoke(percent: Float): Float =
        when
        {
            percent.compare(0.5f) < 0 ->
            {
                val value = 2f * percent
                0.5f * ((this.tension + 1f) * value * value * value - this.tension * value * value)
            }

            else                      ->
            {
                val value = 2f * percent - 2f
                0.5f * ((this.tension + 1f) * value * value * value + this.tension * value * value) + 1f
            }
        }
}