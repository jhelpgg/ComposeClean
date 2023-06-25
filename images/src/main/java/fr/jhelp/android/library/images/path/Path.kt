/*
 *  <h1>License :</h1> <br/>
 * The following code is deliver as is. <br/>
 *  You can use, modify, the code as your need for any usage.<br/>
 *  But you can't do any action that avoid me or other person use, modify this code.<br/>
 *  The code is free for usage and modification, you can't change that fact.
 */

package fr.jhelp.android.library.images.path

import fr.jhelp.android.library.math.AngleFloat
import fr.jhelp.android.library.math.distance
import fr.jhelp.android.library.math.extensions.bounds
import fr.jhelp.android.library.math.extensions.nul
import fr.jhelp.android.library.math.extensions.same
import kotlin.math.max

/**
 * Represents a path for draw.
 *
 * Path is can be composed of segments and/or quadratics and/or cubics and/or elliptic arcs
 *
 * Path starts with a [moveTo] to know the starting point.
 */
class Path
{
    private val path = ArrayList<PathElement<*>>()
    private var startX = 0f
    private var startY = 0f
    private var x = 0f
    private var y = 0f

    /**
     * Copy this path
     */
    fun copy(): Path
    {
        val copy = Path()
        copy.path.addAll(this.path)
        copy.x = this.x
        copy.y = this.y
        copy.startX = this.startX
        copy.startY = this.startY
        return copy
    }

    /**
     * Move to given position.
     *
     * The point will be the start of new path part.
     *
     * The call to [close], will draw a segment to last position to this one, to close this part
     */
    fun moveTo(startX: Float, startY: Float): Path
    {
        this.path.add(MoveToElement(startX, startY))
        this.startX = startX
        this.startY = startY
        this.x = startX
        this.y = startY
        return this
    }

    /**
     * Close the current path part;
     *
     * It draws a segment to last position to last [moveTo] position
     */
    fun close(): Path
    {

        if (!this.startX.same(this.x) || !this.startY.same(this.y))
        {
            this.path.add(CloseElement(true))
            this.path.add(LineElement(this.x, this.y, this.startX, this.startY))
            this.x = this.startX
            this.y = this.startY
        }
        else
        {
            this.path.add(CloseElement(false))
        }

        return this
    }

    /**
     * Draw a segment to last position to given position
     *
     * The given position will become the new last position
     */
    fun lineTo(endX: Float, endY: Float): Path
    {
        this.path.add(LineElement(this.x, this.y, endX, endY))
        this.x = endX
        this.y = endY
        return this
    }

    /**
     * Draw a quadratic from last position, using the control point and end position.
     *
     * The end position will become the new last position
     */
    fun quadraticTo(controlX: Float, controlY: Float,
                    endX: Float, endY: Float): Path
    {
        this.path.add(QuadraticElement(this.x, this.y,
                                       controlX, controlY,
                                       endX, endY))
        this.x = endX
        this.y = endY
        return this
    }

    /**
     * Draw a cubic from last position, using the controls points and end position.
     *
     * The end position will become the new last position
     */
    fun cubicTo(control1X: Float, control1Y: Float,
                control2X: Float, control2Y: Float,
                endX: Float, endY: Float): Path
    {
        this.path.add(CubicElement(this.x, this.y,
                                   control1X, control1Y,
                                   control2X, control2Y,
                                   endX, endY))
        this.x = endX
        this.y = endY
        return this
    }

    /**
     * Draw an elliptic arc from last position, using ellpitic constraints and end position
     *
     * The end position will become the new last position
     */
    fun ellipticArcTo(radiusX: Float, radiusY: Float, rotationAxisX: AngleFloat,
                      largeArc: Boolean, sweep: Boolean,
                      endX: Float, endY: Float): Path
    {
        this.path.add(EllipticArcElement(this.x, this.y,
                                         radiusX, radiusY, rotationAxisX,
                                         largeArc, sweep,
                                         endX, endY))
        this.x = endX
        this.y = endY
        return this
    }

    /**
     * Accumulate a whole path
     */
    fun add(path: Path): Path
    {
        this.path.addAll(path.path)
        this.x = path.x
        this.y = path.y
        this.startX = path.startX
        this.startY = path.startY
        return this
    }

