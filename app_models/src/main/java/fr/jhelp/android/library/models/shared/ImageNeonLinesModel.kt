package fr.jhelp.android.library.models.shared

import android.graphics.Bitmap
import androidx.annotation.ColorInt
import androidx.compose.runtime.State

/**
 * Image with neon lines model
 */
interface ImageNeonLinesModel
{
    /** Image result */
    val image: Bitmap

    /** Neon lines' thin. Bounds in [[10, 32]] */
    val thin: State<Int>

    /**
     * Change neon lines' color
     */
    fun color(@ColorInt color: Int)

    /**
     * Change neon lines' thin.
     * Bounds in [[10, 32]]
     */
    fun thin(thin: Int)
}
