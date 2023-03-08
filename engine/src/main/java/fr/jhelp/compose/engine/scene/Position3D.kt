package fr.jhelp.compose.engine.scene

import fr.jhelp.compose.engine.annotations.OpenGLThread
import fr.jhelp.compose.math.AXIS_X
import fr.jhelp.compose.math.AXIS_Y
import fr.jhelp.compose.math.AXIS_Z
import fr.jhelp.compose.math.AngleFloat
import fr.jhelp.compose.math.AngleUnit
import fr.jhelp.compose.math.Point3D
import fr.jhelp.compose.math.Rotation3D
import fr.jhelp.compose.math.Vector3D
import javax.microedition.khronos.opengles.GL10

/**
 * Generic position in 3D, relative to it's parent
 */
data class Position3D(var x: Float = 0f, var y: Float = 0f, var z: Float = 0f,
                      var angleX: Float = 0f, var angleY: Float = 0f, var angleZ: Float = 0f,
                      var scaleX: Float = 1f, var scaleY: Float = 1f, var scaleZ: Float = 1f)
{
    constructor(position: Position3D) : this(position.x, position.y, position.z,
                                             position.angleX, position.angleY, position.angleZ,
                                             position.scaleX, position.scaleY, position.scaleZ)

    fun copy() = Position3D(this)

    fun set(position: Position3D)
    {
        this.x = position.x
        this.y = position.y
        this.z = position.z

        this.angleX = position.angleX
        this.angleY = position.angleY
        this.angleZ = position.angleZ

        this.scaleX = position.scaleX
        this.scaleY = position.scaleY
        this.scaleZ = position.scaleZ
    }

    fun position(x: Float, y: Float, z: Float)
    {
        this.x = x
        this.y = y
        this.z = z
    }

    fun translate(x: Float, y: Float, z: Float)
    {
        this.x += x
        this.y += y
        this.z += z
    }

    fun setScale(scaleX: Float, scaleY: Float, scaleZ: Float)
    {
        this.scaleX = scaleX
        this.scaleY = scaleY
        this.scaleZ = scaleZ
    }

    fun scale(scaleX: Float, scaleY: Float, scaleZ: Float)
    {
        this.scaleX *= scaleX
        this.scaleY *= scaleY
        this.scaleZ *= scaleZ
    }

    fun setScale(scale: Float)
    {
        this.scaleX = scale
        this.scaleY = scale
        this.scaleZ = scale
    }

    fun scale(scale: Float)
    {
        this.scaleX *= scale
        this.scaleY *= scale
        this.scaleZ *= scale
    }

    @OpenGLThread
    internal fun locate(gl: GL10)
    {
        gl.glTranslatef(this.x, this.y, this.z)
        gl.glRotatef(this.angleX, 1f, 0f, 0f)
        gl.glRotatef(this.angleY, 0f, 1f, 0f)
        gl.glRotatef(this.angleZ, 0f, 0f, 1f)
        gl.glScalef(this.scaleX, this.scaleY, this.scaleZ)
    }

    fun projection(point: Point3D): Point3D
    {
        var vector = Vector3D(point.x + this.x, point.y + this.y, point.z + this.z)
        vector = Rotation3D(AXIS_X, AngleFloat(this.angleX, AngleUnit.DEGREE)).rotateVector(vector)
        vector = Rotation3D(AXIS_Y, AngleFloat(this.angleY, AngleUnit.DEGREE)).rotateVector(vector)
        vector = Rotation3D(AXIS_Z, AngleFloat(this.angleZ, AngleUnit.DEGREE)).rotateVector(vector)
        return Point3D(vector.x, vector.y, vector.z)
    }

    fun antiProjection(point: Point3D): Point3D
    {
        var vector = Vector3D(point.x, point.y, point.z)
        vector = Rotation3D(AXIS_Z, AngleFloat(-this.angleZ, AngleUnit.DEGREE)).rotateVector(vector)
        vector = Rotation3D(AXIS_Y, AngleFloat(-this.angleY, AngleUnit.DEGREE)).rotateVector(vector)
        vector = Rotation3D(AXIS_X, AngleFloat(-this.angleX, AngleUnit.DEGREE)).rotateVector(vector)
        return Point3D(vector.x - this.x, vector.y - this.y, vector.z - this.z)
    }

    operator fun plus(position: Position3D): Position3D =
        Position3D(this.x + position.x,
                   this.y + position.y,
                   this.z + position.z,
                   this.angleX + position.angleX,
                   this.angleY + position.angleY,
                   this.angleZ + position.angleZ,
                   this.scaleX + position.scaleX,
                   this.scaleY + position.scaleY,
                   this.scaleZ + position.scaleZ)

    operator fun minus(position: Position3D): Position3D =
        Position3D(this.x - position.x,
                   this.y - position.y,
                   this.z - position.z,
                   this.angleX - position.angleX,
                   this.angleY - position.angleY,
                   this.angleZ - position.angleZ,
                   this.scaleX - position.scaleX,
                   this.scaleY - position.scaleY,
                   this.scaleZ - position.scaleZ)

    operator fun times(times: Float): Position3D =
        Position3D(times * this.x,
                   times * this.y,
                   times * this.z,
                   times * this.angleX,
                   times * this.angleY,
                   times * this.angleZ,
                   times * this.scaleX,
                   times * this.scaleY,
                   times * this.scaleZ)

    operator fun times(number: Number): Position3D =
        this.times(number.toFloat())
}