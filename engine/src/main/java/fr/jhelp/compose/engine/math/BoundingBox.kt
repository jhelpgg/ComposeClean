/*
 *  <h1>License :</h1> <br/>
 * The following code is deliver as is. <br/>
 *  You can use, modify, the code as your need for any usage.<br/>
 *  But you can't do any action that avoid me or other person use, modify this code.<br/>
 *  The code is free for usage and modification, you can't change that fact.
 */

package fr.jhelp.compose.engine.math

import fr.jhelp.compose.math.Point3D
import kotlin.math.max
import kotlin.math.min

class BoundingBox()
{
    var minX = Float.POSITIVE_INFINITY
        private set
    var maxX = Float.NEGATIVE_INFINITY
        private set
    var minY = Float.POSITIVE_INFINITY
        private set
    var maxY = Float.NEGATIVE_INFINITY
        private set
    var minZ = Float.POSITIVE_INFINITY
        private set
    var maxZ = Float.NEGATIVE_INFINITY
        private set

    val empty get() = this.minX > this.maxX
    val notEmpty get() = this.minX <= this.maxX

    constructor(boundingBox: BoundingBox) : this()
    {
        this.minX = boundingBox.minX
        this.minY = boundingBox.minY
        this.minZ = boundingBox.minZ
        this.maxX = boundingBox.maxX
        this.maxY = boundingBox.maxY
        this.maxZ = boundingBox.maxZ
    }

    fun copy() = BoundingBox(this)

    fun add(x: Float, y: Float, z: Float)
    {
        this.minX = min(this.minX, x)
        this.minY = min(this.minY, y)
        this.minZ = min(this.minZ, z)
        this.maxX = max(this.maxX, x)
        this.maxY = max(this.maxY, y)
        this.maxZ = max(this.maxZ, z)
    }

    fun center(): Point3D =
        if (this.empty) Point3D(0f, 0f, 0f)
        else Point3D((this.minX + this.maxX) / 2f,
                     (this.minY + this.maxY) / 2f,
                     (this.minZ + this.maxZ) / 2f)

    operator fun contains(point3D: Point3D): Boolean =
        this.contains(point3D.x, point3D.y, point3D.z)

    fun contains(x: Float, y: Float, z: Float): Boolean = this.notEmpty
                                                          && (x in this.minX..this.maxX)
                                                          && (y in this.minY..this.maxY)
                                                          && (z in this.minZ..this.maxZ)

    fun translate(x: Float, y: Float): BoundingBox =
        if (this.empty) this
        else
        {
            val copy = this.copy()
            copy.minX += x
            copy.minY += y
            copy.maxX += x
            copy.maxY += y
            copy
        }

    fun willIntersects(thisTranslateX: Float, thisTranslateY: Float, thisTranslateZ: Float,
                       boundingBox: BoundingBox,
                       boxTranslateX: Float, boxTranslateY: Float, boxTranslateZ: Float): Boolean
    {
        if (this.empty || boundingBox.empty)
        {
            return false
        }

        var min = max(this.minX + thisTranslateX, boundingBox.minX + boxTranslateX)
        var max = min(this.maxX + thisTranslateX, boundingBox.maxX + boxTranslateX)

        if (min > max)
        {
            return false
        }

        min = max(this.minY + thisTranslateY, boundingBox.minY + boxTranslateY)
        max = min(this.maxY + thisTranslateY, boundingBox.maxY + boxTranslateY)

        if (min > max)
        {
            return false
        }

        min = max(this.minZ + thisTranslateZ, boundingBox.minZ + boxTranslateZ)
        max = min(this.maxZ + thisTranslateZ, boundingBox.maxZ + boxTranslateZ)

        return min <= max
    }


    fun intersects(boundingBox: BoundingBox): Boolean
    {
        if (this.empty || boundingBox.empty)
        {
            return false
        }

        var min = max(this.minX, boundingBox.minX)
        var max = min(this.maxX, boundingBox.maxX)

        if (min > max)
        {
            return false
        }

        min = max(this.minY, boundingBox.minY)
        max = min(this.maxY, boundingBox.maxY)

        if (min > max)
        {
            return false
        }

        min = max(this.minZ, boundingBox.minZ)
        max = min(this.maxZ, boundingBox.maxZ)

        return min <= max
    }

    fun intersection(boundingBox: BoundingBox): BoundingBox
    {
        if (this.empty || boundingBox.empty)
        {
            return BoundingBox()
        }

        val minX = max(this.minX, boundingBox.minX)
        val maxX = min(this.maxX, boundingBox.maxX)

        if (minX > maxX)
        {
            return BoundingBox()
        }

        val minY = max(this.minY, boundingBox.minY)
        val maxY = min(this.maxY, boundingBox.maxY)

        if (minY > maxY)
        {
            return BoundingBox()
        }

        val minZ = max(this.minZ, boundingBox.minZ)
        val maxZ = min(this.maxZ, boundingBox.maxZ)

        if (minZ > maxZ)
        {
            return BoundingBox()
        }

        val intersection = BoundingBox()
        intersection.minX = minX
        intersection.maxX = maxX
        intersection.minY = minY
        intersection.maxY = maxY
        intersection.minZ = minZ
        intersection.maxZ = maxZ
        return intersection
    }

    fun union(boundingBox: BoundingBox): BoundingBox
    {
        if (this.empty)
        {
            return boundingBox.copy()
        }

        if (boundingBox.empty)
        {
            return this.copy()
        }

        val union = BoundingBox()
        union.minX = min(this.minX, boundingBox.minX)
        union.maxX = max(this.maxX, boundingBox.maxX)
        union.minY = min(this.minY, boundingBox.minY)
        union.maxY = max(this.maxY, boundingBox.maxY)
        union.minZ = min(this.minZ, boundingBox.minZ)
        union.maxZ = max(this.maxZ, boundingBox.maxZ)
        return union
    }
}