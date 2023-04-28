package fr.jhelp.compose.animations.keyFrame

import fr.jhelp.compose.animations.Animation
import fr.jhelp.compose.animations.interpolation.Interpolation
import fr.jhelp.compose.animations.interpolation.LinearInterpolation
import fr.jhelp.compose.collection.sortedArray

/**
 * Generic animation by key frames
 *
 * Use when animation is compose on (key,value) pair.
 *
 * This class says at that frame the object state must be that
 *
 * @param animated Modified object
 * @param fps Animation FPS
 * @param A Type of the modified object
 * @param V Type of the value change by the animation
 */
abstract class AnimationKeyFrame<A, V : Any>(private val animated: A, fps: Int) : Animation(fps)
{
    private val keyFrames = sortedArray<KeyFrame<V>>(true)
    private lateinit var startValue: V

    /**
     * Interpolate a value and change the obj state
     *
     * @param animated  Object to change
     * @param before  Value just before the wanted state
     * @param after   Value just after the wanted state
     * @param percent Percent of invoke
     */
    protected abstract fun interpolateValue(animated: A, before: V, after: V, percent: Float)

    /**
     * Give the actual value for an object
     *
     * @param animated Object we want extract the value
     * @return The actual value
     */
    protected abstract fun obtainValue(animated: A): V

    /**
     * Change object state
     *
     * @param animated Object to change
     * @param value  New state value
     */
    protected abstract fun setValue(animated: A, value: V)

    /**
     * Put an animation step for a number of milliseconds after animation started.
     *
     * The number of milliseconds is converted in number of frames depends to animation FPS
     *
     * @param milliseconds Milliseconds from the start where put sth step
     * @param value Value to give at this step
     * @param interpolation Interpolation type to use to go to this step ([LinearInterpolation] by default)
     */
    fun time(milliseconds: Int, value: V, interpolation: Interpolation = LinearInterpolation)
    {
        this.frame(this.millisecondsToFrame(milliseconds), value, interpolation)
    }


    /**
     * Put an animation step at given frame
     *
     * @param frame Number frames passed from the animation start where put the step
     * @param value Value to give at this step
     * @param interpolation Interpolation type to use to go to this step ([LinearInterpolation] by default)
     */
    fun frame(frame: Int, value: V, interpolation: Interpolation = LinearInterpolation)
    {
        if (frame < 0)
        {
            throw IllegalArgumentException("frame must be >=0, not $frame")
        }

        synchronized(this.keyFrames)
        {
            val keyFrame =
                KeyFrame(frame, value, interpolation)
            val index = this.keyFrames.indexOf(keyFrame)

            if (index < 0)
            {
                this.keyFrames.add(keyFrame)
            }
            else
            {
                this.keyFrames[index].set(value, interpolation)
            }
        }
    }

    /**
     * Initialize the animation
     */
    final override fun initialize()
    {
        this.startValue = this.obtainValue(this.animated)
    }

    /**
     * Remove all animation steps.
     *
     * Animation becomes empty
     */
    fun clear()
    {
        synchronized(this.keyFrames)
        {
            this.keyFrames.clear()
        }
    }

    /**
     * Put animation at given frame
     */
    final override fun animate(frame: Float): Boolean
    {
        synchronized(this.keyFrames)
        {
            // If there are no frame, nothing to do
            val size = this.keyFrames.size

            if (size < 1)
            {
                return false
            }

            // Compute reference frames
            val firstKeyFrame = this.keyFrames[0]
            val lastKeyFrame = this.keyFrames[size - 1]

            // If we are before the first frame (It is possible to start at a frame >0,
            // the effect is an invoke from the actual value, to the first frame)
            if (frame < firstKeyFrame.frame)
            {
                val before = this.startValue
                val after = this.keyFrames[0].value
                val percent = this.keyFrames[0].interpolation(frame / firstKeyFrame.frame)
                this.interpolateValue(this.animated, before, after, percent)
                return true
            }

            // If we are after the last frame, just position in the last frame and the animation is done
            if (frame >= lastKeyFrame.frame)
            {
                this.setValue(this.animated, this.keyFrames[size - 1].value)
                return false
            }

            // Compute the nearest frame index from the actual frame
            var framePosition = 0

            while (framePosition < size && this.keyFrames[framePosition].frame < frame)
            {
                framePosition++
            }

            // If it is the first frame, just locate to the first and the animation continue
            if (framePosition == 0)
            {
                this.setValue(this.animated, this.keyFrames[0].value)
                return true
            }

            // If it is after the last frame, locate at last and the animation is finish
            if (framePosition >= size)
            {
                this.setValue(this.animated, this.keyFrames[size - 1].value)
                return false
            }

            // Interpolate the value and animation continue
            val before = this.keyFrames[framePosition - 1]
            val after = this.keyFrames[framePosition]
            val percent = after.interpolation((frame - before.frame) / (after.frame - before.frame))
            this.interpolateValue(this.animated, before.value, after.value, percent)
        }

        return true
    }
}