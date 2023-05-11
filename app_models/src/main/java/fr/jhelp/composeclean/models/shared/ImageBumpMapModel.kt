package fr.jhelp.composeclean.models.shared

import android.graphics.Bitmap
import androidx.annotation.DrawableRes

/**
 * Image with bump map effect
 */
interface ImageBumpMapModel
{
    /** Image result */
    val image: Bitmap

    /**
     * Change image source
     */
    fun source(@DrawableRes source: Int)

    /**
     * Change bump image
     */
    fun bumpImage(@DrawableRes bumpImage: Int)
}
