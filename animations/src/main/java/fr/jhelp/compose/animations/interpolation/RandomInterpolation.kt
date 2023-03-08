package fr.jhelp.compose.animations.interpolation

import fr.jhelp.compose.math.extensions.nul
import fr.jhelp.compose.math.extensions.same
import fr.jhelp.compose.math.random

/**
 * Interpolation with random progression
 */
object RandomInterpolation : Interpolation
{
    /**
     * Interpolate value with random progression
     *
     * @param percent Value to interpolate
     * @return Interpolate value
     */
    override operator fun invoke(percent: Float) =
        when
        {
            percent.nul || percent.same(1f) -> percent
            else                            -> random(percent, 1f)
        }
}