package fr.jhelp.compose.engine.view.gui

import android.graphics.Canvas
import android.graphics.Paint
import fr.jhelp.tasks.extensions.parallel

/**
 * Generic component to place on [GUI]
 */
abstract class ComponentGUI
{
    /** Define the action when click on component */
    var click: (ComponentGUI) -> Unit = {}
/** X location on parent */
    var x: Int = 0
    /** Y location on parent */
    var y: Int = 0
    /** Component's width */
    var width: Int = 16
    /** Component's height */
    var height: Int = 16
    /** Indicates if component is visible */
    var visible = true

    /**
     * Draw the component
     */
    fun draw(canvas: Canvas, paint: Paint)
    {
        canvas.save()
        canvas.clipRect(this.x.toFloat(), this.y.toFloat(),
                        this.x.toFloat() + this.width.toFloat(),
                        this.y.toFloat() + this.height.toFloat())
        canvas.translate(this.x.toFloat(), this.y.toFloat())
        this.internalDraw(canvas, paint)
        canvas.restore()
    }

    /**
     * Specific draw component
     */
    protected abstract fun internalDraw(canvas: Canvas, paint: Paint)

    internal open fun touchDown() = Unit
    internal open fun touchUp() = Unit

    internal fun onClick()
    {
        this.click.parallel(this)
    }
}