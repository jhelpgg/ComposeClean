package fr.jhelp.compose.animations.interpolation

import kotlin.math.PI
import kotlin.math.sin

/**
 * Interpolation follow sinus function
 */
object SinusInterpolation : Interpolation
{
    /**
     * Interpolate value with following equation :
     *
     *    1 + sin(t * PI - PI/2)
     *    ----------------------
     *              2
     *
     * @param percent Value to interpolate
     * @return Interpolate value
     */
    override operator fun invoke(percent: Float) =
        ((1.0 + sin(percent.toDouble() * PI - PI / 2.0)) / 2.0).toFloat()
}