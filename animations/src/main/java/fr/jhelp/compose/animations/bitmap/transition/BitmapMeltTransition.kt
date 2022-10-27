package fr.jhelp.compose.animations.bitmap.transition

import android.graphics.Bitmap
import androidx.core.graphics.alpha
import androidx.core.graphics.blue
import androidx.core.graphics.green
import androidx.core.graphics.red

/**
 * Transition between images one melt to become other
 */
class BitmapMeltTransition : BitmapTransition
{
    constructor(bitmapStart: Bitmap, bitmapEnd: Bitmap) :
            super(bitmapStart, bitmapEnd)

    internal constructor(bitmapStart: Bitmap, bitmapEnd: Bitmap, bitmapResult: Bitmap) :
            super(bitmapStart, bitmapEnd, bitmapResult)

    override fun computeTransition(width: Int, height: Int, numberPixels: Int, factor: Float,
                                   pixelsStart: IntArray, pixelsEnd: IntArray,
                                   pixelsResult: IntArray)
    {
        val rotcaf = 1f - factor
        var colorStart: Int
        var colorEnd: Int
        var alpha: Int
        var red: Int
        var green: Int
        var blue: Int

        for (pixel in 0 until numberPixels)
        {
            colorStart = pixelsStart[pixel]
            colorEnd = pixelsEnd[pixel]
            alpha = (colorStart.alpha * rotcaf + colorEnd.alpha * factor).toInt()
            red = (colorStart.red * rotcaf + colorEnd.red * factor).toInt()
            green = (colorStart.green * rotcaf + colorEnd.green * factor).toInt()
            blue = (colorStart.blue * rotcaf + colorEnd.blue * factor).toInt()
            pixelsResult[pixel] = (alpha shl 24) or (red shl 16) or (green shl 8) or blue
        }
    }
}