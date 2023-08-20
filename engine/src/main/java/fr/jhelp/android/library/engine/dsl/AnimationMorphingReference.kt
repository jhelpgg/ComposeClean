package fr.jhelp.android.library.engine.dsl

import fr.jhelp.android.library.engine.dsl.animation.AnimationMorphingCreator
import fr.jhelp.android.library.engine.scene.morphing.Morphing

/**
 * Reference on animation based on [Morphing] object
 *
 * The node reference must reference a [Morphing] object.
 * Call [NodeTreeCreator.morphing] on the node reference before create this reference.
 */
class AnimationMorphingReference internal constructor(morphingReference: NodeReference)
{
    internal val animationMorphingCreator =
        AnimationMorphingCreator(morphingReference.node as Morphing)

    override fun toString(): String =
        "AnimationMorphingReference : ${this.animationMorphingCreator}"
}
