package fr.jhelp.composeclean.models.implementation

import android.graphics.Bitmap
import fr.jhelp.android.library.engine.resources.ResourcesAccess
import fr.jhelp.android.library.images.clear
import fr.jhelp.android.library.images.fitSpace
import fr.jhelp.android.library.images.mask
import fr.jhelp.composeclean.models.shared.ImageMaskModel
import fr.jhelp.composeclean.models.shared.ImageMasks

internal class ImageMaskModelImplementation : ImageMaskModel
{
    override val bitmap: Bitmap = Bitmap.createBitmap(512, 512, Bitmap.Config.ARGB_8888)

    private var image = android.R.drawable.ic_delete
    private var mask = ImageMasks.SMALL_CIRCLE

    override fun source(image: Int)
    {
        this.image = image
        this.update()
    }

    override fun mask(mask: ImageMasks)
    {
        this.mask = mask
        this.update()
    }

    private fun update()
    {
        val image = ResourcesAccess.obtainBitmap(this.image)
        val maskImage = this.mask.bitmap
        this.bitmap.clear(0)
        this.bitmap.fitSpace(image)
        this.bitmap.mask(maskImage, 0, 0, 512, 512)
        image.recycle()
    }
}
