package fr.jhelp.android.library.engine.resources.image

import android.graphics.Bitmap
import fr.jhelp.android.library.images.copy
import fr.jhelp.android.library.images.darker
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
