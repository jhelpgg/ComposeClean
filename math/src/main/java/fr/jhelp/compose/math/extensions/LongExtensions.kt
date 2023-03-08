package fr.jhelp.compose.math.extensions

import fr.jhelp.compose.math.anotations.Milliseconds
import kotlin.math.abs
import kotlin.math.max
import kotlin.math.min


/**
 * Greatest Common Divider
 */
infix fun Long.GCD(integer: Long): Long
{
    var minimum = min(abs(this), abs(integer))
    var maximum = max(abs(this), abs(integer))
    var temporary: Long

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
infix fun Long.LCM(integer: Long): Long
{
    val gcd = this GCD integer

    if (gcd == 0L)
    {
        return 0L
    }

    return this * (integer / gcd)
}

/** Number of seconds converted in milliseconds*/
inline val @receiver:Milliseconds Long.seconds: Long get() = this * 1000L

/** Number of minutes converted in milliseconds*/
inline val @receiver:Milliseconds Long.minutes: Long get() = this * 60L * 1000L

/** Number of hours converted in milliseconds*/
inline val @receiver:Milliseconds Long.hours: Long get() = this * 60L * 60L * 1000L

/** Number of days converted in milliseconds*/
inline val @receiver:Milliseconds Long.days: Long get() = this * 24L * 60L * 60L * 1000L
