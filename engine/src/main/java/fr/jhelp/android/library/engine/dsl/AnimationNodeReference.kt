package fr.jhelp.android.library.engine.dsl

import fr.jhelp.android.library.engine.dsl.animation.AnimationNodeCreator

/**
 * Reference to an animation for a node
 */
class AnimationNodeReference internal constructor(nodeReference: NodeReference)
{
    internal val animationNodeCreator: AnimationNodeCreator =
        AnimationNodeCreator(nodeReference)

    override fun toString(): String=
        "AnimationNodeReference : ${this.animationNodeCreator}"
}
