package fr.jhelp.compose.engine.view.overlay.component

import android.graphics.Canvas
import android.graphics.Paint
import fr.jhelp.compose.engine.resources.image.ImageSource
import fr.jhelp.compose.engine.resources.image.ImageSourceDefault
import fr.jhelp.compose.images.Point
import fr.jhelp.compose.images.bitmap

/**
 * Overly component fro draw an image
 */
class OverlayImage(imageSource: ImageSource = ImageSourceDefault,
                   horizontalAlignment: HorizontalAlignment = HorizontalAlignment.CENTER,
                   verticalAlignment: VerticalAlignment = VerticalAlignment.CENTER)
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

    override fun draw(canvas: Canvas, paint: Paint)
    {
        val image = this.imageSource.image

        val x: Float =
            when (this.horizontalAlignment)
            {
                HorizontalAlignment.LEFT   -> this.margin.left.toFloat()
                HorizontalAlignment.CENTER -> this.margin.left + (this.width - this.margin.width - image.width) / 2f
                HorizontalAlignment.RIGHT  -> this.width - this.margin.right - image.width
            }

        val y: Float =
            when (this.verticalAlignment)
            {
                VerticalAlignment.TOP    -> this.margin.top.toFloat()
                VerticalAlignment.CENTER -> this.margin.top + (this.height - this.margin.height - image.height) / 2f
                VerticalAlignment.BOTTOM -> this.height - this.margin.bottom - image.height
            }

        canvas.bitmap(image, x, y)
    }

    override fun layout(): Unit = Unit

    override fun preferredSize(parentWidth: Int, parentHeight: Int): Point =
        Point(this.imageSource.image.width, this.imageSource.image.height)
}
