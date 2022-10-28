package fr.jhelp.compose.math.extensions

import fr.jhelp.compose.math.COLOR_MASK
import fr.jhelp.compose.math.anotations.Milliseconds
import kotlin.math.abs
import kotlin.math.max
import kotlin.math.min

fun Int.bounds(value1: Int, value2: Int): Int =
    max(min(value1, value2), min(this, max(value1, value2)))

fun Int.useAlpha(colorAlpha: Int) = (this and COLOR_MASK) or colorAlpha

/**
 * Greatest Common Divider
 */
infix fun Int.GCD(integer: Int): Int
{
    var minimum = min(abs(this), abs(integer))
    var maximum = max(abs(this), abs(integer))
    var temporary: Int

    while (minimum > 0)
    {
        temporary = minimum
        minimum = maximum % minimum
        maximum = temporary
    }

    return maximum
}

/**
 * Lowest Common Multiple
 */
infix fun Int.LCM(integer: Int): Int
{
    val gcd = this GCD integer

    if (gcd == 0)
    {
        return 0
    }

    return this * (integer / gcd)
}

/** Number of seconds converted in milliseconds*/
inline val @receiver:Milliseconds Int.seconds: Int get() = this * 1000

/** Number of minutes converted in milliseconds*/
inline val @receiver:Milliseconds Int.minutes: Int get() = this * 60 * 1000

/** Number of hours converted in milliseconds*/
inline val @receiver:Milliseconds Int.hours: Int get() = this * 60 * 60 * 1000

/** Number of days converted in milliseconds*/
inline val @receiver:Milliseconds Int.days: Int get() = this * 24 * 60 * 60 * 1000
