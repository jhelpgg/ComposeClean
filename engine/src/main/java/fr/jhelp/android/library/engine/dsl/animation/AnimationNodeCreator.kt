package fr.jhelp.android.library.engine.dsl.animation

import fr.jhelp.android.library.animations.interpolation.Interpolation
import fr.jhelp.android.library.animations.interpolation.LinearInterpolation
import fr.jhelp.android.library.collection.sortedArray
import fr.jhelp.android.library.engine.animation.keyFrame.AnimationNode3D
import fr.jhelp.android.library.engine.dsl.NodeReference
import fr.jhelp.android.library.engine.scene.Position3D
import fr.jhelp.android.library.math.anotations.Milliseconds
import kotlin.math.max

/**
 * Create on animation for a node
 */
class AnimationNodeCreator internal constructor(private val nodeReference: NodeReference)
{
    internal val animation: AnimationNode3D by lazy {
        val animationNode3D = AnimationNode3D(this.nodeReference.node)

        for (element in this.elements)
        {
            animationNode3D.time(element.timeMilliseconds,
                                 element.position,
                                 element.interpolation)
        }

        this.elements.clear()
        animationNode3D
    }

    private val elements = sortedArray<AnimationNodeCreatorElement>()

    /**
     * Define the node position for an animation time
     *
     * @param atTimeMilliseconds Number of milliseconds since the animation started
     * @param withInterpolation Interpolation used to reach the position ([LinearInterpolation] by default)
     * @param betAtPosition Desired node position at the specified moment
     */
    fun atTime(@Milliseconds atTimeMilliseconds: Int,
               withInterpolation: Interpolation = LinearInterpolation,
               betAtPosition: Position3D.() -> Unit)
    {
        val position3D = Position3D()
        betAtPosition(position3D)
        this.elements.add(AnimationNodeCreatorElement(max(0, atTimeMilliseconds),
                                                      withInterpolation,
                                                      position3D))
    }

    override fun toString(): String =
        "AnimationNodeCreator : ${this.nodeReference}"
}