package fr.jhelp.compose.engine.resources.image

import android.graphics.Bitmap

/**
 * Image comes from a source
 */
sealed class ImageSource
{
    /** Image carry by the source */
    val image: Bitmap by lazy { this.createBitmap() }

    /**
     * Create the image form the source
     */
    protected abstract fun createBitmap(): Bitmap
}