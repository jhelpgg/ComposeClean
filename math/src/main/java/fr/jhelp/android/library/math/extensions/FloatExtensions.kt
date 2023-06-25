package fr.jhelp.android.library.math.extensions

import fr.jhelp.android.library.math.EPSILON_FLOAT
import kotlin.math.abs
import kotlin.math.ceil
import kotlin.math.floor
import kotlin.math.max
import kotlin.math.min
import kotlin.math.round

/**
 * Limit a float in given interval
 */
fun Float.bounds(value1: Float, value2: Float): Float =
    max(min(value1, value2), min(this, max(value1, value2)))

/**
 * The maximum integer lower or equals to this number
 */
val Float.floor: Int get() = floor(this).toInt()

/**
 * The minimum integer greater or equals to this number
 */
val Float.ciel: Int get() = ceil(this).toInt()

/**
 * The nearest integer to this number
 */
val Float.round: Int get() = round(this).toInt()

/**
 * Indicates if Float can be assimilate to zero
 */
val Float.nul: Boolean get() = abs(this) <= EPSILON_FLOAT

/**
 * Float sign
 */
fun Float.sign(): Int =
    when
    {
        this.nul -> 0
        this > 0 -> 1
        else     -> -1
    }

/**
 * Compare with an other Float
 */
fun Float.compare(real: Float): Int =
    (this - real).sign()

/**
 * Indicates if other Float is same aas this one
 */
fun Float.same(real: Float): Boolean =
    abs(this - real).nul
