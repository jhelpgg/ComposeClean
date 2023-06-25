package fr.jhelp.composeclean.models.implementation

import android.graphics.Bitmap
import fr.jhelp.compose.engine.resources.ResourcesAccess
import fr.jhelp.android.library.images.clear
import fr.jhelp.android.library.images.fitSpace
import fr.jhelp.android.library.images.invertColors
import fr.jhelp.composeclean.models.shared.ImageInvertColorsModel

internal class ImageInvertColorsModelImplementation : ImageInvertColorsModel
{
    override val image: Bitmap = Bitmap.createBitmap(512, 512, Bitmap.Config.ARGB_8888)

    override fun source(source: Int)
    {
        val image = ResourcesAccess.obtainBitmap(source)
        this.image.clear(0)
        this.image.fitSpace(image)
        this.image.invertColors()
        image.recycle()
    }
}
