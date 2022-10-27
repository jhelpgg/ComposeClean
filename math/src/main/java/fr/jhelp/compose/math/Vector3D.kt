package fr.jhelp.compose.math

import kotlin.math.sqrt

data class Vector3D(var x: Float = 0f, var y: Float = 0f, var z: Float = 0f)
{
    constructor(start: Point3D, end: Point3D) :
            this(end.x - start.x, end.y - start.y, end.z - start.z)

    fun set(x: Float = 0f, y: Float = 0f, z: Float = 0f)
    {
        this.x = x
        this.y = y
        this.z = z
    }

    fun lengthSquare() = this.x * this.x + this.y * this.y + this.z * this.z

    fun length() = sqrt(this.x * this.x + this.y * this.y + this.z * this.z)

    operator fun timesAssign(number: Float)
    {
        this.x *= number
        this.y *= number
        this.z *= number
    }

    operator fun times(number: Float): Vector3D =
        Vector3D(this.x * number, this.y * number, this.z * number)

    fun cross(var1: Vector3D): Vector3D
    {
        val var2 = Vector3D()
        var2.cross(this, var1)
        return var2
    }

    fun cross(var1: Vector3D, var2: Vector3D)
    {
        this.x = var1.y * var2.z - var1.z * var2.y
        this.y = var1.z * var2.x - var1.x * var2.z
        this.z = var1.x * var2.y - var1.y * var2.x
    }

    fun add(var1: Vector3D)
    {
        this.add(this, var1)
    }

    fun add(var1: Vector3D, var2: Vector3D)
    {
        this.x = var1.x + var2.x
        this.y = var1.y + var2.y
        this.z = var1.z + var2.z
    }

    fun dot(var1: Vector3D): Float =
        this.x * var1.x + this.y * var1.y + this.z * var1.z
}
