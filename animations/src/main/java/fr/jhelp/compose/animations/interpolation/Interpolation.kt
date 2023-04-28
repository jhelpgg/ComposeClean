package fr.jhelp.compose.animations.interpolation

/**
 * Represents an interpolation
 */
interface Interpolation
{
    /**
     * Interpolate a [0, 1] value
     *
     * The function **f(x)** MUST meet :
     *
     * * f(0) = 0
     * * f(1) = 1
     *
     * @param percent Value (in [0, 1]) to interpolate
     * @return Interpolation result (in [0, 1])
     */
    operator fun invoke(percent: Float): Float
}