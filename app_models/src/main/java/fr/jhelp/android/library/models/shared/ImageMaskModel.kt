package fr.jhelp.android.library.models.shared

import android.graphics.Bitmap
import androidx.annotation.DrawableRes

/**
 * Mask applied on image model
 */
interface ImageMaskModel
{
    /** Result image */
    val bitmap: Bitmap

    /**
     * Change the image source
     */
    fun source(@DrawableRes image: Int)

    /**
     * Change the mask applied
     */
    fun mask(mask: ImageMasks)
}
