package fr.jhelp.compose.math.extensions

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
