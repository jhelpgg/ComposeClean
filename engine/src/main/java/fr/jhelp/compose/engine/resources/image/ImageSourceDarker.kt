package fr.jhelp.compose.engine.resources.image

import android.graphics.Bitmap
import fr.jhelp.compose.images.copy
import fr.jhelp.compose.images.darker
import java.util.Objects

/**
 * Darker image source
 */
class ImageSourceDarker(private val source: ImageSource<*>, private val darker: Int = 64) :
        ImageSource<ImageSourceDarker>()
{
    override fun createBitmap(): Bitmap
    {
        val image = this.source.image.copy()
        image.darker(this.darker)
        return image
    }

    override fun hash(): Int = Objects.hash(this.source, this.darker)

    override fun equalsSource(source: ImageSourceDarker): Boolean =
        this.source == source.source && this.darker == source.darker

}
