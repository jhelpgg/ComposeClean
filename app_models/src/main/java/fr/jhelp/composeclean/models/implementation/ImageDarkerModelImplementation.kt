package fr.jhelp.composeclean.models.implementation

import android.graphics.Bitmap
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import fr.jhelp.compose.engine.resources.ResourcesAccess
import fr.jhelp.compose.images.clear
import fr.jhelp.compose.images.darker
import fr.jhelp.compose.images.fitSpace
import fr.jhelp.android.library.math.extensions.bounds
import fr.jhelp.composeclean.models.shared.ImageDarkerModel

internal class ImageDarkerModelImplementation : ImageDarkerModel
{
    override val image: Bitmap = Bitmap.createBitmap(512, 512, Bitmap.Config.ARGB_8888)
    private val darkerMutable = mutableStateOf<Int>(0)
    override val darker: State<Int> = this.darkerMutable

    private var source = android.R.drawable.ic_delete

    override fun source(image: Int)
    {
        this.source = image
        this.update()
    }

    override fun darker(darker: Int)
    {
        this.darkerMutable.value = darker.bounds(0, 255)
        this.update()
    }

    private fun update()
    {
        val darker = this.darkerMutable.value
        val image = ResourcesAccess.obtainBitmap(this.source)
        this.image.clear(0)
        this.image.fitSpace(image)
        this.image.darker(darker)
        image.recycle()
    }
}