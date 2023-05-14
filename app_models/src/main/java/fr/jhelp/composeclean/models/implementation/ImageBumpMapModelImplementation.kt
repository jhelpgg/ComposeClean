package fr.jhelp.composeclean.models.implementation

import android.graphics.Bitmap
import fr.jhelp.compose.engine.resources.ResourcesAccess
import fr.jhelp.compose.images.clear
import fr.jhelp.compose.images.createBumped
import fr.jhelp.compose.images.fitSpace
import fr.jhelp.composeclean.models.shared.ImageBumpMapModel

internal class ImageBumpMapModelImplementation : ImageBumpMapModel
{
    override val image: Bitmap = Bitmap.createBitmap(512, 512, Bitmap.Config.ARGB_8888)

    private var source = android.R.drawable.ic_delete
    private var bumpImage = android.R.drawable.ic_delete

    override fun source(source: Int)
    {
        this.source = source
        this.update()
    }

    override fun bumpImage(bumpImage: Int)
    {
        this.bumpImage = bumpImage
        this.update()
    }

    private fun update()
    {
        val source = ResourcesAccess.obtainBitmap(this.source, 512, 512)
        val bumpImage = ResourcesAccess.obtainBitmap(this.bumpImage, 512, 512)
        val bumpMapImage = createBumped(source, bumpImage)
        this.image.clear(0)
        this.image.fitSpace(bumpMapImage)
        source.recycle()
        bumpImage.recycle()
        bumpMapImage.recycle()
    }
}