package fr.jhelp.compose.animations.bitmap

import android.graphics.Bitmap
import fr.jhelp.compose.animations.Animation
import fr.jhelp.compose.animations.bitmap.transition.BitmapCoverTransition
import fr.jhelp.compose.animations.bitmap.transition.BitmapCoverWay
import fr.jhelp.compose.animations.bitmap.transition.BitmapMeltTransition
import fr.jhelp.compose.animations.bitmap.transition.BitmapRandomTransition
import fr.jhelp.compose.animations.interpolation.Interpolation
import fr.jhelp.compose.animations.interpolation.LinearInterpolation
import fr.jhelp.compose.images.fitSpace

/**
 * Animation that do transition between bitmaps
 * @param bitmapAtStart Bitmap at start
 * @param width Result bitmap width
 * @param height Result bitmap height
 * @param fps Animation FPS
 * @param autoRecycle Indicates that bitmaps can be recycle when no more need (They aren't use any where else)
 */
class AnimationBitmap(bitmapAtStart: Bitmap, width: Int, height: Int, fps: Int,
                      val autoRecycle: Boolean = true) : Animation(fps)
{
    /** Bitmap to use as start image in next transition */
    private var currentBitmap = bitmapAtStart

    /** Last animation frame add */
    private var currentFrame = 0

    /** First animation element */
    internal var firstElement: BitmapAnimationElement? = null

    /**Bitmap result with the animation*/
    val resultBitmap = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888)

    /** Embed bitmap animation */
    private val bitmapAnimation = BitmapAnimation(this, this.fps)

    init
    {
        this.bitmapAnimation.frame(0, BitmapAnimationElement(null))
        this.resultBitmap.fitSpace(bitmapAtStart)
    }

    /**
     * Change animation frame position
     */
    fun setAnimateFrame(frame: Float): Boolean =
        this.animate(frame)

    /**
     * Update animation frame position
     */
    override fun animate(frame: Float): Boolean =
        this.bitmapAnimation.setAnimationFrame(frame)

    /**
     * Add transition image
     * @param numberFrame Number frame to go to the given bitmap form the previous one
     * @param bitmap Target bitmap
     * @param transition Transition type ([AnimationBitmapTransition.MELT] by default)
     * @param interpolation Interpolation to use ([LinearInterpolation] by default)
     */
    fun appendTransitionIn(numberFrame: Int, bitmap: Bitmap,
                           transition: AnimationBitmapTransition = AnimationBitmapTransition.MELT,
                           interpolation: Interpolation = LinearInterpolation)
    {
        if (numberFrame <= 0)
        {
            throw IllegalArgumentException("Number of transition frame must be at least 1, not $numberFrame")
        }

        val bitmapTransition =
            when (transition)
            {
                AnimationBitmapTransition.MELT                ->
                    BitmapMeltTransition(this.currentBitmap, bitmap, this.resultBitmap)

                AnimationBitmapTransition.COVER_BOTTOM_TO_TOP ->
                    BitmapCoverTransition(this.currentBitmap, bitmap, this.resultBitmap,
                                          BitmapCoverWay.BOTTOM_TO_TOP)

                AnimationBitmapTransition.COVER_LEFT_TO_RIGHT ->
                    BitmapCoverTransition(this.currentBitmap, bitmap, this.resultBitmap,
                                          BitmapCoverWay.LEFT_TO_RIGHT)

                AnimationBitmapTransition.COVER_RIGHT_TO_LEFT ->
                    BitmapCoverTransition(this.currentBitmap, bitmap, this.resultBitmap,
                                          BitmapCoverWay.RIGHT_TO_LEFT)

                AnimationBitmapTransition.COVER_TOP_TO_BOTTOM ->
                    BitmapCoverTransition(this.currentBitmap, bitmap, this.resultBitmap,
                                          BitmapCoverWay.TOP_TO_BOTTOM)

                AnimationBitmapTransition.RANDOM              ->
                    BitmapRandomTransition(this.currentBitmap, bitmap, this.resultBitmap)
            }

        val bitmapAnimationElement = BitmapAnimationElement(bitmapTransition)

        if (this.firstElement == null)
        {
            this.firstElement = bitmapAnimationElement
        }

        this.currentFrame += numberFrame
        this.bitmapAnimation.frame(this.currentFrame, bitmapAnimationElement, interpolation)

        if (this.autoRecycle)
        {
            this.currentBitmap.recycle()
        }

        this.currentBitmap = bitmap
    }
}