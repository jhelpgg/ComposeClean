package fr.jhelp.composeclean.models.implementation

import android.graphics.Bitmap
import androidx.annotation.ColorInt
import androidx.annotation.DrawableRes
import fr.jhelp.compose.engine.resources.ResourcesAccess
import fr.jhelp.compose.images.clear
import fr.jhelp.compose.images.fitSpace
import fr.jhelp.compose.images.tint
import fr.jhelp.composeclean.models.shared.ImageTintModel

internal class ImageTintModelImplementation : ImageTintModel
{
    @DrawableRes
    private var imageResource: Int = android.R.drawable.ic_delete

    @ColorInt
    private var colorTint: Int = 0xFF_CA_FE_00.toInt()

    override val image: Bitmap = Bitmap.createBitmap(512, 512, Bitmap.Config.ARGB_8888)

    override fun tintColor(color: Int)
    {
        this.colorTint = color
        this.update()
    }

    override fun source(source: Int)
    {
        this.imageResource = source
        this.update()
    }

    private fun update()
    {
        val image = ResourcesAccess.obtainBitmap(this.imageResource)
        this.image.clear(0)
        this.image.fitSpace(image)
        this.image.tint(this.colorTint)
        image.recycle()
    }
}
