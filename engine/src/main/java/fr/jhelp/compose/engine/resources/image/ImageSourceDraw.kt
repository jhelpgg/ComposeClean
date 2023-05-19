package fr.jhelp.compose.engine.resources.image

import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.Paint
import kotlin.math.max

/**
 * Image obtain by a draw on it
 */
class ImageSourceDraw(width: Int, height: Int, private val draw: (Bitmap, Canvas, Paint) -> Unit) :
        ImageSource()
{
    private val width: Int = max(1, width)
    private val height: Int = max(1, height)

    override fun createBitmap(): Bitmap =
        fr.jhelp.compose.images.createBitmap(this.width, this.height, this.draw)
}
