package fr.jhelp.composeclean.models.implementation

import android.graphics.Bitmap
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import fr.jhelp.compose.images.clear
import fr.jhelp.compose.images.draw
import fr.jhelp.compose.images.repeatOnLine
import fr.jhelp.compose.math.COLOR_WHITE
import fr.jhelp.compose.math.extensions.bounds
import fr.jhelp.composeclean.models.shared.ImageMasks
import fr.jhelp.composeclean.models.shared.ImageRepeatOnLineModel

internal class ImageRepeatOnLineModelImplementation : ImageRepeatOnLineModel
{
    override val image: Bitmap = Bitmap.createBitmap(512, 512, Bitmap.Config.ARGB_8888)
    private val stepMutable = mutableStateOf(32)
    override val step: State<Int> = this.stepMutable
    private var source = ImageMasks.SMALL_CIRCLE

    override fun source(image: ImageMasks)
    {
        this.source = image
        this.update()
    }

    override fun step(step: Int)
    {
        this.stepMutable.value = step.bounds(1, 64)
        this.update()
    }

    private fun update()
    {
        this.image.clear(COLOR_WHITE)
        this.image.draw { canvas, _ ->
            canvas.repeatOnLine(this.source.preview, 0f, 0f, 512f, 512f, this.stepMutable.value)
        }
    }
}