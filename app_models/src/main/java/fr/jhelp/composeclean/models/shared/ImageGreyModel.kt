package fr.jhelp.composeclean.models.shared

import android.graphics.Bitmap
import androidx.annotation.DrawableRes
import androidx.compose.runtime.State

/**
 * Image in grey model
 */
interface ImageGreyModel
{
    /**
     * Image to draw result
     */
    val image : Bitmap

    /**
     * Change the image source
     */
    fun source(@DrawableRes source:Int)
}
