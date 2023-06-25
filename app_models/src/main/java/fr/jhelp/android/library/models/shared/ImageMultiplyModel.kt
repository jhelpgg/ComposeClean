package fr.jhelp.android.library.models.shared

import android.graphics.Bitmap
import androidx.annotation.DrawableRes

/**
 * Multiply two images sample
 */
interface ImageMultiplyModel
{
    /** Image result */
    val image: Bitmap

    /**
     * First image to multiply
     */
    fun firstImage(@DrawableRes firstImage: Int)

    /**
     * Second image to multiply
     */
    fun secondImage(@DrawableRes secondImage: Int)
}