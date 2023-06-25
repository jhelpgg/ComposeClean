
package fr.jhelp.compose.images.path

import fr.jhelp.android.library.math.Point2D


/**
 * Represents a segments with associated value at start and at end
 * @property startX Start point X
 * @property startY Start point Y
 * @property startValue Value at start position
 * @property endX End point X
 * @property endY End point Y
 * @property endValue Value at end position
 */
class Segment(val startX: Float, val startY: Float, var startValue: Float,
              val endX: Float, val endY: Float, var endValue: Float)
{
    constructor(start: Point2D, startValue: Float, end: Point2D, endValue: Float) :
            this(start.x, start.y, startValue, end.x, end.y, endValue)
}