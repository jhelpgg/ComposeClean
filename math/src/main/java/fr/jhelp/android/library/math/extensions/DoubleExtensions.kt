package fr.jhelp.android.library.math.extensions

import fr.jhelp.android.library.math.EPSILON
import kotlin.math.abs
import kotlin.math.max
import kotlin.math.min

/**
 * Limit a double in given interval
 */
fun Double.bounds(value1: Double, value2: Double): Double =
    max(min(value1, value2), min(this, max(value1, value2)))

/**
 * Indicates if Double can be assimilate to zero
 */
val Double.nul: Boolean get() = abs(this) <= EPSILON

/**
 * Double sign
 */
fun Double.sign(): Int =
    when
    {
        this.nul -> 0
        this > 0 -> 1
        else     -> -1
    }


/**
 * Compare with an other Double
 */
fun Double.compare(real: Double): Int =
    (this - real).sign()


/**
 * Indicates if other Double is same aas this one
 */
fun Double.same(real: Double): Boolean =
    abs(this - real).nul
