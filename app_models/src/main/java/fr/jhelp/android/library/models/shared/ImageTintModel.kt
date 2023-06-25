package fr.jhelp.android.library.models.shared

import android.graphics.Bitmap
import androidx.annotation.ColorInt
import androidx.annotation.DrawableRes

/**
 * Model for image tint
 */
interface ImageTintModel
{
    /**
     * Image to draw result
     */
    val image: Bitmap

    /**
     * Change the tint color
     */
    fun tintColor(@ColorInt color: Int)

    /**
     * Change the image source
     */
    fun source(@DrawableRes source: Int)
}
