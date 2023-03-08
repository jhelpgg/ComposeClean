/*
 *  <h1>License :</h1> <br/>
 * The following code is deliver as is. <br/>
 *  You can use, modify, the code as your need for any usage.<br/>
 *  But you can't do any action that avoid me or other person use, modify this code.<br/>
 *  The code is free for usage and modification, you can't change that fact.
 */

package fr.jhelp.compose.images.crawler

import fr.jhelp.compose.images.Point
import kotlin.math.abs
import kotlin.math.sign

/**
 * Follow a segment from start point to end point.
 *
 * Use [hasNext] to know if end is reach or not
 *
 * Use [next] to get next step point
 */
class SegmentCrawler(startX: Int, startY: Int,
                     private val endX: Int, private val endY: Int)
{
    constructor(point1: Point, point2: Point) : this(point1.x, point2.y, point2.x, point2.y)

    /**Indicates if their a next step*/
    var hasNext = true
        private set
    private var moreStepToDo = true
    private var x = startX
    private var y = startY
    private var error = 0
    private val dx = abs(this.endX - startX)
    private val dy = abs(this.endY - startY)
    private val sx = (this.endX - startX).sign
    private val sy = (this.endY - startY).sign
    private val next =
        if (this.dx >= this.dy) this::nextDx
        else this::nextDy

    /**
     * Get current position and advance to given number of steps
     */
    fun next(step: Int): Point
    {
        if (step <= 0 || !this.moreStepToDo)
        {
            this.hasNext = this.moreStepToDo
            return Point(this.x, this.y)
        }

        val xx = this.x
        val yy = this.y

        for (count in 0 until step)
        {
            if (this.x == this.endX && this.y == this.endY)
            {
                this.moreStepToDo = false
                return Point(xx, yy)
            }

            this.next()
        }

        return Point(xx, yy)
    }

    private fun nextDx()
    {
        this.x += this.sx
        this.error += this.dy

        if (this.error >= this.dx)
        {
            this.error -= this.dx
            this.y += this.sy
        }
    }

    private fun nextDy()
    {
        this.y += this.sy
        this.error += this.dx

        if (this.error >= this.dy)
        {
            this.error -= this.dy
            this.x += this.sx
        }
    }
}