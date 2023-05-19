package fr.jhelp.compose.engine.resources.image

import android.graphics.Bitmap
import fr.jhelp.compose.engine.resources.ResourcesAccess

/**
 * Default image source
 */
object ImageSourceDefault : ImageSource()
{
    override fun createBitmap(): Bitmap = ResourcesAccess.defaultBitmap()
}
