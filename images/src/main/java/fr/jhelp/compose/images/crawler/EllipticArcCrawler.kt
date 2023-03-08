/*
 *  <h1>License :</h1> <br/>
 * The following code is deliver as is. <br/>
 *  You can use, modify, the code as your need for any usage.<br/>
 *  But you can't do any action that avoid me or other person use, modify this code.<br/>
 *  The code is free for usage and modification, you can't change that fact.
 */

package fr.jhelp.compose.images.crawler

import fr.jhelp.compose.math.AngleFloat
import fr.jhelp.compose.math.Matrix
import fr.jhelp.compose.math.PI_FLOAT
import fr.jhelp.compose.math.Point2D
import fr.jhelp.compose.math.TWO_PI_FLOAT
import fr.jhelp.compose.math.extensions.sign
import fr.jhelp.compose.math.obtainRotateMatrix
import kotlin.math.abs
import kotlin.math.acos
import kotlin.math.cos
import kotlin.math.floor
import kotlin.math.max
import kotlin.math.sin
import kotlin.math.sqrt

internal class EllipticArcCrawler(private val startX: Float, private val startY: Float,
                                  private val radiusX: Float, private val radiusY: Float,
                                  rotationAxisX: AngleFloat,
                                  largeArc: Boolean, sweep: Boolean,
                                  private val endX: Float, private val endY: Float,
                                  precision: Int)
{
    private val angleStart: Float
    private val angleEnd: Float
    private val centerX: Float
    private val centerY: Float
    val numberStep: Int
    private val rotation: Matrix

    init
    {
        var angleStart: Float
        var angleEnd: Float
        var radiusX = this.radiusX
        var radiusY = this.radiusY

        // Compute the half distance between the current and the final point
        val distanceX2 = (this.startX - this.endX) / 2.0f
        val distanceY2 = (this.startY - this.endY) / 2.0f
        // Convert angle to radians
        val cosAngle = rotationAxisX.cos()
        val sinAngle = rotationAxisX.sin()

        //
        // Step 1 : Compute (x1, y1)
        //
        val x1 = cosAngle * distanceX2 + sinAngle * distanceY2
        val y1 = -sinAngle * distanceX2 + cosAngle * distanceY2
        var Prx = radiusX * radiusX
        var Pry = radiusY * radiusY
        val Px1 = x1 * x1
        val Py1 = y1 * y1

        // check that radii are large enough
        val radiiCheck = Px1 / Prx + Py1 / Pry

        if (radiiCheck > 0.99999f)
        {
            // don't cut it too close
            val radiiScale = sqrt(radiiCheck) * 1.00001f
            radiusX *= radiiScale
            radiusY *= radiiScale
            Prx = radiusX * radiusX
            Pry = radiusY * radiusY
        }

        //
        // Step 2 : Compute (cx1, cy1)
        //
        var sign = if (largeArc == sweep) -1.0f else 1.0f
        val sq = max(0.0f, (Prx * Pry - Prx * Py1 - Pry * Px1) / (Prx * Py1 + Pry * Px1))
        val coef = sign * sqrt(sq)
        val cx1 = coef * (radiusX * y1 / radiusY)
        val cy1 = coef * -(radiusY * x1 / radiusX)

        //
        // Step 3 : Compute (centerX, centerY) from (cx1, cy1)
        //
        val sx2 = (this.startX + this.endX) / 2.0f
        val sy2 = (this.startY + this.endY) / 2.0f
        this.centerX = sx2 + (cosAngle * cx1 - sinAngle * cy1)
        this.centerY = sy2 + (sinAngle * cx1 + cosAngle * cy1)

        //
        // Step 4 : Compute the angleStart and the angleEnd
        //
        val ux = (x1 - cx1) / radiusX
        val uy = (y1 - cy1) / radiusY
        val vx = (-x1 - cx1) / radiusX
        val vy = (-y1 - cy1) / radiusY
        var p: Float
        var n: Float
        // Compute the angle start
        n = sqrt(ux * ux + uy * uy)
        p = ux // (1 * ux) + (0 * uy)
        sign = uy.sign().toFloat()
        angleStart = sign * acos(p / n)

        // Compute the angle extent
        n = sqrt((ux * ux + uy * uy) * (vx * vx + vy * vy))
        p = ux * vx + uy * vy
        sign = (ux * vy - uy * vx).sign().toFloat()
        var angleExtent = sign * acos(p / n)

        if (!sweep && angleExtent > 0)
        {
            angleExtent -= TWO_PI_FLOAT
        }
        else if (sweep && angleExtent < 0)
        {
            angleExtent += TWO_PI_FLOAT
        }

        angleEnd = angleStart + angleExtent
        this.rotation = obtainRotateMatrix(-this.centerX, -this.centerY, -rotationAxisX)

        if (sweep != largeArc && this.startX < this.endX && this.startY > this.endY)
        {
            angleEnd -= PI_FLOAT
            angleStart -= PI_FLOAT
        }

        if (sweep == largeArc && this.startX < this.endX && this.startY < this.endY)
        {
            angleEnd -= PI_FLOAT
            angleStart -= PI_FLOAT
        }

        this.numberStep = precision * (1 + floor(4 * abs(angleExtent) / PI_FLOAT).toInt())
        this.angleStart = angleStart
        this.angleEnd = angleEnd
    }

    operator fun get(step: Int) =
        when
        {
            step < 0 || step > this.numberStep ->
                throw IllegalArgumentException("step MUST be in [0, ${this.numberStep}], not $step")
            step == 0                          -> Point2D(this.startX, this.startY)
            step == this.numberStep            -> Point2D(this.endX, this.endY)
            else                               ->
            {
                val angle =
                    this.angleStart + (((this.angleEnd - this.angleStart) * step) / this.numberStep)
                val x = this.centerX + (this.radiusX * cos(angle))
                val y = this.centerY + (this.radiusY * sin(angle))
                this.rotation.transform(x, y)
            }
        }
}