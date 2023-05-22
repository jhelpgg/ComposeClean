/*
 *  <h1>License :</h1> <br/>
 * The following code is deliver as is. <br/>
 *  You can use, modify, the code as your need for any usage.<br/>
 *  But you can't do any action that avoid me or other person use, modify this code.<br/>
 *  The code is free for usage and modification, you can't change that fact.
 */

package fr.jhelp.compose.images.path

import fr.jhelp.compose.images.crawler.EllipticArcCrawler
import fr.jhelp.compose.math.AngleFloat
import fr.jhelp.compose.math.Point2D
import fr.jhelp.compose.math.cubic
import fr.jhelp.compose.math.quadratic
import java.util.Objects


internal sealed class PathElement<PE : PathElement<PE>>
{
    abstract fun appendSegments(segments: MutableList<Segment>, precision: Int)
    abstract fun equalsPathElement(pathElement: PE): Boolean
    abstract fun hash(): Int

    override fun equals(other: Any?): Boolean
    {
        if (this === other)
        {
            return true
        }

        if (other == null || this.javaClass != other.javaClass)
        {
            return false
        }

        return this.equalsPathElement(other as PE)
    }

    override fun hashCode(): Int
    {
        return this.hash() * 31 + this.javaClass.name.hashCode()
    }
}

internal class CloseElement(val ignoreNext: Boolean) : PathElement<CloseElement>()
{
    override fun appendSegments(segments: MutableList<Segment>, precision: Int) = Unit

    override fun equalsPathElement(pathElement: CloseElement): Boolean =
        this.ignoreNext == pathElement.ignoreNext

    override fun hash(): Int = Objects.hash(this.ignoreNext)
}

internal class MoveToElement(val startX: Float, val startY: Float) : PathElement<MoveToElement>()
{
    override fun appendSegments(segments: MutableList<Segment>, precision: Int) = Unit

    override fun equalsPathElement(pathElement: MoveToElement): Boolean =
        this.startX == pathElement.startX && this.startY == pathElement.startY

    override fun hash(): Int = Objects.hash(this.startX, this.startY)
}

internal class LineElement(val startX: Float, val startY: Float,
                           val endX: Float, val endY: Float) : PathElement<LineElement>()
{
    override fun appendSegments(segments: MutableList<Segment>, precision: Int)
    {
        segments.add(Segment(this.startX, this.startY, 0f,
                             this.endX, this.endY, 1f))
    }

    override fun equalsPathElement(pathElement: LineElement): Boolean =
        this.startX == pathElement.startX && this.startY == pathElement.startY &&
                this.endX == pathElement.endX && this.endY == pathElement.endY

    override fun hash(): Int = Objects.hash(this.startX, this.startY, this.endX, this.endY)
}

internal class QuadraticElement(val startX: Float, val startY: Float,
                                val controlX: Float, val controlY: Float,
                                val endX: Float, val endY: Float) : PathElement<QuadraticElement>()
{
    override fun appendSegments(segments: MutableList<Segment>, precision: Int)
    {
        val xs = quadratic(this.startX, this.controlX, this.endX, precision)
        val ys = quadratic(this.startY, this.controlY, this.endY, precision)

        for (index in 1 until precision)
        {
            segments.add(
                Segment(xs[index - 1], ys[index - 1], 0f,
                        xs[index], ys[index], 1f))
        }
    }

    override fun equalsPathElement(pathElement: QuadraticElement): Boolean =
        this.startX == pathElement.startX && this.startY == pathElement.startY &&
                this.controlX == pathElement.controlX && this.controlY == pathElement.controlY &&
                this.endX == pathElement.endX && this.endY == pathElement.endY

    override fun hash(): Int =
        Objects.hash(this.startX, this.startY, this.controlX, this.controlY, this.endX, this.endY)
}

internal class CubicElement(val startX: Float, val startY: Float,
                            val control1X: Float, val control1Y: Float,
                            val control2X: Float, val control2Y: Float,
                            val endX: Float, val endY: Float) : PathElement<CubicElement>()
{
    override fun appendSegments(segments: MutableList<Segment>, precision: Int)
    {
        val xs = cubic(this.startX, this.control1X, this.control2X, this.endX, precision)
        val ys = cubic(this.startY, this.control1Y, this.control2Y, this.endY, precision)

        for (index in 1 until precision)
        {
            segments.add(
                Segment(xs[index - 1], ys[index - 1], 0f,
                        xs[index], ys[index], 1f))
        }
    }

    override fun equalsPathElement(pathElement: CubicElement): Boolean =
        this.startX == pathElement.startX && this.startY == pathElement.startY &&
                this.control1X == pathElement.control1X && this.control1Y == pathElement.control1Y &&
                this.control2X == pathElement.control2X && this.control2Y == pathElement.control2Y &&
                this.endX == pathElement.endX && this.endY == pathElement.endY

    override fun hash(): Int =
        Objects.hash(this.startX, this.startY,
                     this.control1X, this.control1Y,
                     this.control2X, this.control2Y,
                     this.endX, this.endY)
}

internal class EllipticArcElement(val startX: Float, val startY: Float,
                                  val radiusX: Float, val radiusY: Float,
                                  val rotationAxisX: AngleFloat,
                                  val largeArc: Boolean, val sweep: Boolean,
                                  val endX: Float, val endY: Float) :
        PathElement<EllipticArcElement>()
{
    override fun appendSegments(segments: MutableList<Segment>, precision: Int)
    {
        val ellipticArcToCrawler =
            EllipticArcCrawler(this.startX, this.startY,
                               this.radiusX, this.radiusY,
                               this.rotationAxisX,
                               this.largeArc, this.sweep,
                               this.endX, this.endY,
                               precision)
        var first = ellipticArcToCrawler[0]
        var second: Point2D

        for (index in 1..ellipticArcToCrawler.numberStep)
        {
            second = ellipticArcToCrawler[index]
            segments.add(Segment(first, 0f, second, 1f))
            first = second
        }
    }

    override fun equalsPathElement(pathElement: EllipticArcElement): Boolean =
        this.startX == pathElement.startX && this.startY == pathElement.startY &&
                this.radiusX == pathElement.radiusX && this.radiusY == pathElement.radiusY &&
                this.rotationAxisX == pathElement.rotationAxisX &&
                this.largeArc == pathElement.largeArc && this.sweep == pathElement.sweep &&
                this.endX == pathElement.endX && this.endY == pathElement.endY

    override fun hash(): Int =
        Objects.hash(this.startX, this.startY,
                     this.radiusX, this.radiusY,
                     this.rotationAxisX,
                     this.largeArc, this.sweep,
                     this.endX, this.endY)
}

