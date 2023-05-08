package fr.jhelp.composeclean.models.implementation

import android.graphics.Bitmap
import fr.jhelp.compose.engine.resources.ResourcesAccess
import fr.jhelp.compose.images.clear
import fr.jhelp.compose.images.fitSpace
import fr.jhelp.compose.images.grey
import fr.jhelp.composeclean.models.shared.ImageGreyModel

internal class ImageGreyModelImplementation : ImageGreyModel
{
    override val image: Bitmap = Bitmap.createBitmap(512, 512, Bitmap.Config.ARGB_8888)

    override fun source(source: Int)
    {
        val image = ResourcesAccess.obtainBitmap(source)
        this.image.clear(0)
        this.image.fitSpace(image)
        this.image.grey()
        image.recycle()
    }
}
