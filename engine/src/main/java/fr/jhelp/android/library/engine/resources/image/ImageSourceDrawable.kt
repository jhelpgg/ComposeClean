package fr.jhelp.android.library.engine.resources.image

import android.graphics.Bitmap
import androidx.annotation.DrawableRes
import fr.jhelp.android.library.engine.resources.ResourcesAccess

/**
 * Image from a drawable
 */
class ImageSourceDrawable(@DrawableRes private val drawable: Int) :
        ImageSource<ImageSourceDrawable>()
{
    override fun createBitmap(): Bitmap = ResourcesAccess.obtainBitmap(this.drawable)

    override fun hash(): Int = this.drawable

    override fun equalsSource(source: ImageSourceDrawable): Boolean =
        this.drawable == source.drawable
}
