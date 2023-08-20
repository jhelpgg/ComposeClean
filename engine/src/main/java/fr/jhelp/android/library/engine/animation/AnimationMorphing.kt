package fr.jhelp.android.library.engine.animation

import fr.jhelp.android.library.animations.Animation
import fr.jhelp.android.library.animations.interpolation.Interpolation
import fr.jhelp.android.library.animations.interpolation.LinearInterpolation
import fr.jhelp.android.library.engine.scene.morphing.Morphing
import kotlin.math.max
import kotlin.math.min

/**
 * Animation based on morphing
 */
class AnimationMorphing(private val morphing: Morphing,
                        durationInMilliseconds: Int,
                        private val interpolation: Interpolation = LinearInterpolation,
                        fps: Int = 25) : Animation(fps)
{
    private val durationInFrame =
        max(1f, (durationInMilliseconds.toFloat() * this.fps.toFloat()) / 1000f)

    override fun animate(frame: Float): Boolean
    {
        val percent = min(1f, frame / this.durationInFrame)
        this.morphing.percent = this.interpolation(percent)
        return percent < 1f
    }
}
