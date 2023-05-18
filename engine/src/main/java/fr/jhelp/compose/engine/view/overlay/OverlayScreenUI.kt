package fr.jhelp.compose.engine.view.overlay

import android.graphics.Canvas
import android.graphics.Paint
import android.util.Log
import fr.jhelp.compose.engine.view.overlay.component.OverlayComponent
import fr.jhelp.compose.engine.view.overlay.component.OverlayText

/**
 * Overlay screen with UI
 */
class OverlayScreenUI : OverlayScreen
{
    private var refresh: () -> Unit = {}
    private var width = 1f
    private var height = 1f

    /**
     * The main component
     */
    var mainComponent: OverlayComponent = OverlayText()
        set(value)
        {
            value.refresh = this.refresh
            value.x = 0f
            value.y = 0f
            value.width = this.width
            value.height = this.height
            value.layout()
            field = value
            this.refresh()
        }

    override fun registerRefresh(refresh: () -> Unit)
    {
        this.refresh = refresh
        this.mainComponent.refresh = refresh
    }

    override fun size(width: Int, height: Int)
    {
        this.width = width.toFloat()
        this.height = height.toFloat()
        this.mainComponent.x = 0f
        this.mainComponent.y = 0f
        this.mainComponent.width = this.width
        this.mainComponent.height = this.height
        this.mainComponent.layout()
    }

    override fun draw(canvas: Canvas, paint: Paint)
    {
        if (this.mainComponent.visible)
        {
            this.mainComponent.draw(canvas, paint)
        }
    }

    override fun touchDown(x: Float, y: Float)
    {
        if (this.mainComponent.visible)
        {
            this.mainComponent.touchDown(x, y)
        }
    }

    override fun touchUp(x: Float, y: Float)
    {
        if (this.mainComponent.visible)
        {
            this.mainComponent.touchUp(x, y)
        }
    }

    override fun touchMove(xPrevious: Float, yPrevious: Float, x: Float, y: Float)
    {
        if (this.mainComponent.visible)
        {
            this.mainComponent.touchMove(xPrevious, yPrevious, x, y)
        }
    }

    override fun touchClick(x: Float, y: Float)
    {
        if (this.mainComponent.visible)
        {
            this.mainComponent.doClick(x, y)
        }
    }
}