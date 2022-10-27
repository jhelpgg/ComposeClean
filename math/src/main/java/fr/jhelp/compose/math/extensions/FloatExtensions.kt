package fr.jhelp.compose.math.extensions

import fr.jhelp.compose.math.EPSILON_FLOAT
import kotlin.math.abs
import kotlin.math.ceil
import kotlin.math.floor
import kotlin.math.max
import kotlin.math.min
import kotlin.math.round

fun Float.bounds(value1: Float, value2: Float): Float =
    max(min(value1, value2), min(this, max(value1, value2)))

/**
 * The maximum integer lower or equals to this number
 */
val Float.floor get() = floor(this).toInt()

/**
 * The minimum integer greater or equals to this number
 */
val Float.ciel get() = ceil(this).toInt()

/**
 * The nearest integer to this number
 */
val Float.round get() = round(this).toInt()

/**
 * Indicates if Float can be assimilate to zero
 */
val Float.nul get() = abs(this) <= EPSILON_FLOAT

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
fun Float.compare(real: Float) =
    (this - real).sign()

/**
 * Indicates if other Float is same aas this one
 */
fun Float.same(real: Float) =
    abs(this - real).nul