    /**
     * Compute the path as a list of segments.
     *
     * The size and the number of segments depends on the given precision.
     * More the precision is high, more smooth is the path, but more segments are generated
     *
     * The start and end values will be homogeneously interpolated along generated segments
     */
    fun path(precision: Int, start: Float, end: Float): List<Segment>
    {
        val precisionLocal = max(2, precision)
        val lines = ArrayList<Segment>()

        for (element in this.path)
        {
            element.appendSegments(lines, precisionLocal)
        }

        val distances = ArrayList<Float>()
        var size = 0f
        var distance: Float

        for (line in lines)
        {
            distance = distance(line.startX, line.startY, line.endX, line.endY)
            distances.add(distance)
            size += distance
        }

        if (size.nul)
        {
            return lines
        }

        var value = start
        val diff = end - start

        for ((index, line) in lines.withIndex())
        {
            line.startValue = value
            value += (distances[index] * diff) / size
            line.endValue = value
        }

        return lines
    }

    /**
     * Transform this path to a path of a polygon understandable by android Canvas
     *
     * @param percent Percentage of this path to convert
     * @param precision Precision to use for approximate curves by list of lines
     */
    fun toAndroidPolygon(percent: Int = 100, precision: Int = 16): android.graphics.Path
    {
        val androidPath = android.graphics.Path()
        val percentLocal = percent.bounds(0, 100)
        val precisionLocal = max(2, precision)
        val segmentOrPointInfo = ArrayList<SegmentPointCloseInfo>()
        val lines = ArrayList<Segment>()
        var ignoreNext = false

        for (element in this.path)
        {
            if (ignoreNext)
            {
                ignoreNext = false
                continue
            }

            when (element)
            {
                is MoveToElement ->
                {
                    segmentOrPointInfo.add(PointInfo(element.startX, element.startY))
                }

                is CloseElement  ->
                {
                    segmentOrPointInfo.add(CloseInfo)
                    ignoreNext = element.ignoreNext
                }

                else             ->
                {
                    lines.clear()
                    element.appendSegments(lines, precisionLocal)

                    for (segment in lines)
                    {
                        segmentOrPointInfo.add(SegmentInfo(segment))
                    }
                }
            }
        }

        val number = (segmentOrPointInfo.size * percentLocal) / 100

        var segmentPointClose: SegmentPointCloseInfo
        var segment: Segment
        var open = false

        for (index in 0 until number)
        {
            segmentPointClose = segmentOrPointInfo[index]

            when (segmentPointClose)
            {
                CloseInfo      ->
                {
                    open = false
                    androidPath.close()
                }

                is PointInfo   ->
                {
                    open = true
                    androidPath.moveTo(segmentPointClose.x, segmentPointClose.y)
                }

                is SegmentInfo ->
                {
                    segment = segmentPointClose.segment
                    androidPath.lineTo(segment.endX, segment.endY)
                }
            }
        }

        if (open)
        {
            androidPath.close()
        }

        return androidPath
    }

    /**
     *  Transform this path to a path understandable by android Canvas
     *
     *  @param ellipseArcPrecision Precision use to approximate ellipse arcs. (Ellipse arc not exists in Android Path)
     */
    fun toAndroidPath(ellipseArcPrecision: Int = 16): android.graphics.Path
    {
        val precision = max(2, ellipseArcPrecision)
        val androidPath = android.graphics.Path()
        var ignoreNext = false

        for (element in this.path)
        {
            if (ignoreNext)
            {
                ignoreNext = false
                continue
            }

            when (element)
            {
                is CloseElement       ->
                {
                    androidPath.close()
                    ignoreNext = element.ignoreNext
                }

                is MoveToElement      -> androidPath.moveTo(element.startX, element.startY)
                is LineElement        -> androidPath.lineTo(element.endX, element.endY)
                is QuadraticElement   -> androidPath.quadTo(element.controlX, element.controlY,
                                                            element.endX, element.endY)

                is CubicElement       -> androidPath.cubicTo(element.control1X, element.control1Y,
                                                             element.control2X, element.control2Y,
                                                             element.endX, element.endY)

                is EllipticArcElement ->
                {
                    val segments = ArrayList<Segment>()
                    element.appendSegments(segments, precision)

                    for (segment in segments)
                    {
                        androidPath.lineTo(segment.endX, segment.endY)
                    }
                }
            }
        }

        return androidPath
    }

    override fun equals(other: Any?): Boolean
    {
        if (this === other)
        {
            return true
        }

        if (other == null || other !is Path)
        {
            return false
        }

        if (this.path.size != other.path.size)
        {
            return false
        }

        for ((index, pathElement) in this.path.withIndex())
        {
            if (pathElement != other.path[index])
            {
                return false
            }
        }

        return true
    }

    override fun hashCode(): Int= this.path.hashCode()
}