package fr.jhelp.android.library.models.shared

import android.graphics.Bitmap
import androidx.annotation.DrawableRes

/**
 * Add two images sample
 */
interface ImageAddModel
{
    /** Image result */
    val image: Bitmap

    /**
     * First image to add
     */
    fun firstImage(@DrawableRes firstImage: Int)

    /**
     * Second image to add
     */
    fun secondImage(@DrawableRes secondImage: Int)
}