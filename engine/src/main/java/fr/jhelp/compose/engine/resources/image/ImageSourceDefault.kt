package fr.jhelp.compose.engine.resources.image

import android.graphics.Bitmap
import fr.jhelp.compose.engine.resources.ResourcesAccess

/**
 * Default image source
 */
object ImageSourceDefault : ImageSource<ImageSourceDefault>()
{
    override fun createBitmap(): Bitmap = ResourcesAccess.defaultBitmap()

    override fun hash(): Int = 0

    override fun equalsSource(source: ImageSourceDefault): Boolean = true
}
