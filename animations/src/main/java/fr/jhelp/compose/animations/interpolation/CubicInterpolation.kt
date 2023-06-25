package fr.jhelp.compose.animations.interpolation

import fr.jhelp.android.library.math.cubic

/**
 * Cubic interpolation
 * @param firstControl First control point
 * @param secondControl Second control point
 */
class CubicInterpolation(private val firstControl: Float = 0.1f,
                         private val secondControl: Float = 0.9f) : Interpolation
{
    /**
     * Compute cubic interpolation
     *
     * @param percent Value to interpolate
     * @return Interpolate value
     */
    override operator fun invoke(percent: Float): Float =
        cubic(0.0,
              this.firstControl.toDouble(),
              this.secondControl.toDouble(),
              1.0,
              percent.toDouble()).toFloat()
}