package fr.jhelp.compose.math

import fr.jhelp.compose.math.extensions.nul
import kotlin.math.acos
import kotlin.math.sqrt

/**
 * Rotation in 3D
 */
class Rotation3D()
{
    private var w = 1f
    private var x = 0f
    private var y = 0f
    private var z = 0f

    constructor(rotation3D: Rotation3D) : this()
    {
        this.w = rotation3D.w
        this.x = rotation3D.x
        this.y = rotation3D.y
        this.z = rotation3D.z
    }

    /**
     * Create a rotation with a rotation axis and rotation angle
     * @param axis Rotation axis
     * @param angle Rotation angle in radian
     */
    constructor(axis: Vector3D, angle: AngleFloat) : this()
    {
        this.set(axis, angle)
    }

    /**
     * Create rotation with two vectors.
     *
     * The constructed rotation will be able to transform the **`source`** vector to the **`destination`** vector.
     *
     * So the rotation angle will be the angle between the **`source`** and the **`destination`**
     * and the axis will be the cross vector of the given ones.
     * @param source Vector source
     * @param destination Vector destination
     */
    constructor(source: Vector3D, destination: Vector3D) : this()
    {
        this.set(source, destination)
    }

    /**
     * Make the rotation to identity
     */
    fun identity()
    {
        this.w = 1f
        this.x = 0f
        this.y = 0f
        this.z = 0f
    }

    /**
     * Fill the rotation axis inside given vector and return the rotation angle
     * @param axis Vector to set with the rotation axis
     * @return Rotation angle
     */
    fun get(axis: Vector3D): AngleFloat
    {
        val angle = 2.0f * acos(this.w)
        axis.set(this.x, this.y, this.z)
        val length = axis.length()

        if (length.nul)
        {
            axis.set(0.0F, 0.0F, 1.0F)
        }
        else
        {
            axis *= 1.0F / length
        }

        return AngleFloat(angle, AngleUnit.RADIAN)
    }

    /**
     * Compute the opposite of rotation
     * @return Rotation opposite
     */
    operator fun unaryMinus(): Rotation3D
    {
        val inverted = Rotation3D(this)
        inverted.opposite()
        return inverted
    }

    /**
     * Make rotation to its opposite
     */
    fun opposite()
    {
        this.x = -this.x
        this.y = -this.y
        this.z = -this.z
    }

    /**
     * Rotation length squared
     * @return Rotation length squared
     */
    fun lengthSquared(): Float =
        this.w * this.w + this.x * this.x + this.y * this.y + this.z * this.z

    /**
     * Rotation length
     * @return Rotation length
     */
    fun length(): Float =
        sqrt(this.w * this.w + this.x * this.x + this.y * this.y + this.z * this.z)

    /**
     * Affect this rotation as the result of combination of two rotations (First then second).
     * @param rotation1 First rotation
     * @param rotation2 Second rotation
     */
    fun multiply(rotation1: Rotation3D, rotation2: Rotation3D)
    {
        this.w =
            rotation1.w * rotation2.w - rotation1.x * rotation2.x - rotation1.y * rotation2.y - rotation1.z * rotation2.z
        this.x =
            rotation1.w * rotation2.x + rotation1.x * rotation2.w + rotation1.y * rotation2.z - rotation1.z * rotation2.y
        this.y =
            rotation1.w * rotation2.y + rotation1.y * rotation2.w - rotation1.x * rotation2.z + rotation1.z * rotation2.x
        this.z =
            rotation1.w * rotation2.z + rotation1.z * rotation2.w + rotation1.x * rotation2.y - rotation1.y * rotation2.x
    }

    /**
     * Rotate a vector by this rotation and fill the result in a given vector
     * @param toRotate Vector to rotate
     * @param rotationResult Vector where fill the rotation result
     * @return Rotation result
     */
    fun rotateVector(toRotate: Vector3D, rotationResult: Vector3D = Vector3D()): Vector3D
    {
        val axis = Vector3D(this.x, this.y, this.z)
        val cross = axis.cross(toRotate)
        val projected = cross.cross(axis)
        cross *= 2.0f * this.w
        projected *= -2.0f
        rotationResult.add(toRotate, cross)
        rotationResult.add(rotationResult, projected)
        return rotationResult
    }

    /**
     * Copy a rotation
     * @param rotation Rotation to copy
     */
    fun copy(rotation: Rotation3D)
    {
        this.w = rotation.w
        this.x = rotation.x
        this.y = rotation.y
        this.z = rotation.z
    }

    /**
     * Change to a rotation around given axis vector with given angle
     */
    fun set(axis: Vector3D, angle: AngleFloat)
    {
        val semiAngle = angle / 2.0f
        this.w = angle.cos()
        val sinus = semiAngle.sin()
        val size = axis.length()
        this.x = (axis.x * sinus) / size
        this.y = (axis.y * sinus) / size
        this.z = (axis.z * sinus) / size
    }

    /**
     * Change to rotation that transform a vector to an other
     */
    fun set(source: Vector3D, destination: Vector3D)
    {
        val axis = source.cross(destination)

        if (axis.lengthSquare().nul)
        {
            this.identity()
        }
        else
        {
            var arcCosinus = source.dot(destination)
            val length = source.length() * destination.length()

            if (length.nul)
            {
                this.identity()
            }
            else
            {
                arcCosinus /= length
                this.set(axis, AngleFloat(acos(arcCosinus), AngleUnit.RADIAN))
            }
        }
    }

    /**
     * Combine this rotation with given one (this then given)
     * @param rotation Rotation to combine with
     * @return Combination result
     */
    operator fun times(rotation: Rotation3D): Rotation3D
    {
        val rotationResult = Rotation3D()
        rotationResult.multiply(this, rotation)
        return rotationResult
    }
}
