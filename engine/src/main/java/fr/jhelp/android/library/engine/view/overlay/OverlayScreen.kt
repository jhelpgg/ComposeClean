package fr.jhelp.android.library.engine.view.overlay

import android.graphics.Canvas
import android.graphics.Paint

/**
 * Screen draw in overlay
 */
interface OverlayScreen
{
    /**
     * Register a listener to call for refresh the screen with last changes
     */
    fun registerRefresh(refresh: () -> Unit)

    /**
     * Called each time overlay size changed
     */
    fun size(width: Int, height: Int)

    /**
     * Called when screen have to be drawn
     */
    fun draw(canvas: Canvas, paint: Paint)

    /**
     * Called when user put finger in overlay
     */
    fun touchDown(x: Float, y: Float)

    /**
     * Called when user remove finger in overlay
     */
    fun touchUp(x: Float, y: Float)

    /**
     * Called when user move/slide finger on overlay
     */
    fun touchMove(xPrevious: Float, yPrevious: Float, x: Float, y: Float)

    /**
     * Called when user click on overlay (Touch then release in short time)
     */
    fun touchClick(x: Float, y: Float)
}
