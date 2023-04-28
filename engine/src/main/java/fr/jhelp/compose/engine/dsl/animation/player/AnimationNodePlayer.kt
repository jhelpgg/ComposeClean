package fr.jhelp.compose.engine.dsl.animation.player

import fr.jhelp.compose.animations.Animation
import fr.jhelp.compose.engine.dsl.AnimationNodeReference
import fr.jhelp.compose.engine.scene.Scene3D

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