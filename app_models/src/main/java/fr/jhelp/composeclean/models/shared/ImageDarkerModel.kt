package fr.jhelp.composeclean.models.shared

import android.graphics.Bitmap
import androidx.annotation.DrawableRes
import androidx.compose.runtime.State

/**
 * Image darker sample model
 */
interface ImageDarkerModel
{
    /** Image result */
    val image: Bitmap

    /** Darker value bounds in [[0 , 255]] */
    val darker: State<Int>

    /**
     * Change image source
     */
    fun source(@DrawableRes image: Int)

    /**
     * Change the contrast bounds in [[0, 255]]
     */
    fun darker(darker: Int)
}
