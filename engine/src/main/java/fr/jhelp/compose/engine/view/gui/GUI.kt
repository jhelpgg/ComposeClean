package fr.jhelp.compose.engine.view.gui

import android.graphics.Canvas
import android.graphics.Paint
import fr.jhelp.compose.collection.extensions.forEachReversed
import fr.jhelp.compose.engine.view.touch.View3DTouchOverListener

class GUI : View3DTouchOverListener
{
    private val components = ArrayList<ComponentGUI>()

    fun add(componentGUI: ComponentGUI)
    {
        synchronized(this.components)
        {
            this.components.add(componentGUI)
        }
    }

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

    override fun onDown(x: Int, y: Int)
    {
        this.componentOn(x, y)?.touchDown()

    }

    override fun onUp(x: Int, y: Int)
    {
        this.componentOn(x, y)?.touchUp()
    }

    override fun onClick(x: Int, y: Int)
    {
        this.componentOn(x, y)?.onClick()
    }

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

    private fun componentOn(x: Int, y: Int): ComponentGUI?
    {
        synchronized(this.components)
        {
            this.components.forEachReversed { component ->
                if (component.visible && x >= component.x && y >= component.y && x < component.x + component.width && y < component.y + component.height)
                {
                    return component
                }
            }
        }

        return null
    }
}