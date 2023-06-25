package fr.jhelp.compose.engine.resources.image

import android.graphics.Bitmap
import fr.jhelp.android.library.images.copy
import fr.jhelp.android.library.images.grey

/**
 * Image source grey version
 */
class ImageSourceGrey(private val imageSource: ImageSource<*>) : ImageSource<ImageSourceGrey>()
{
    override fun createBitmap(): Bitmap
    {
        val image = this.imageSource.image.copy()
        image.grey()
        return image
    }

    override fun hash(): Int = this.imageSource.hashCode()

    override fun equalsSource(source: ImageSourceGrey): Boolean =
        this.imageSource == source.imageSource
}
