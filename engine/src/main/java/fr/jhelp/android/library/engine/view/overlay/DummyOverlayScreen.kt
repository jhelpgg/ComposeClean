package fr.jhelp.android.library.engine.view.overlay

import android.graphics.Canvas
import android.graphics.Paint

/**
 * Screen that does nothing
 */
object DummyOverlayScreen : OverlayScreen
{
    override fun registerRefresh(refresh: () -> Unit)
    {
    }

    override fun size(width: Int, height: Int)
    {
    }

    override fun draw(canvas: Canvas, paint: Paint)
    {
    }

    override fun touchDown(x: Float, y: Float)
    {
    }

    override fun touchUp(x: Float, y: Float)
    {
    }

    override fun touchMove(xPrevious: Float, yPrevious: Float, x: Float, y: Float)
    {
    }

    override fun touchClick(x: Float, y: Float)
    {
    }
}
