package fr.jhelp.android.library.math

/** Degree unit name */
const val DEGREE_NAME: String = "deg"

/** Radian unit name */
const val RADIAN_NAME: String = "rad"

/** Grade unit name */
const val GRADE_NAME: String = "grad"

/**
 * Managed angle unit
 * @property angleName Name of the unit
 */
enum class AngleUnit(val angleName: String, private val modularizeValue: Double)
{
    /** Represents degree unit for angles */
    DEGREE(DEGREE_NAME, 360.0),

    /** Radian degree unit for angles */
    RADIAN(RADIAN_NAME, TWO_PI),

    /** Represents grade unit for angles */
    GRADE(GRADE_NAME, 400.0)
    ;

    /**
     * Modularize the value for this unit
     */
    fun modularize(value: Float): Float = modulo(value, this.modularizeValue.toFloat())
}
