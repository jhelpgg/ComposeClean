package fr.jhelp.compose.engine.view.touch

import android.graphics.Canvas
import android.graphics.Paint

interface View3DTouchOverListener
{
    fun onRefresh(canvas: Canvas, paint: Paint)

    fun onDown(x: Int, y: Int)

    fun onUp(x: Int, y: Int)

    fun onClick(x: Int, y: Int)

    fun onMove(previousX: Int, previousY: Int, x: Int, y: Int)

    fun dirtyListener(dirty: () -> Unit) = Unit
}
