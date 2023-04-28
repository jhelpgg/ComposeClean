package fr.jhelp.compose.animations.bitmap.transition

import android.graphics.Bitmap

/**
 * Transition between images one become other by choose pixels randomly
 */
class BitmapRandomTransition : BitmapTransition
{
    /**
     * Create a random transition from an image to an other
     *
     * @param bitmapStart Start image
     * @param bitmapEnd Image at end
     */
    constructor(bitmapStart: Bitmap, bitmapEnd: Bitmap) :
            super(bitmapStart, bitmapEnd)

    /**
     * Create a random transition from an image to an other
     *
     * @param bitmapStart Start image
     * @param bitmapEnd Image at end
     * @param bitmapResult Image where write the transition
     */
    internal constructor(bitmapStart: Bitmap, bitmapEnd: Bitmap, bitmapResult: Bitmap) :
            super(bitmapStart, bitmapEnd, bitmapResult)

    private val orderPixels = IntArray(this.numberPixels) { index -> index }

    /**
     * Initialize the random order
     */
    override fun initialize()
    {
        this.orderPixels.shuffle()
    }

    /**
     * Indicates how to do a transition for random progression
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
        System.arraycopy(pixelsStart, 0, pixelsResult, 0, numberPixels)
        val number = (numberPixels * factor).toInt()
        var pixel: Int

        for (index in 0 until number)
        {
            pixel = this.orderPixels[index]
            pixelsResult[pixel] = pixelsEnd[pixel]
        }
    }
}