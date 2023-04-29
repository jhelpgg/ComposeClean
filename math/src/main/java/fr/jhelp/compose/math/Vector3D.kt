package fr.jhelp.compose.math

import kotlin.math.sqrt

/**
 * Vector in 3D
 * @property x Vector abscissa direction
 * @property y Vector ordinate direction
 * @property z Vector depth direction
 */
data class Vector3D(var x: Float = 0f, var y: Float = 0f, var z: Float = 0f)
{
    /**
     * Create from two points
     */
    constructor(start: Point3D, end: Point3D) :
            this(end.x - start.x, end.y - start.y, end.z - start.z)

    /**
     * Change vector direction
     */
    fun set(x: Float = 0f, y: Float = 0f, z: Float = 0f)
    {
        this.x = x
        this.y = y
        this.z = z
    }

    /**
     * Vector length in square
     */
    fun lengthSquare(): Float = this.x * this.x + this.y * this.y + this.z * this.z

    /**
     * Vector length
     */
    fun length(): Float = sqrt(this.x * this.x + this.y * this.y + this.z * this.z)

    /**
     * Multiply vector by a factor and keep result as new direction
     */
    operator fun timesAssign(number: Float)
    {
        this.x *= number
        this.y *= number
        this.z *= number
    }

    /**
     * Multiply vector by a factor
     */
    operator fun times(number: Float): Vector3D =
        Vector3D(this.x * number, this.y * number, this.z * number)

    /**
     * Compute cross product with an other vector
     */
    fun cross(var1: Vector3D): Vector3D
    {
        val var2 = Vector3D()
        var2.cross(this, var1)
        return var2
    }

    /**
     * Compute cross product with an other vector and keep result as new direction
     */
    fun cross(var1: Vector3D, var2: Vector3D)
    {
        this.x = var1.y * var2.z - var1.z * var2.y
        this.y = var1.z * var2.x - var1.x * var2.z
        this.z = var1.x * var2.y - var1.y * var2.x
    }

    /**
     * Add a vector an keep result as new direction
     */
    fun add(var1: Vector3D)
    {
        this.add(this, var1)
    }

    /**
     * Add an other vector
     */
    fun add(var1: Vector3D, var2: Vector3D)
    {
        this.x = var1.x + var2.x
        this.y = var1.y + var2.y
        this.z = var1.z + var2.z
    }

    /**
     * Compute dot product with an other vector
     */
    fun dot(var1: Vector3D): Float =
        this.x * var1.x + this.y * var1.y + this.z * var1.z
}
