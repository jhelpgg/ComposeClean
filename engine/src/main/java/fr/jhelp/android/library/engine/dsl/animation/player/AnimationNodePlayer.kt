package fr.jhelp.android.library.engine.dsl.animation.player

import fr.jhelp.android.library.animations.Animation
import fr.jhelp.android.library.engine.dsl.AnimationNodeReference
import fr.jhelp.android.library.engine.scene.Scene3D

/**
 * Player associate to a node animation
 */
class AnimationNodePlayer(scene3D: Scene3D,
                          private val animationNodeReference: AnimationNodeReference)
    : AnimationPlayer(scene3D)
{
    /**
     * Create the animation to play.
     */
    override fun animation(): Animation =
        this.animationNodeReference.animationNodeCreator.animation

    override fun toString(): String =
        "AnimationNodePlayer on ${this.animationNodeReference}"
}