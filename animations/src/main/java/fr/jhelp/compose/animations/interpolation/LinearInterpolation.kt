package fr.jhelp.compose.animations.interpolation

object LinearInterpolation : Interpolation
{
    override fun invoke(percent: Float): Float = percent
}