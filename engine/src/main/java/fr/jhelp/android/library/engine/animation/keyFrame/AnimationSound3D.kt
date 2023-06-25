package fr.jhelp.android.library.engine.animation.keyFrame

import fr.jhelp.android.library.animations.keyFrame.AnimationKeyFrame
import fr.jhelp.android.library.engine.annotations.OpenGLThread
import fr.jhelp.android.library.engine.scene.Sound3D
import fr.jhelp.android.library.math.Point3D

/**
 * Animation move a 3D sound
 */
class AnimationSound3D(sound: Sound3D, fps: Int = 25) :
        AnimationKeyFrame<Sound3D, Point3D>(sound, fps)
{
    @OpenGLThread
    override fun interpolateValue(animated: Sound3D, before: Point3D, after: Point3D,
                                  percent: Float)
    {
        val anti = 1f - percent
        val x = before.x * anti + after.x * percent
        val y = before.y * anti + after.y * percent
        val z = before.z * anti + after.z * percent
        animated.position(x, y, z)
    }

    @OpenGLThread
    override fun obtainValue(animated: Sound3D): Point3D =
        Point3D(animated.x, animated.y, animated.z)

    @OpenGLThread
    override fun setValue(animated: Sound3D, value: Point3D)
    {
        animated.position(value.x, value.y, value.z)
    }
}