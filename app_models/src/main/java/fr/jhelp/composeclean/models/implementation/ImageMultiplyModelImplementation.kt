package fr.jhelp.composeclean.models.implementation

import android.graphics.Bitmap
import fr.jhelp.compose.engine.resources.ResourcesAccess
import fr.jhelp.compose.images.clear
import fr.jhelp.compose.images.fitSpace
import fr.jhelp.compose.images.multiply
import fr.jhelp.composeclean.models.shared.ImageMultiplyModel

internal class ImageMultiplyModelImplementation : ImageMultiplyModel
{
    override val image: Bitmap = Bitmap.createBitmap(512, 512, Bitmap.Config.ARGB_8888)
    private var firstImage = android.R.drawable.ic_delete
    private var secondImage = android.R.drawable.ic_delete

    override fun firstImage(firstImage: Int)
    {
        this.firstImage = firstImage
        this.update()
    }

    override fun secondImage(secondImage: Int)
    {
        this.secondImage = secondImage
        this.update()
    }

    private fun update()
    {
        val firstImage = ResourcesAccess.obtainBitmap(this.firstImage, 512, 512)
        val secondImage = ResourcesAccess.obtainBitmap(this.secondImage, 512, 512)
        this.image.clear(0)
        this.image.fitSpace(firstImage)
        this.image.multiply(secondImage)
        firstImage.recycle()
        secondImage.recycle()
    }
}