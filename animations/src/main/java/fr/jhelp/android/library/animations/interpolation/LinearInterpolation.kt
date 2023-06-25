package fr.jhelp.android.library.animations.interpolation

/**
 * Linear interpolation
 */
object LinearInterpolation : Interpolation
{
    override fun invoke(percent: Float): Float = percent
}