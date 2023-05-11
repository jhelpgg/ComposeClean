package fr.jhelp.composeclean.models.shared

import android.graphics.Bitmap
import androidx.annotation.DrawableRes

/**
 * Image with invert colors
 */
interface ImageInvertColorsModel
{
    /**
     * Image to draw result
     */
    val image: Bitmap

    /**
     * Change the image source
     */
    fun source(@DrawableRes source: Int)
}
