package fr.jhelp.compose.engine.resources.image

import android.graphics.Bitmap
import androidx.annotation.DrawableRes
import fr.jhelp.compose.engine.resources.ResourcesAccess

/**
 * Image from a drawable
 */
class ImageSourceDrawable(@DrawableRes private val drawable: Int) : ImageSource()
{
    override fun createBitmap(): Bitmap = ResourcesAccess.obtainBitmap(this.drawable)
}
