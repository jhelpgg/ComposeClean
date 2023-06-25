package fr.jhelp.android.library.engine.resources.image

import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.Paint
import fr.jhelp.android.library.images.createBitmap
import kotlin.math.max

/**
 * Image obtain by a draw on it
 */
class ImageSourceDraw(width: Int, height: Int, private val draw: (Bitmap, Canvas, Paint) -> Unit) :
        ImageSource<ImageSourceDraw>()
{
    private val width: Int = max(1, width)
    private val height: Int = max(1, height)

    override fun createBitmap(): Bitmap = createBitmap(this.width, this.height, this.draw)

    override fun hash(): Int = System.identityHashCode(this)

    override fun equalsSource(source: ImageSourceDraw): Boolean =
        this === source
}
