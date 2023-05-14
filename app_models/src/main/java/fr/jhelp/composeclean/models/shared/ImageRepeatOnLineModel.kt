package fr.jhelp.composeclean.models.shared

import android.graphics.Bitmap
import androidx.compose.runtime.State

/**
 * Model for show repeat on line
 */
interface ImageRepeatOnLineModel
{
    /** Image result */
    val image: Bitmap

    /** Step between repeat bounds in [[1, 64]]*/
    val step: State<Int>

    /**
     * Change the image repeat
     */
    fun source(image: ImageMasks)

    /**
     * Change the repeat step.
     * Bounds in [[1, 64]]
     */
    fun step(step: Int)
}