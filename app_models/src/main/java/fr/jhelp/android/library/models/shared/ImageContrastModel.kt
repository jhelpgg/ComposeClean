package fr.jhelp.android.library.models.shared

import android.graphics.Bitmap
import androidx.annotation.DrawableRes
import androidx.compose.runtime.State

/**
 * Image contrast sample model
 */
interface ImageContrastModel
{
    /** Image result */
    val image: Bitmap

    /** Contrast value bounds in [[0 , 2]] */
    val contrast: State<Double>

    /**
     * Change image source
     */
    fun source(@DrawableRes image: Int)

    /**
     * Change the contrast bounds in [[0, 2]]
     */
    fun contrast(contrast: Double)
}
