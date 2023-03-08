package fr.jhelp.compose.animations.interpolation

import kotlin.math.PI
import kotlin.math.cos

/**
 * Interpolation follow cosinus function
 */
object CosinusInterpolation : Interpolation
{
    /**
     * Interpolate value with following equation :
     *
     *    1 + cos((t + 1) * PI)
     *    ---------------------
     *              2
     *
     * @param percent Value to interpolate
     * @return Interpolate value
     */
    override operator fun invoke(percent: Float) =
        ((1.0 + cos((percent.toDouble() + 1.0) * PI)) / 2.0).toFloat()
}
