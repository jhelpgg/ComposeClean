package fr.jhelp.android.library.engine.view.touch

import android.graphics.Canvas
import android.graphics.Paint

/**
 * Listener of touch events on overlay
 */
interface View3DTouchOverListener
{
    /***
     * Called when refresh is need to repaint the overlay
     */
    fun onRefresh(canvas: Canvas, paint: Paint)

    /**
     * Called when touch down the overlay
     */
    fun onDown(x: Int, y: Int)

    /**
     * Called when touch up the overlay
     */
    fun onUp(x: Int, y: Int)

    /**
     * Called when click on the overlay
     */
    fun onClick(x: Int, y: Int)

    /**
     * Called when slide on overlay
     */
    fun onMove(previousX: Int, previousY: Int, x: Int, y: Int)

    /**
     * Register a listener to call for force a refresh
     */
    fun dirtyListener(dirty: () -> Unit): Unit = Unit
}
