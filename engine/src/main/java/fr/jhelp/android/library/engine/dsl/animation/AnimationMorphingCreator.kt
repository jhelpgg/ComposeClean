package fr.jhelp.android.library.engine.dsl.animation

import fr.jhelp.android.library.animations.interpolation.Interpolation
import fr.jhelp.android.library.animations.interpolation.LinearInterpolation
import fr.jhelp.android.library.engine.animation.AnimationMorphing
import fr.jhelp.android.library.engine.scene.morphing.Morphing

/**
 * Create an animation morphing
 */
class AnimationMorphingCreator internal constructor(private val morphing: Morphing)
{
    internal val animationMorphing: AnimationMorphing by lazy {
        AnimationMorphing(this.morphing, this.durationInMilliseconds, this.interpolation)
    }

    /** Animation interpolation */
    var interpolation: Interpolation = LinearInterpolation

    /** animation duration in milliseconds */
    var durationInMilliseconds: Int = 1024

    override fun toString(): String =
        "AnimationMorphingCreator : ${this.morphing}"

}

