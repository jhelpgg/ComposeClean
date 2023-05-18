package fr.jhelp.compose.engine.view.overlay.component

import android.graphics.Canvas
import android.graphics.Paint
import fr.jhelp.compose.images.Point

/**
 * Generic overlay UI component
 *
 * @property refresh Call it to refresh the component
 */
abstract class OverlayComponent
{
    /** Call it to refresh the component. It is set by the system */
    var refresh: () -> Unit = {}

    /** X position in parent */
    var x: Float = 0f

    /** Y position in parent */
    var y: Float = 0f

    /** Width in pixels */
    var width: Float = 1f

    /** Height in pixels */
    var height: Float = 1f

    /** Indicates if component is visible */
    var visible: Boolean = true
        set(value)
        {
            if (field != value)
            {
                field = value
                this.refresh()
            }
        }

    /** React to click. If the component is [visible]. */
    var click: (OverlayComponent, x: Float, y: Float) -> Unit = { _, _, _ -> }

    /** Component margin */
    var margin = Margin()
        set(value)
        {
            if (field != value)
            {
                field = value
                this.refresh()
            }
        }

    /**
     * Draw the component in given canvas with given paint.
     *
     * Don't assume any value for paint, set the ones you need for the component.
     *
     * The canvas is translated so that the draw of the component is always in (0,0) <-> ([width], [height])
     */
    abstract fun draw(canvas: Canvas, paint: Paint)

    /**
     * Called when size changed or layout need to be refresh
     */
    abstract fun layout()

    /**
     * Compute preferred size.
     *
     * Have to take margin in count while computing
     */
    abstract fun preferredSize(parentWidth: Int, parentHeight: Int): Point

    /**
     * Called when user touch the component. If the component is [visible].
     *
     * Does nothing by default. Override it to have reaction
     */
    open fun touchDown(x: Float, y: Float): Unit = Unit

    /**
     * Called when user release the component. If the component is [visible].
     *
     * Does nothing by default. Override it to have reaction
     */
    open fun touchUp(x: Float, y: Float): Unit = Unit


    /**
     * Called when user move finger over the component. If the component is [visible].
     *
     * Does nothing by default. Override it to have reaction
     */
    open fun touchMove(xPrevious: Float, yPrevious: Float, x: Float, y: Float): Unit = Unit

    /**
     * Do a click on component
     *
     * By default it call the click action
     */
    open fun doClick(x: Float, y: Float)
    {
        this.click(this, x, y)
    }
}
