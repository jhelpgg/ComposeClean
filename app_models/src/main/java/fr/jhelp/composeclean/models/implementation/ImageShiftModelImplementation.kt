package fr.jhelp.composeclean.models.implementation

import android.graphics.Bitmap
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import fr.jhelp.compose.engine.resources.ResourcesAccess
import fr.jhelp.android.library.images.clear
import fr.jhelp.android.library.images.fitSpace
import fr.jhelp.android.library.images.shift
import fr.jhelp.android.library.math.extensions.bounds
import fr.jhelp.composeclean.models.shared.ImageShiftModel

internal class ImageShiftModelImplementation : ImageShiftModel
{
    override val image: Bitmap =
        Bitmap.createBitmap(ImageShiftModel.SIZE, ImageShiftModel.SIZE, Bitmap.Config.ARGB_8888)
    private val shiftXMutable = mutableStateOf<Int>(0)
    override val shiftX: State<Int> = this.shiftXMutable
    private val shiftYMutable = mutableStateOf<Int>(0)
    override val shiftY: State<Int> = this.shiftYMutable

    private var source = android.R.drawable.ic_delete

    override fun source(image: Int)
    {
        this.source = image
        this.update()
    }

    override fun shiftX(shiftX: Int)
    {
        this.shiftXMutable.value = shiftX.bounds(-ImageShiftModel.SIZE, ImageShiftModel.SIZE)
        this.update()
    }

    override fun shiftY(shiftY: Int)
    {
        this.shiftYMutable.value = shiftY.bounds(-ImageShiftModel.SIZE, ImageShiftModel.SIZE)
        this.update()
    }

    private fun update()
    {
        val shiftX = this.shiftXMutable.value
        val shiftY = this.shiftYMutable.value
        val image = ResourcesAccess.obtainBitmap(this.source)
        this.image.clear(0)
        this.image.fitSpace(image)
        this.image.shift(shiftX, shiftY)
        image.recycle()
    }
}