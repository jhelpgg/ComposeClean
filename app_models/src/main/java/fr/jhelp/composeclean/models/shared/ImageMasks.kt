package fr.jhelp.composeclean.models.shared

import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.Paint
import fr.jhelp.compose.images.clear
import fr.jhelp.compose.images.createBitmap
import fr.jhelp.compose.images.fitSpace
import fr.jhelp.android.library.math.COLOR_BLACK

/**
 * Possible masks
 */
enum class ImageMasks(private val draw: (Bitmap, Canvas, Paint) -> Unit)
{
    /** Small circle mask */
    SMALL_CIRCLE({ bitmap, canvas, paint ->
                     bitmap.clear(0)
                     paint.style = Paint.Style.FILL
                     paint.color = COLOR_BLACK
                     canvas.drawCircle(256f, 256f, 128f, paint)
                 }),

    /** Rectangle and oval mask */
    RECTANGLE_OVAL({ bitmap, canvas, paint ->
                       bitmap.clear(0)
                       paint.style = Paint.Style.FILL
                       paint.color = COLOR_BLACK
                       canvas.drawRect(32f, 32f, 300f, 250f, paint)
                       canvas.drawOval(64f, 256f, 500f, 400f, paint)
                   }),

    /** Large circle mask */
    LARGE_CIRCLE({ bitmap, canvas, paint ->
                     bitmap.clear(0)
                     paint.style = Paint.Style.STROKE
                     paint.strokeWidth = 64f
                     paint.color = COLOR_BLACK
                     canvas.drawCircle(256f, 256f, 192f, paint)
                 }),

    /** Path mask */
    PATH({ bitmap, canvas, paint ->
             bitmap.clear(0)
             paint.style = Paint.Style.FILL
             paint.color = COLOR_BLACK
             val path = android.graphics.Path()
             path.moveTo(200f, 12f)
             path.lineTo(300f, 256f)
             path.lineTo(200f, 500f)
             path.lineTo(100f, 256f)
             path.close()
             canvas.drawPath(path, paint)
         })
    ;

    /** Mask bitmap */
    val bitmap: Bitmap by lazy { createBitmap(512, 512, this.draw) }

    /** Mask preview */
    val preview: Bitmap by lazy {
        createBitmap(64, 64) { bitmap, _, _ ->
            bitmap.fitSpace(this.bitmap)
        }
    }
}