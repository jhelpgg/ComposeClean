package fr.jhelp.android.library.engine.view.overlay.component.layout

import fr.jhelp.android.library.engine.view.overlay.component.OverlayConstraint
import kotlin.math.max

/**
 * Constraint for table layout
 */
class OverlayTableLayoutConstraint(cellX: Int, cellY: Int,
                                   cellWidth: Int = 1, cellHeight: Int = 1) : OverlayConstraint
{
    /** Cell position in X */
    val cellX = max(0, cellX)

    /** Cell position in Y */
    val cellY = max(0, cellY)

    /** Cell width */
    val cellWidth = max(1, cellWidth)

    /** Cell height */
    val cellHeight = max(1, cellHeight)
}

