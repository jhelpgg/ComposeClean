package fr.jhelp.compose.animations.bitmap.transition

import android.graphics.Bitmap

/**
 * Transition between images one cover other from one side to its opposite
 */
class BitmapCoverTransition : BitmapTransition
{
    private val coverWay: BitmapCoverWay

    constructor(bitmapStart: Bitmap, bitmapEnd: Bitmap, coverWay: BitmapCoverWay) :
            super(bitmapStart, bitmapEnd)
    {
        this.coverWay = coverWay
    }

    internal constructor(bitmapStart: Bitmap, bitmapEnd: Bitmap, bitmapResult: Bitmap,
                         coverWay: BitmapCoverWay) :
            super(bitmapStart, bitmapEnd, bitmapResult)
    {
        this.coverWay = coverWay
    }

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