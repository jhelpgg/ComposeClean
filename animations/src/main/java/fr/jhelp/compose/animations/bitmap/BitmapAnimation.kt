package fr.jhelp.compose.animations.bitmap

import fr.jhelp.compose.animations.keyFrame.AnimationKeyFrame

internal class BitmapAnimation(animationBitmap: AnimationBitmap, fps: Int) :
    AnimationKeyFrame<AnimationBitmap, BitmapAnimationElement>(animationBitmap, fps)
{
    override fun interpolateValue(animated: AnimationBitmap, before: BitmapAnimationElement,
                                  after: BitmapAnimationElement, percent: Float)
    {
        after.transition?.updatePercent(percent)
    }

    override fun obtainValue(animated: AnimationBitmap): BitmapAnimationElement =
        animated.firstElement ?: BitmapAnimationElement(null)

    override fun setValue(animated: AnimationBitmap, value: BitmapAnimationElement)
    {
        value.transition?.updatePercent(100f)
    }

    internal fun setAnimationFrame(frame: Float) =
        this.animate(frame)
}