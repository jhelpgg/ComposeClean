package fr.jhelp.android.library.animations.bitmap.transition

import android.graphics.Bitmap

/**
 * Transition between images one cover other from one side to its opposite
 */
class BitmapCoverTransition : BitmapTransition
{
    /** Cover transition way */
    private val coverWay: BitmapCoverWay

    /**
     * Create a cover transition from an image to an other
     *
     * @param bitmapStart Start image
     * @param bitmapEnd Image at end
     * @param coverWay Cover way
     */
    constructor(bitmapStart: Bitmap, bitmapEnd: Bitmap, coverWay: BitmapCoverWay) :
            super(bitmapStart, bitmapEnd)
    {
        this.coverWay = coverWay
    }

    /**
     * Create a cover transition from an image to an other
     *
     * @param bitmapStart Start image
     * @param bitmapEnd Image at end
     * @param bitmapResult Image where write the transition
     * @param coverWay Cover way
     */
    internal constructor(bitmapStart: Bitmap, bitmapEnd: Bitmap, bitmapResult: Bitmap,
                         coverWay: BitmapCoverWay) :
            super(bitmapStart, bitmapEnd, bitmapResult)
    {
        this.coverWay = coverWay
    }

    /**
     * Indicates how to do a transition for cover transition
     * @param width Images width
     * @param height Images height
     * @param numberPixels Number of pixels on images
     * @param factor Progression percentage
     * @param pixelsStart Pixels of start image
     * @param pixelsEnd Pixels of end image
     * @param pixelsResult Pixels where write the transition result
     */
    override fun computeTransition(width: Int, height: Int, numberPixels: Int, factor: Float,
                                   pixelsStart: IntArray, pixelsEnd: IntArray,
                                   pixelsResult: IntArray)
    {
        when (this.coverWay)
        {
            BitmapCoverWay.LEFT_TO_RIGHT ->
                this.leftToRight(width, height, numberPixels,
                                 factor,
                                 pixelsStart, pixelsEnd, pixelsResult)
            BitmapCoverWay.RIGHT_TO_LEFT ->
                this.leftToRight(width, height, numberPixels,
                                 1.0f - factor,
                                 pixelsEnd, pixelsStart, pixelsResult)
            BitmapCoverWay.TOP_TO_BOTTOM ->
                this.topToBottom(width, height, numberPixels,
                                 factor,
                                 pixelsStart, pixelsEnd, pixelsResult)
            BitmapCoverWay.BOTTOM_TO_TOP ->
                this.topToBottom(width, height, numberPixels,
                                 1.0f - factor,
                                 pixelsEnd, pixelsStart, pixelsResult)
        }
    }

    /**
     * Cover left to right animation
     *
     * @param width Images width
     * @param height Images height
     * @param numberPixels Number of pixels in images
     * @param factor Transition factor in [0, 1]
     * @param pixelsToCopy Pixels of start image
     * @param pixelsToAnimate Pixels of final image
     * @param pixelsResult Pixels where write the result
     */
    private fun leftToRight(width: Int, height: Int, numberPixels: Int,
                            factor: Float,
                            pixelsToCopy: IntArray, pixelsToAnimate: IntArray,
                            pixelsResult: IntArray)
    {
        System.arraycopy(pixelsToCopy, 0, pixelsResult, 0, numberPixels)

        val limitX = (width * factor).toInt()

        if (limitX > 0)
        {
            var startX = 0

            for (y in 0 until height)
            {
                System.arraycopy(pixelsToAnimate, startX, pixelsResult, startX, limitX)
                startX += width
            }
        }
    }

    /**
     * Cover top to bottom animation
     *
     * @param width Images width
     * @param height Images height
     * @param numberPixels Number of pixels in images
     * @param factor Transition factor in [0, 1]
     * @param pixelsToCopy Pixels of start image
     * @param pixelsToAnimate Pixels of final image
     * @param pixelsResult Pixels where write the result
     */
    private fun topToBottom(width: Int, height: Int, numberPixels: Int,
                            factor: Float,
                            pixelsToCopy: IntArray, pixelsToAnimate: IntArray,
                            pixelsResult: IntArray)
    {
        System.arraycopy(pixelsToCopy, 0, pixelsResult, 0, numberPixels)

        val limitY = (height * factor).toInt() * width

        if (limitY > 0)
        {
            System.arraycopy(pixelsToAnimate, 0, pixelsResult, 0, limitY)
        }
    }
}