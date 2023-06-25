package fr.jhelp.composeclean.models.implementation

import android.graphics.Bitmap
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import fr.jhelp.android.library.engine.resources.ResourcesAccess
import fr.jhelp.android.library.images.clear
import fr.jhelp.android.library.images.contrast
import fr.jhelp.android.library.images.fitSpace
import fr.jhelp.android.library.math.extensions.bounds
import fr.jhelp.composeclean.models.shared.ImageContrastModel

internal class ImageContrastModelImplementation : ImageContrastModel
{
    override val image: Bitmap = Bitmap.createBitmap(512, 512, Bitmap.Config.ARGB_8888)
    private val contrastMutable = mutableStateOf<Double>(1.0)
    override val contrast: State<Double> = this.contrastMutable

    private var source = android.R.drawable.ic_delete

    override fun source(image: Int)
    {
        this.source = image
        this.update()
    }

    override fun contrast(contrast: Double)
    {
        this.contrastMutable.value = contrast.bounds(0.0, 2.0)
        this.update()
    }

    private fun update()
    {
        val contrast = this.contrastMutable.value
        val image = ResourcesAccess.obtainBitmap(this.source)
        this.image.clear(0)
        this.image.fitSpace(image)
        this.image.contrast(contrast)
        image.recycle()
    }
}