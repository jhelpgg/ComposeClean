package fr.jhelp.compose.engine.resources.image

import android.graphics.Bitmap
import fr.jhelp.compose.images.bitmap
import fr.jhelp.compose.images.copy
import fr.jhelp.compose.images.draw
import java.util.Objects

/**
 * Image source with several images draw one over the previous.
 *
 * Thi final image size is he size of the background one, others are draw at specified position
 * in the order specified. (First element on background, second on result, third on result, ...)
 */
class ImageSourceStack(private val background: ImageSource<*>,
                       private vararg val images: ImagePosition)
    : ImageSource<ImageSourceStack>()
{
    override fun createBitmap(): Bitmap
    {
        val image = this.background.image.copy()

        image.draw { canvas, _ ->
            for (imagePosition in this.images)
            {
                canvas.bitmap(imagePosition.image.image, imagePosition.x, imagePosition.y)
            }
        }

        return image
    }

    override fun hash(): Int = Objects.hash(this.background, *this.images)

    override fun equalsSource(source: ImageSourceStack): Boolean
    {
        if (this.background != source.background || this.images.size != source.images.size)
        {
            return false
        }

        for (index in 0 until this.images.size)
        {
            if (this.images[index] != source.images[index])
            {
                return false
            }
        }

        return true
    }
}
