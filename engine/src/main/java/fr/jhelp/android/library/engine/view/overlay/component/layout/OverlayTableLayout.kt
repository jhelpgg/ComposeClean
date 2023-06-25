package fr.jhelp.android.library.engine.view.overlay.component.layout

import fr.jhelp.android.library.engine.view.overlay.component.OverlayComponent
import fr.jhelp.android.library.engine.view.overlay.component.OverlayContainer
import fr.jhelp.android.library.images.Point
import kotlin.math.max

/**
 * Overlay table layout
 */
class OverlayTableLayout : OverlayContainer<OverlayTableLayoutConstraint>()
{
    override fun layout(components: List<Pair<OverlayComponent, OverlayTableLayoutConstraint>>)
    {
        if (components.isEmpty())
        {
            return
        }

        var maxCellX = 1
        var maxCellY = 1

        for ((_, constraint) in components)
        {
            maxCellX = max(maxCellX, constraint.cellX + constraint.cellWidth)
            maxCellY = max(maxCellY, constraint.cellY + constraint.cellHeight)
        }

        val cellWidth = (this.width - this.margin.width) / maxCellX
        val cellHeight = (this.height - this.margin.height) / maxCellY

        for ((component, constraint) in components)
        {
            component.x = constraint.cellX * cellWidth
            component.y = constraint.cellY * cellHeight
            component.width = constraint.cellWidth * cellWidth
            component.height = constraint.cellHeight * cellHeight
        }
    }

    override fun preferredSize(parentWidth: Int,
                               parentHeight: Int,
                               components: List<Pair<OverlayComponent, OverlayTableLayoutConstraint>>): Point
    {
        if (components.isEmpty())
        {
            return Point(1, 1)
        }

        var maxCellX = 1
        var maxCellY = 1

        for ((_, constraint) in components)
        {
            maxCellX = max(maxCellX, constraint.cellX + constraint.cellWidth)
            maxCellY = max(maxCellY, constraint.cellY + constraint.cellHeight)
        }

        val cellParentWidth = (parentWidth - this.margin.width) / maxCellX
        val cellParentHeight = (parentHeight - this.margin.height) / maxCellY
        var cellWidth = 1
        var cellHeight = 1

        for ((component, constraint) in components)
        {
            val margin = component.margin
            val preferred =
                component.preferredSize(cellParentWidth * constraint.cellWidth - margin.width,
                                        cellParentHeight * constraint.cellHeight - margin.height)
            cellWidth = max(cellWidth, preferred.x / constraint.cellWidth)
            cellHeight = max(cellHeight, preferred.y / constraint.cellHeight)
        }

        return Point(this.margin.width + maxCellX * cellWidth,
                     this.margin.height + maxCellY * cellHeight)
    }
}
