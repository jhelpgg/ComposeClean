package fr.jhelp.compose.engine.animation.keyFrame

import fr.jhelp.compose.animations.keyFrame.AnimationKeyFrame
import fr.jhelp.compose.engine.annotations.OpenGLThread
import fr.jhelp.compose.engine.scene.Node3D
import fr.jhelp.compose.engine.scene.Position3D

/**
 * Animation that move a 3D Node
 */
class AnimationNode3D(node: Node3D, fps: Int = 25) :
    AnimationKeyFrame<Node3D, Position3D>(node, fps)
{
    @OpenGLThread
    override fun interpolateValue(animated: Node3D, before: Position3D, after: Position3D,
                                  percent: Float)
    {
        val anti = 1f - percent
        val interpolated = Position3D()

        interpolated.x = before.x * anti + after.x * percent
        interpolated.y = before.y * anti + after.y * percent
        interpolated.z = before.z * anti + after.z * percent

        interpolated.angleX = before.angleX * anti + after.angleX * percent
        interpolated.angleY = before.angleY * anti + after.angleY * percent
        interpolated.angleZ = before.angleZ * anti + after.angleZ * percent

        interpolated.scaleX = before.scaleX * anti + after.scaleX * percent
        interpolated.scaleY = before.scaleY * anti + after.scaleY * percent
        interpolated.scaleZ = before.scaleZ * anti + after.scaleZ * percent

        animated.position = interpolated
    }

    @OpenGLThread
    override fun obtainValue(animated: Node3D): Position3D = animated.position.copy()

    @OpenGLThread
    override fun setValue(animated: Node3D, value: Position3D)
    {
        animated.position = value.copy()
    }
}