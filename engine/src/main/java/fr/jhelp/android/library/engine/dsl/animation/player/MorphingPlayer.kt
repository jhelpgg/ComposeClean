package fr.jhelp.android.library.engine.dsl.animation.player

import fr.jhelp.android.library.animations.Animation
import fr.jhelp.android.library.engine.dsl.AnimationMorphingReference
import fr.jhelp.android.library.engine.scene.Scene3D

/**
 * Player of animation morphing
 */
class MorphingPlayer(scene3D: Scene3D,
                     private val animationMorphingReference: AnimationMorphingReference)
    :AnimationPlayer(scene3D)
{
    override fun animation(): Animation =
        this.animationMorphingReference.animationMorphingCreator.animationMorphing

    override fun toString(): String =
        "AnimationMorphingPlayer on ${this.animationMorphingReference}"
}