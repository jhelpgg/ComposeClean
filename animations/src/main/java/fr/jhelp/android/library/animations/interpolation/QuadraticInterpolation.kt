package fr.jhelp.android.library.animations.interpolation

import fr.jhelp.android.library.math.quadratic

/**
 * Quadratic interpolation
 * @param control Control point
 */
class QuadraticInterpolation(private val control: Float = 0.25f) : Interpolation
{
    /**
     * Compute quadratic interpolation
     *
     * @param percent Value to interpolate
     * @return Interpolate value
     */
    override operator fun invoke(percent: Float): Float =
        quadratic(0.0f, this.control, 1.0f, percent)
}