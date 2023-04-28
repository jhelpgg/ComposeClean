package fr.jhelp.compose.engine.view.gui

import android.graphics.Canvas
import android.graphics.Paint
import androidx.annotation.ColorInt

class ButtonGUI(@ColorInt val normalColor: Int, @ColorInt val downColor: Int) : ComponentGUI()
{
    private var currentColor = this.normalColor

    override fun internalDraw(canvas: Canvas, paint: Paint)
    {
        paint.color = this.currentColor
        paint.style = Paint.Style.FILL
        canvas.drawRoundRect(1f, 1f,
                             this.width.toFloat() - 1f, this.height.toFloat() - 1f,
                             this.width.toFloat() / 8f, this.height.toFloat() / 8f,
                             paint)
    }

    override fun touchDown()
    {
        this.currentColor = this.downColor
    }

    override fun touchUp()
    {
        this.currentColor = this.normalColor
    }
}