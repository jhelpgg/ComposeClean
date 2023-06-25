package fr.jhelp.android.library.images

import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.Paint
import android.graphics.Rect
import android.graphics.RectF
import fr.jhelp.android.library.images.crawler.SegmentCrawler
import kotlin.math.min

/**
 * Draw neon line on a canvas.
 *
 * It use the color defines by [Paint.getColor]
 *
 * Then thin represents the neon thin, 15 is a good value for minimal neon effect
 */
fun Canvas.neonLine(x1: Float, y1: Float, x2: Float, y2: Float, thin: Int, paint: Paint)
{
    if (thin < 1)
    {
        return
    }

    if (thin == 1)
    {
        this.drawLine(x1, y1, x2, y2, paint)
        return
    }

    val startColor = paint.color
    val style = paint.style
    val stroke = paint.strokeWidth
    paint.style = Paint.Style.STROKE

    var thinLocal = thin
    var color = startColor
    val alpha = color and ALPHA_MASK
    var red = color.red
    var green = color.green
    var blue = color.blue
    var y = computeY(red, green, blue)
    val u = computeU(red, green, blue)
    val v = computeV(red, green, blue)

    do
    {
        paint.color = color
        paint.strokeWidth = thinLocal.toFloat()
        this.drawLine(x1, y1, x2, y2, paint)
        y *= 1.2
        red = computeRed(y, u, v)
        green = computeGreen(y, u, v)
        blue = computeBlue(y, u, v)
        color = alpha or (red shl 16) or (green shl 8) or blue
        thinLocal = thinLocal shr 1
    }
    while (thinLocal > 1)

    paint.color = startColor
    paint.style = style
    paint.strokeWidth = stroke
}

/**
 * Draw a bitmap on place its up left corner at given coordinates
 */
fun Canvas.bitmap(bitmap: Bitmap, x: Float, y: Float)
{
    this.drawBitmap(bitmap, null, RectF(x, y, x + bitmap.width, y + bitmap.height), null)
}

/**
 * Draw a bitmap on place its center at given coordinates
 */
fun Canvas.center(bitmap: Bitmap, x: Float, y: Float)
{
    this.bitmap(bitmap, x - bitmap.width / 2f, y - bitmap.height / 2f)
}

/**
 * Draw a bitmap several times on follow given segment.
 *
 * The step is the number of pixels between each repetition
 */
fun Canvas.repeatOnLine(bitmap: Bitmap, x1: Float, y1: Float, x2: Float, y2: Float,
                        step: Int = min(bitmap.width, bitmap.height))
{
    val segmentCrawler = SegmentCrawler(x1.toInt(), y1.toInt(), x2.toInt(), y2.toInt())

    while (segmentCrawler.hasNext)
    {
        val (x, y) = segmentCrawler.next(step)
        this.center(bitmap, x.toFloat(), y.toFloat())
    }
}

/**
 * Draw bitmap to toke all place in given rectangle
 */
fun Canvas.fitRectangle(bitmap: Bitmap, x: Int, y: Int, width: Int, height: Int)
{
    this.drawBitmap(bitmap,
                    Rect(0, 0, bitmap.width, bitmap.height),
                    Rect(x, y, width, height),
                    null)
}
