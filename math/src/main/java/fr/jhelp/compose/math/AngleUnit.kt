package fr.jhelp.compose.math

const val DEGREE_NAME = "deg"
const val RADIAN_NAME = "rad"
const val GRADE_NAME = "grad"

fun angleUnitByName(name: String) = AngleUnit.values().first { it.angleName == name }

/**
 * Managed angle unit
 */
enum class AngleUnit(val angleName: String, private val modularizeValue: Double)
{
    DEGREE(DEGREE_NAME, 360.0),
    RADIAN(RADIAN_NAME, TWO_PI),
    GRADE(GRADE_NAME, 400.0)
    ;

    /**
     * Modularize the value for this unit
     */
    fun modularize(value: Double) = modulo(value, this.modularizeValue)

    /**
     * Modularize the value for this unit
     */
    fun modularize(value: Float) = modulo(value, this.modularizeValue.toFloat())
}
