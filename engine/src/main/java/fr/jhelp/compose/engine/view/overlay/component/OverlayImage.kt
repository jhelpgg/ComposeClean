package fr.jhelp.compose.engine.view.overlay.component

import android.graphics.Canvas
import android.graphics.Paint
import android.graphics.RectF
import fr.jhelp.compose.engine.resources.image.ImageSource
import fr.jhelp.compose.engine.resources.image.ImageSourceDefault
import fr.jhelp.compose.images.Point
import kotlin.math.min

/**
 * Overly component fro draw an image
 */
class OverlayImage(imageSource: ImageSource = ImageSourceDefault,
                   horizontalAlignment: HorizontalAlignment = HorizontalAlignment.CENTER,
                   verticalAlignment: VerticalAlignment = VerticalAlignment.CENTER,
                   imageAdjustment: ImageAdjustment = ImageAdjustment.FIT)
    : OverlayComponent()
{
    /** Image source to draw */
    var imageSource: ImageSource = imageSource
        set(value)
        {
            field = value
            this.refresh()
        }

    /** Image horizontal alignment */
    var horizontalAlignment: HorizontalAlignment = horizontalAlignment
        set(value)
        {
            if (field != value)
            {
                field = value
                this.refresh()
            }
        }

    /** Image vertical alignment */
    var verticalAlignment: VerticalAlignment = verticalAlignment
        set(value)
        {
            if (field != value)
            {
                field = value
                this.refresh()
            }
        }

    /** Image adjustment in component */
    var imageAdjustment: ImageAdjustment = imageAdjustment
        set(value)
        {
            if (field != value)
            {
                field = value
                this.refresh()
            }
        }

    override fun draw(canvas: Canvas, paint: Paint)
    {
        val image = this.imageSource.image
        val totalWidth = this.width - this.margin.width
        val totalHeight = this.height - this.margin.height
        var width = image.width.toFloat()
        var height = image.height.toFloat()

        when (this.imageAdjustment)
        {
            ImageAdjustment.CUT            -> Unit

            ImageAdjustment.FIT            ->
            {
                width = totalWidth
                height = totalHeight
            }

            ImageAdjustment.FIT_PROPORTION ->
            {
                val widthFactor = totalWidth / width
                val heightFactor = totalHeight / height
                val factor = min(widthFactor, heightFactor)
                width *= factor
                height *= factor
            }
        }

        val x: Float =
            when (this.horizontalAlignment)
            {
                HorizontalAlignment.LEFT   -> this.margin.left.toFloat()
                HorizontalAlignment.CENTER -> this.margin.left + (this.width - this.margin.width - width) / 2f
                HorizontalAlignment.RIGHT  -> this.width - this.margin.right - width
            }

        val y: Float =
            when (this.verticalAlignment)
            {
                VerticalAlignment.TOP    -> this.margin.top.toFloat()
                VerticalAlignment.CENTER -> this.margin.top + (this.height - this.margin.height - height) / 2f
                VerticalAlignment.BOTTOM -> this.height - this.margin.bottom - height
            }

        canvas.drawBitmap(image, null,
                          RectF(x, y, x + width, y + height),
                          paint)
    }

    override fun layout(): Unit = Unit

    override fun preferredSize(parentWidth: Int, parentHeight: Int): Point =
        Point(this.imageSource.image.width, this.imageSource.image.height)
}
