package fr.jhelp.compose.animations.interpolation

/**
 * Linear interpolation
 */
object LinearInterpolation : Interpolation
{
    override fun invoke(percent: Float): Float = percent
}