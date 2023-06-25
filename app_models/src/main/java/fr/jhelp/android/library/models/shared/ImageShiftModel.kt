package fr.jhelp.android.library.models.shared

import android.graphics.Bitmap
import androidx.annotation.DrawableRes
import androidx.compose.runtime.State

/**
 * Image shift effect model
 */
interface ImageShiftModel
{
    companion object
    {
        /** Image size in width and height */
        const val SIZE = 512
    }

    /**Image result*/
    val image: Bitmap

    /**
     * Current shift on X
     *
     * Bounds to [ -[ImageShiftModel.SIZE] , [ImageShiftModel.SIZE] ]
     */
    val shiftX: State<Int>

    /**
     * Current shift on Y
     *
     * Bounds to [ -[ImageShiftModel.SIZE] , [ImageShiftModel.SIZE] ]
     */
    val shiftY: State<Int>

    /**
     * Change image source
     */
    fun source(@DrawableRes image: Int)

    /**
     * Change the shift on X
     *
     * Bounds to [ -[ImageShiftModel.SIZE] , [ImageShiftModel.SIZE] ]
     */
    fun shiftX(shiftX: Int)

    /**
     * Change the shift on Y
     *
     * Bounds to [ -[ImageShiftModel.SIZE] , [ImageShiftModel.SIZE] ]
     */
    fun shiftY(shiftY: Int)
}
