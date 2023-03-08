package fr.jhelp.compose.animations.interpolation

import kotlin.math.E
import kotlin.math.expm1

/**
 * Interpolation follow exponential progression
 */
object ExponentialInterpolation : Interpolation
{
    /**
     * Interpolate value with following equation :
     *
     *     t
     *    e - 1
     *    ------
     *    e - 1
     *
     * @param percent Value to interpolate
     * @return Interpolate value
     */
    override operator fun invoke(percent: Float) =
        (expm1(percent.toDouble()) / (E - 1.0)).toFloat()
}