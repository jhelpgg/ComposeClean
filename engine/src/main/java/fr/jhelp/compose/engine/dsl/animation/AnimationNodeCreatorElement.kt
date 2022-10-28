package fr.jhelp.compose.engine.dsl.animation

import fr.jhelp.compose.animations.interpolation.Interpolation
import fr.jhelp.compose.engine.scene.Position3D

internal class AnimationNodeCreatorElement(val timeMilliseconds: Int,
                                           val interpolation: Interpolation,
                                           val position: Position3D) :
        Comparable<AnimationNodeCreatorElement>
{
    override operator fun compareTo(other: AnimationNodeCreatorElement): Int =
        this.timeMilliseconds - other.timeMilliseconds
}
