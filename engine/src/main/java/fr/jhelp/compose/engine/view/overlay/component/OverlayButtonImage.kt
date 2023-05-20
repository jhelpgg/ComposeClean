package fr.jhelp.compose.engine.view.overlay.component

import android.graphics.Canvas
import android.graphics.Paint
import android.graphics.RectF
import fr.jhelp.compose.engine.resources.image.ImageSource
import fr.jhelp.compose.engine.resources.image.ImageSourceDarker
import fr.jhelp.compose.engine.resources.image.ImageSourceDefault
import fr.jhelp.compose.engine.resources.image.ImageSourceGrey
import fr.jhelp.compose.images.Point
import fr.jhelp.tasks.extensions.parallel
import kotlin.math.min

/**
 * Button base on image
 *
 * If [toggleBehavior] is `true` the [selectListener] is called when selection changed ([click] never called)
 *
 * If [toggleBehavior] is `false` [click] called on click, [selectListener] never called
 */
class OverlayButtonImage(imageUp: ImageSource<*> = ImageSourceDefault,
                         imageDown: ImageSource<*> = ImageSourceDarker(imageUp),
                         imageDisabled: ImageSource<*> = ImageSourceGrey(imageUp),
                         horizontalAlignment: HorizontalAlignment = HorizontalAlignment.CENTER,
                         verticalAlignment: VerticalAlignment = VerticalAlignment.CENTER,
                         imageAdjustment: ImageAdjustment = ImageAdjustment.FIT,
                         enabled: Boolean = true,
                         toggleBehavior: Boolean = false,
                         selected: Boolean = false)
    : OverlayComponent()
{
    companion object
    {
        private const val FLY = 32f
    }

    /**
     * Called if
     */
    var selectListener: (selected: Boolean) -> Unit = {}

    /** Up image */
    var imageUp: ImageSource<*> = imageUp
        set(value)
        {
            if (field != value)
            {
                field = value
                this.refresh()
            }
        }

    /** Down image */
    var imageDown: ImageSource<*> = imageDown
        set(value)
        {
            if (field != value)
            {
                field = value
                this.refresh()
            }
        }


    /** Down image */
    var imageDisabled: ImageSource<*> = imageDisabled
        set(value)
        {
            if (field != value)
            {
                field = value
                this.refresh()
            }
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

    /** Indicates if button is enabled */
    var enabled: Boolean = enabled
        set(value)
        {
            if (field != value)
            {
                field = value
                this.refresh()
            }
        }

    /** Indicates if button have a toggle behavior */
    var toggleBehavior: Boolean = toggleBehavior
        set(value)
        {
            if (field != value)
            {
                field = value
                this.refresh()
            }
        }

    /** Indicates if button is selected : Have no meaning and no effect if [toggleBehavior]  is `false` */
    var selected: Boolean = selected && toggleBehavior
        set(value)
        {
            if (field != value)
            {
                field = value

                if (this.toggleBehavior)
                {
                    this.refresh()
                }
            }
        }

    private var down: Boolean = false
    internal var insideGroup = false

    override fun draw(canvas: Canvas, paint: Paint)
    {
        val (image, delta) =
            when
            {
                !this.enabled                         ->
                    Pair(this.imageDisabled.image, OverlayButtonImage.FLY / 2f)

                this.toggleBehavior && this.selected  ->
                    Pair(this.imageDown.image, OverlayButtonImage.FLY)

                this.toggleBehavior && !this.selected ->
                    Pair(this.imageUp.image, 0f)

                this.down                             ->
                    Pair(this.imageDown.image, OverlayButtonImage.FLY)

                else                                  ->
                    Pair(this.imageUp.image, 0f)
            }


        val totalWidth = this.width - this.margin.width - OverlayButtonImage.FLY * 2f
        val totalHeight = this.height - this.margin.height - OverlayButtonImage.FLY * 2f
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
                          RectF(x + delta, y + delta,
                                x + delta + width, y + delta + height),
                          paint)
    }

    override fun layout(): Unit = Unit

    override fun preferredSize(parentWidth: Int, parentHeight: Int): Point
    {
        val imageUp = this.imageUp.image
        val imageDown = this.imageDown.image
        val imageDisabled = this.imageDisabled.image

        return Point(maxOf(imageUp.width, imageDown.width, imageDisabled.width),
                     maxOf(imageUp.height, imageDown.height, imageDisabled.height))
    }

    override fun touchDown(x: Float, y: Float)
    {
        if (!this.enabled)
        {
            return
        }

        if (this.toggleBehavior)
        {
            if (this.insideGroup && this.selected)
            {
                return
            }

            this.selected = !this.selected
            this.selectListener.parallel(this.selected)
            return
        }

        this.down = true
        this.refresh()
    }

    override fun touchUp(x: Float, y: Float)
    {
        if (!this.enabled || this.toggleBehavior)
        {
            return
        }

        this.down = false
        this.refresh()
    }

    override fun doClick(x: Float, y: Float)
    {
        if (!this.enabled || this.toggleBehavior)
        {
            return
        }

        super.doClick(x, y)
    }
}
