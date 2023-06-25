package fr.jhelp.android.library.engine.dsl.animation

import fr.jhelp.android.library.animations.interpolation.Interpolation
import fr.jhelp.android.library.engine.scene.Position3D

internal class AnimationNodeCreatorElement(val timeMilliseconds: Int,
                                           val interpolation: Interpolation,
                                           val position: Position3D) :
        Comparable<AnimationNodeCreatorElement>
{
    override operator fun compareTo(other: AnimationNodeCreatorElement): Int =
        this.timeMilliseconds - other.timeMilliseconds
}
