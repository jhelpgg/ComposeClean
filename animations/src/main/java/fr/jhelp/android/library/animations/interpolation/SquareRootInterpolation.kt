package fr.jhelp.android.library.animations.interpolation

import kotlin.math.sqrt

/**
 * Interpolation follow square root progression
 */
object SquareRootInterpolation : Interpolation
{
    /**
     * Interpolate value with following equation:
     *
     *     ___
     *    V t
     *
     * @param percent Value to interpolate
     * @return Interpolate value
     */
    override operator fun invoke(percent: Float): Float = sqrt(percent)
}
