package fr.jhelp.compose.math

import java.util.Random
import kotlin.math.abs
import kotlin.math.min


private val random = Random()

fun random(bound1: Int, bound2: Int): Int =
    min(bound1, bound2) + random.nextInt(abs(bound1 - bound2 + 1))

fun random(bound1: Float, bound2: Float): Float =
    min(bound1, bound2) + random.nextFloat() * abs(bound1 - bound2)
