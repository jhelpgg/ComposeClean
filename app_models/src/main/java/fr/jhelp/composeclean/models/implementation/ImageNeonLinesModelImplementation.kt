package fr.jhelp.composeclean.models.implementation

import android.graphics.Bitmap
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import fr.jhelp.android.library.images.clear
import fr.jhelp.android.library.images.draw
import fr.jhelp.android.library.images.neonLine
import fr.jhelp.android.library.math.COLOR_WHITE
import fr.jhelp.android.library.math.extensions.bounds
import fr.jhelp.composeclean.models.DEFAULT_COLOR
import fr.jhelp.composeclean.models.shared.ImageNeonLinesModel

internal class ImageNeonLinesModelImplementation : ImageNeonLinesModel
{
    override val image: Bitmap = Bitmap.createBitmap(512, 512, Bitmap.Config.ARGB_8888)
    private val thinMutable = mutableStateOf(15)
    override val thin: State<Int> = this.thinMutable

    private var color = DEFAULT_COLOR

    override fun color(color: Int)
    {
        this.color = color
        this.update()
    }

    override fun thin(thin: Int)
    {
        this.thinMutable.value = thin.bounds(10, 32)
        this.update()
    }

    private fun update()
    {
        this.image.clear(COLOR_WHITE)
        this.image.draw { canvas, paint ->
            val thin = this.thinMutable.value
            paint.color = this.color
            canvas.neonLine(16f, 500f, 500f, 500f, thin, paint)
            canvas.neonLine(500f, 500f, 500f, 16f, thin, paint)
            canvas.neonLine(500f, 16f, 16f, 500f, thin, paint)
            canvas.neonLine(16f, 16f, 400f, 256f, thin, paint)
        }
    }
}