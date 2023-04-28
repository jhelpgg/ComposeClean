package fr.jhelp.compose.engine.view.gui

import android.graphics.Canvas
import android.graphics.Paint
import fr.jhelp.tasks.extensions.parallel

abstract class ComponentGUI
{
    var click: (ComponentGUI) -> Unit = {}

    var x: Int = 0
    var y: Int = 0
    var width: Int = 16
    var height: Int = 16
    var visible = true

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

    protected abstract fun internalDraw(canvas: Canvas, paint: Paint)

    internal open fun touchDown() = Unit
    internal open fun touchUp() = Unit

    internal fun onClick()
    {
        this.click.parallel(this)
    }
}