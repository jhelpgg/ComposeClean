package fr.jhelp.android.library.animations.interpolation

import kotlin.math.ln
import kotlin.math.ln1p

/**
 * Interpolation follow logarithm progression
 */
object LogarithmInterpolation : Interpolation
{
    /**
     * Interpolate value with following equation:
     *
     *    ln(t + 1)
     *    --------
     *     ln(2)
     *
     * @param percent Value to interpolate
     * @return Interpolate value
     */
    override operator fun invoke(percent: Float): Float =
        (ln1p(percent.toDouble()) / ln(2.0)).toFloat()
}
