
package fr.jhelp.compose.images.path

import fr.jhelp.compose.math.Point2D


/**
 * Represents a segments with associated value at start and at end
 */
class Segment(val startX: Float, val startY: Float, var startValue: Float,
              val endX: Float, val endY: Float, var endValue: Float)
{
    constructor(start: Point2D, startValue: Float, end: Point2D, endValue: Float) :
            this(start.x, start.y, startValue, end.x, end.y, endValue)
}