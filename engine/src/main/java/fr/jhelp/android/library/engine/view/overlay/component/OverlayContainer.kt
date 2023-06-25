package fr.jhelp.android.library.engine.view.overlay.component

import android.graphics.Canvas
import android.graphics.Paint
import android.graphics.PointF
import fr.jhelp.android.library.images.Point

/**
 * Container of components
 */
abstract class OverlayContainer<CONSTRAINT : OverlayConstraint> : OverlayComponent()
{
    private val components = ArrayList<Pair<OverlayComponent, CONSTRAINT>>()
    private val refreshListener: () -> Unit = { this.refresh() }

    /**
     * Add component to layout
     */
    fun add(component: OverlayComponent, constraint: CONSTRAINT)
    {
        component.refresh = this.refreshListener
        this.components.add(Pair<OverlayComponent, CONSTRAINT>(component, constraint))
        this.layout()
    }

    final override fun layout()
    {
        for ((component, _) in this.components)
        {
            component.layout()
        }

        this.layout(this.components)
        this.refresh()
    }

    final override fun draw(canvas: Canvas, paint: Paint)
    {
        for ((component, _) in this.components)
        {
            if (component.visible)
            {
                canvas.save()
                canvas.clipRect(component.x,
                                component.y,
                                component.x + component.width,
                                component.y + component.height)
                canvas.translate(component.x, component.y)
                component.draw(canvas, paint)
                canvas.restore()
            }
        }
    }

    final override fun touchDown(x: Float, y: Float)
    {
        val (component, position) = this.obtainComponent(x, y) ?: return
        component.touchDown(position.x, position.y)
    }

    final override fun touchUp(x: Float, y: Float)
    {
        val (component, position) = this.obtainComponent(x, y) ?: return
        component.touchUp(position.x, position.y)
    }

    final override fun touchMove(xPrevious: Float, yPrevious: Float, x: Float, y: Float)
    {
        val previous = this.obtainComponent(xPrevious, yPrevious)
        val actual = this.obtainComponent(x, y)

        if (previous != null && actual != null && previous.first == actual.first)
        {
            actual.first.touchMove(previous.second.x, previous.second.y,
                                   actual.second.x, actual.second.y)
            return
        }

        if (previous != null)
        {
            previous.first.touchUp(previous.second.x, previous.second.y)
        }

        if (actual != null)
        {
            actual.first.touchDown(actual.second.x, actual.second.y)
        }
    }

    final override fun doClick(x: Float, y: Float)
    {
        val (component, position) = this.obtainComponent(x, y) ?: return
        component.doClick(position.x, position.y)
    }

    final override fun preferredSize(parentWidth: Int, parentHeight: Int): Point =
        this.preferredSize(parentWidth, parentHeight, this.components)

    /**
     * Layout components
     */
    protected abstract fun layout(components: List<Pair<OverlayComponent, CONSTRAINT>>)

    /**
     * Compute preferred size
     */
    protected abstract fun preferredSize(parentWidth: Int,
                                         parentHeight: Int,
                                         components: List<Pair<OverlayComponent, CONSTRAINT>>): Point

    private fun obtainComponent(x: Float, y: Float): Pair<OverlayComponent, PointF>?
    {
        for ((component, _) in this.components)
        {
            if (component.visible)
            {
                val xx = x - component.x
                val yy = y - component.y

                if (xx >= 0f && xx < component.width && yy >= 0f && yy < component.height)
                {
                    return Pair<OverlayComponent, PointF>(component, PointF(xx, yy))
                }
            }
        }

        return null
    }
}
