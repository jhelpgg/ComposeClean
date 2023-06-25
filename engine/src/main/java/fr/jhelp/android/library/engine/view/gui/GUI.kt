package fr.jhelp.android.library.engine.view.gui

import android.graphics.Canvas
import android.graphics.Paint
import fr.jhelp.android.library.collection.extensions.forEachReversed
import fr.jhelp.android.library.engine.view.touch.View3DTouchOverListener

/**
 * Overlay 3D with 2D components
 */
class GUI : View3DTouchOverListener
{
    private val components = ArrayList<ComponentGUI>()

    /**
     * Add component in the overlay
     */
    fun add(componentGUI: ComponentGUI)
    {
        synchronized(this.components)
        {
            this.components.add(componentGUI)
        }
    }

    /**
     * Called when image overlay should be refreshed.
     *
     * It redraw the overlay
     *
     * @param canvas Canvas where draw
     * @param paint Paint to use with canvas
     */
    override fun onRefresh(canvas: Canvas, paint: Paint)
    {
        synchronized(this.components)
        {
            for (component in this.components)
            {
                if (component.visible)
                {
                    component.draw(canvas, paint)
                }
            }
        }
    }

    /**
     * Called when user touch the overlay
     */
    override fun onDown(x: Int, y: Int)
    {
        this.componentOn(x, y)?.touchDown()

    }

    /**
     * Called when user remove finger from overlay
     */
    override fun onUp(x: Int, y: Int)
    {
        this.componentOn(x, y)?.touchUp()
    }

    /**
     * Called when user clicked on overlay
     */
    override fun onClick(x: Int, y: Int)
    {
        this.componentOn(x, y)?.onClick()
    }

    /**
     * Called when user slide is finger in overlay
     */
    override fun onMove(previousX: Int, previousY: Int, x: Int, y: Int)
    {
        val previousComponent = this.componentOn(previousX, previousY)
        val component = this.componentOn(x, y)

        if (previousComponent != component)
        {
            previousComponent?.touchUp()
            component?.touchDown()
        }
    }

    /**
     * Search component under the given position
     */
    private fun componentOn(x: Int, y: Int): ComponentGUI?
    {
        synchronized(this.components)
        {
            this.components.forEachReversed { component ->
                if (component.visible && x >= component.x && y >= component.y && x < component.x + component.width && y < component.y + component.height)
                {
                    return@componentOn component
                }
            }
        }

        return null
    }
}