package fr.jhelp.compose.animations.interpolation

/**
 * Interpolation follow square progression
 */
object SquareInterpolation : Interpolation
{
    /**
     * Interpolate value with following equation:
     *
     *    t²
     *
     * @param percent Value to interpolate
     * @return Interpolate value
     */
    override operator fun invoke(percent: Float) = percent * percent
}
