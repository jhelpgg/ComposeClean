package fr.jhelp.compose.animations.interpolation

/**
 * Interpolation with hesitation effect
 */
object HesitateInterpolation : Interpolation
{
    /**
     * Interpolate value with hesitation effect
     *
     * @param percent Value to interpolate
     * @return Interpolate value
     */
    override operator fun invoke(percent: Float): Float
    {
        val value = 2f * percent - 1f
        return 0.5f * (value * value * value + 1f)
    }
}