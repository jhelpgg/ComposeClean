package fr.jhelp.android.library.engine.scene

import fr.jhelp.android.library.engine.annotations.OpenGLThread
import fr.jhelp.android.library.math.AXIS_X
import fr.jhelp.android.library.math.AXIS_Y
import fr.jhelp.android.library.math.AXIS_Z
import fr.jhelp.android.library.math.AngleFloat
import fr.jhelp.android.library.math.AngleUnit
import fr.jhelp.android.library.math.Point3D
import fr.jhelp.android.library.math.Rotation3D
import fr.jhelp.android.library.math.Vector3D
import javax.microedition.khronos.opengles.GL10

/**
 * Generic position in 3D, relative to it's parent
 * @property x Position abscissa
 * @property y Position ordinate
 * @property z Position depth
 * @property angleX Rotation angle, in degree, around X axis
 * @property angleY Rotation angle, in degree, around Y axis
 * @property angleZ Rotation angle, in degree, around Z axis
 * @property scaleX Scale factor in X axis direction
 * @property scaleY Scale factor in Y axis direction
 * @property scaleZ Scale factor in Z axis direction
 */
data class Position3D(var x: Float = 0f, var y: Float = 0f, var z: Float = 0f,
                      var angleX: Float = 0f, var angleY: Float = 0f, var angleZ: Float = 0f,
                      var scaleX: Float = 1f, var scaleY: Float = 1f, var scaleZ: Float = 1f)
{
    /**
     * Create position copy to the given one
     */
    constructor(position: Position3D) : this(position.x, position.y, position.z,
                                             position.angleX, position.angleY, position.angleZ,
                                             position.scaleX, position.scaleY, position.scaleZ)

    /**
     * Create copy of this position
     */
    fun copy(): Position3D = Position3D(this)

    /**
     * Copy the position parameters to this one
     */
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

    /**
     * Change position location
     */
    fun position(x: Float, y: Float, z: Float)
    {
        this.x = x
        this.y = y
        this.z = z
    }

    /**
     * Translate the position
     */
    fun translate(x: Float, y: Float, z: Float)
    {
        this.x += x
        this.y += y
        this.z += z
    }

    /**
     * Change scale factors
     */
    fun setScale(scaleX: Float, scaleY: Float, scaleZ: Float)
    {
        this.scaleX = scaleX
        this.scaleY = scaleY
        this.scaleZ = scaleZ
    }

    /**
     * Scale the position
     */
    fun scale(scaleX: Float, scaleY: Float, scaleZ: Float)
    {
        this.scaleX *= scaleX
        this.scaleY *= scaleY
        this.scaleZ *= scaleZ
    }

    /**
     * Change scale factor to the same one
     */
    fun setScale(scale: Float)
    {
        this.scaleX = scale
        this.scaleY = scale
        this.scaleZ = scale
    }

    /**
     * Scale with same value in all directions
     */
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

    /**
     * Project o point with this position
     *
     * Space reference to position reference
     */
    fun projection(point: Point3D): Point3D
    {
        var vector = Vector3D(point.x + this.x, point.y + this.y, point.z + this.z)
        vector = Rotation3D(AXIS_X, AngleFloat(this.angleX, AngleUnit.DEGREE)).rotateVector(vector)
        vector = Rotation3D(AXIS_Y, AngleFloat(this.angleY, AngleUnit.DEGREE)).rotateVector(vector)
        vector = Rotation3D(AXIS_Z, AngleFloat(this.angleZ, AngleUnit.DEGREE)).rotateVector(vector)
        return Point3D(vector.x, vector.y, vector.z)
    }

    /**
     * Anti-project a points from this position
     *
     * Position reference to space reference
     */
    fun antiProjection(point: Point3D): Point3D
    {
        var vector = Vector3D(point.x, point.y, point.z)
        vector = Rotation3D(AXIS_Z, AngleFloat(-this.angleZ, AngleUnit.DEGREE)).rotateVector(vector)
        vector = Rotation3D(AXIS_Y, AngleFloat(-this.angleY, AngleUnit.DEGREE)).rotateVector(vector)
        vector = Rotation3D(AXIS_X, AngleFloat(-this.angleX, AngleUnit.DEGREE)).rotateVector(vector)
        return Point3D(vector.x - this.x, vector.y - this.y, vector.z - this.z)
    }

    /**
     * Add an other position
     */
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

    /**
     * Subtract an other position
     */
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

    /**
     * Multiply position by a factor
     */
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

    /**
     * Multiply position by a factor
     */
    operator fun times(number: Number): Position3D =
        this.times(number.toFloat())
}