package fr.jhelp.android.library.math

import java.util.Random
import kotlin.math.abs
import kotlin.math.min


private val random = Random()

/**
 * Choose a random value between given limits includes
 */
fun random(bound1: Int, bound2: Int): Int =
    min(bound1, bound2) + random.nextInt(abs(bound1 - bound2) + 1)

/**
 * Choose a random value between given limits
 */
fun random(bound1: Float, bound2: Float): Float =
    min(bound1, bound2) + random.nextFloat() * abs(bound1 - bound2)

/**
 * Choose a random value in the array.
 *
 * If the array is empty an error happen
 */
fun <T> random(array: Array<T>): T =
    array[random.nextInt(array.size)]

/**
 * Choose a random enum value in possibilities offers by the enum class
 */
fun <E : Enum<E>> random(enumClass: Class<E>): E =
    random(enumClass.enumConstants!!)

