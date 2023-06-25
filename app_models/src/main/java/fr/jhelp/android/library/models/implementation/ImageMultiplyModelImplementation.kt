package fr.jhelp.android.library.models.implementation

import android.graphics.Bitmap
import fr.jhelp.android.library.engine.resources.ResourcesAccess
import fr.jhelp.android.library.images.clear
import fr.jhelp.android.library.images.fitSpace
import fr.jhelp.android.library.images.multiply
import fr.jhelp.android.library.models.shared.ImageMultiplyModel

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