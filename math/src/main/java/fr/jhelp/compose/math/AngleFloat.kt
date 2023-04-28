package fr.jhelp.compose.math

import fr.jhelp.compose.math.extensions.compare
import fr.jhelp.compose.math.extensions.same
import kotlin.math.cos
import kotlin.math.sin

/**
 * Represents an angle
 *
 * @param value Angle value
 * @param unit Angle unit, see [AngleUnit]
 */
class AngleFloat(value: Float, val unit: AngleUnit) : Comparable<AngleFloat>
{
    /**Angle value in its unit*/
    val value = this.unit.modularize(value)

    /**
     * Add an angle to this angle
     *
     * @param angle Angle to add (Angle unit is convert to this angle unit if necessary)
     * @return Result angle in same unit as this angle unit
     */
    operator fun plus(angle: AngleFloat): AngleFloat =
        AngleFloat(this.value + angle.convert(this.unit).value, this.unit)

    /**
     * Subtract an angle to this angle
     *
     * @param angle Angle to subtract (Angle unit is convert to this angle unit if necessary)
     * @return Result angle in same unit as this angle unit
     */
    operator fun minus(angle: AngleFloat): AngleFloat =
        AngleFloat(this.value - angle.convert(this.unit).value, this.unit)

    /**
     * Multiply a number to this angle
     *
     * @param factor Factor to apply to this angle
     * @return Result angle in same unit as this angle unit
     */
    operator fun times(factor: Number): AngleFloat =
        AngleFloat(this.value * factor.toFloat(), this.unit)

    /**
     * Divide a number to this angle
     *
     * @param factor Factor to apply to this angle
     * @return Result angle in same unit as this angle unit
     */
    operator fun div(factor: Number): AngleFloat =
        AngleFloat(this.value / factor.toFloat(), this.unit)

    /**
     * This opposite angle
     */
    operator fun unaryMinus(): AngleFloat = AngleFloat(-this.value, this.unit)

    /**
     * Convert this angle to an other unit
     *
     * If the unit is the same as this angle unit, this angle is returns, else a new angle is created
     */
    fun convert(angleUnit: AngleUnit): AngleFloat =
        when (this.unit)
        {
            AngleUnit.DEGREE ->
                when (angleUnit)
                {
                    AngleUnit.DEGREE -> this
                    AngleUnit.RADIAN -> AngleFloat(degreeToRadian(this.value), AngleUnit.RADIAN)
                    AngleUnit.GRADE  -> AngleFloat(degreeToGrade(this.value), AngleUnit.GRADE)
                }

            AngleUnit.RADIAN ->
                when (angleUnit)
                {
                    AngleUnit.DEGREE -> AngleFloat(radianToDegree(this.value), AngleUnit.DEGREE)
                    AngleUnit.RADIAN -> this
                    AngleUnit.GRADE  -> AngleFloat(radianToGrade(this.value), AngleUnit.GRADE)
                }

            AngleUnit.GRADE  ->
                when (angleUnit)
                {
                    AngleUnit.DEGREE -> AngleFloat(gradeToDegree(this.value), AngleUnit.DEGREE)
                    AngleUnit.RADIAN -> AngleFloat(gradeToRadian(this.value), AngleUnit.RADIAN)
                    AngleUnit.GRADE  -> this
                }
        }

    /**
     * Angle cosinus values
     */
    fun cos(): Float = cos(this.convert(AngleUnit.RADIAN).value)

    /**
     * Angle sinus values
     */
    fun sin(): Float = sin(this.convert(AngleUnit.RADIAN).value)

    override fun hashCode(): Int = this.convert(AngleUnit.RADIAN).value.hashCode()

    override fun equals(other: Any?): Boolean
    {
        if (this === other)
        {
            return true
        }

        if (other == null || other !is AngleFloat)
        {
            return false
        }

        return this.convert(AngleUnit.RADIAN).value.same(other.convert(AngleUnit.RADIAN).value)
    }

    override fun toString(): String = "${this.value}${this.unit.angleName}"

    override operator fun compareTo(other: AngleFloat): Int =
        this.convert(AngleUnit.RADIAN).value.compare(other.convert(AngleUnit.RADIAN).value)
}

/**
 * Multiply a factor to an angle
 */
operator fun Number.times(angle: AngleFloat): AngleFloat = angle * this
