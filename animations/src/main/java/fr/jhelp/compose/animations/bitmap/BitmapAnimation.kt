package fr.jhelp.compose.animations.bitmap

import fr.jhelp.compose.animations.keyFrame.AnimationKeyFrame

/**
 * Animation inside a bitmap
 */
internal class BitmapAnimation(animationBitmap: AnimationBitmap, fps: Int) :
    AnimationKeyFrame<AnimationBitmap, BitmapAnimationElement>(animationBitmap, fps)
{
    /**
     * Interpolate from an element to an other
     */
    override fun interpolateValue(animated: AnimationBitmap, before: BitmapAnimationElement,
                                  after: BitmapAnimationElement, percent: Float)
    {
        after.transition?.updatePercent(percent)
    }

    /**
     * Extract element from animation bitmap
     */
    override fun obtainValue(animated: AnimationBitmap): BitmapAnimationElement =
        animated.firstElement ?: BitmapAnimationElement(null)

    /**
     * Put animation at specified value
     */
    override fun setValue(animated: AnimationBitmap, value: BitmapAnimationElement)
    {
        value.transition?.updatePercent(100f)
    }

    /**
     * Change current animation frame and update it
     */
    internal fun setAnimationFrame(frame: Float) =
        this.animate(frame)
}