package fr.jhelp.compose.math

import fr.jhelp.compose.math.extensions.nul
import kotlin.math.E
import kotlin.math.PI
import kotlin.math.abs
import kotlin.math.acos
import kotlin.math.exp
import kotlin.math.floor
import kotlin.math.max
import kotlin.math.min
import kotlin.math.sqrt

/** PI in float precision */
const val PI_FLOAT: Float = PI.toFloat()

/** E in float precision */
const val E_FLOAT: Float = E.toFloat()

/** Two PI or Theta */
const val TWO_PI: Double = 2.0 * PI

/** Two PI or Theta  in float precision*/
const val TWO_PI_FLOAT: Float = 2.0f * PI_FLOAT

/**
 * Double precision, the "zero"
 */
val EPSILON: Double = maximum(Double.MIN_VALUE, abs(E - exp(1.0)), abs(PI - acos(-1.0)))

/**
 * Float precision, the "zero"
 */
val EPSILON_FLOAT: Float =
    maximum(Float.MIN_VALUE, abs(E_FLOAT - exp(1.0f)), abs(PI_FLOAT - acos(-1.0f)))

/** X axis */
val AXIS_X: Vector3D = Vector3D(1f, 0f, 0F)

/** Y axis */
val AXIS_Y: Vector3D = Vector3D(0f, 1f, 0F)

/** Z axis */
val AXIS_Z: Vector3D = Vector3D(0f, 0f, 1f)

/**
 * Square of number
 */
fun square(number: Int): Int = number * number

/**
 * Square of number
 */
fun square(number: Long): Long = number * number

/**
 * Square of number
 */
fun square(number: Float): Float = number * number

/**
 * Square of number
 */
fun square(number: Double): Double = number * number

/**
 * Square root of number
 */
fun sqrt(number: Int): Int = sqrt(number.toDouble()).toInt()

/**
 * Square root of number
 */
fun sqrt(number: Long): Long = sqrt(number.toDouble()).toLong()

/**
 * Distance between two points
 */
fun distance(point1: Point2D, point2: Point2D): Float =
    sqrt(square(point1.x - point2.x) + square(point1.y - point2.y))

/**
 * Distance between two points
 */
fun distance(point1: Point3D, point2: Point3D): Float =
    sqrt(square(point1.x - point2.x) + square(point1.y - point2.y) + square(point1.z - point2.z))

/**
 * Distance between two points
 */
fun distance(x1: Float, y1: Float, x2: Float, y2: Float): Float =
    sqrt(square(x1 - x2) + square(y1 - y2))

/**
 * Distance between two points
 */
fun distance(x1: Float, y1: Float, z1: Float, x2: Float, y2: Float, z2: Float): Float =
    sqrt(square(x1 - x2) + square(y1 - y2) + square(z1 - z2))


/**
 * Maximum of several integers
 */
fun maximum(value: Int, vararg others: Int): Int
{
    var maximum = value

    for (integer in others)
    {
        maximum = max(maximum, integer)
    }

    return maximum
}

/**
 * Maximum of several integers
 */
fun maximum(value: Long, vararg others: Long): Long
{
    var maximum = value

    for (integer in others)
    {
        maximum = max(maximum, integer)
    }

    return maximum
}

/**
 * Maximum of several real
 */
fun maximum(value: Float, vararg others: Float): Float
{
    var maximum = value

    for (integer in others)
    {
        maximum = max(maximum, integer)
    }

    return maximum
}

/**
 * Maximum of several real
 */
fun maximum(value: Double, vararg others: Double): Double
{
    var maximum = value

    for (integer in others)
    {
        maximum = max(maximum, integer)
    }

    return maximum
}

/**
 * Minimum of several integers
 */
fun minimum(value: Int, vararg others: Int): Int
{
    var minimum = value

    for (integer in others)
    {
        minimum = min(minimum, integer)
    }

    return minimum
}

/**
 * Minimum of several integers
 */
fun minimum(value: Long, vararg others: Long): Long
{
    var minimum = value

    for (integer in others)
    {
        minimum = min(minimum, integer)
    }

    return minimum
}

/**
 * Minimum of several real
 */
fun minimum(value: Float, vararg others: Float): Float
{
    var minimum = value

    for (integer in others)
    {
        minimum = min(minimum, integer)
    }

    return minimum
}

/**
 * Minimum of several real
 */
fun minimum(value: Double, vararg others: Double): Double
{
    var minimum = value

    for (integer in others)
    {
        minimum = min(minimum, integer)
    }

    return minimum
}

/**
 * Log2 base 2 of given integer
 */
fun log2(integer: Int): Int
{
    if (integer <= 1)
    {
        return 0
    }

    var left = integer shr 1
    var log2 = 1

    while (left > 1)
    {
        log2++
        left = left shr 1
    }

    return log2
}


/**
 * Compute the cubic interpolation
 *
 * @param cp Start value
 * @param p1 First control point
 * @param p2 Second control point
 * @param p3 Third control point
 * @param t  Factor in [0, 1]
 * @return Interpolation
 */
fun cubic(cp: Double, p1: Double, p2: Double, p3: Double, t: Double): Double
{
    val u = 1.0 - t
    return u * u * u * cp + 3.0 * t * u * u * p1 + 3.0 * t * t * u * p2 + t * t * t * p3
}

/**
 * Compute several cubic interpolation
 *
 * @param cp        Start value
 * @param p1        First control point
 * @param p2        Second control point
 * @param p3        Third control point
 * @param precision Number of interpolation
 * @param cubic     Where write interpolations. If `null` or length too small, a new array is created
 * @return Interpolations
 */
fun cubic(cp: Double, p1: Double, p2: Double, p3: Double, precision: Int,
          cubic: DoubleArray? = null): DoubleArray
{
    var cubicLocal = cubic
    var actual: Double

    if (cubicLocal == null || cubicLocal.size < precision)
    {
        cubicLocal = DoubleArray(precision)
    }

    val step = 1.0 / (precision - 1.0)
    actual = 0.0

    for (i in 0 until precision)
    {
        if (i == precision - 1)
        {
            actual = 1.0
        }

        cubicLocal[i] = cubic(cp, p1, p2, p3, actual)
        actual += step
    }

    return cubicLocal
}


/**
 * Compute the quadratic interpolation
 *
 * @param cp Start value
 * @param p1 First control point
 * @param p2 Second control point
 * @param t  Factor in [0, 1]
 * @return Interpolation
 */
fun quadratic(cp: Double, p1: Double, p2: Double, t: Double): Double
{
    val u = 1.0 - t
    return u * u * cp + 2.0 * t * u * p1 + t * t * p2
}

/**
 * Compute several quadratic interpolation
 *
 * @param cp        Start value
 * @param p1        First control point
 * @param p2        Second control point
 * @param precision Number of interpolation
 * @param quadratic Where write interpolations
 * @return Interpolations
 */
fun quadratic(cp: Double, p1: Double, p2: Double, precision: Int,
              quadratic: DoubleArray? = null): DoubleArray
{
    var quadraticLocal = quadratic
    var actual: Double

    if (quadraticLocal == null || quadraticLocal.size < precision)
    {
        quadraticLocal = DoubleArray(precision)
    }

    val step = 1.0 / (precision - 1.0)
    actual = 0.0

    for (i in 0 until precision)
    {
        if (i == precision - 1)
        {
            actual = 1.0
        }

        quadraticLocal[i] = quadratic(cp, p1, p2, actual)
        actual += step
    }

    return quadraticLocal
}


/**
 * Compute the cubic interpolation
 *
 * @param cp Start value
 * @param p1 First control point
 * @param p2 Second control point
 * @param p3 Third control point
 * @param t  Factor in [0, 1]
 * @return Interpolation
 */
fun cubic(cp: Float, p1: Float, p2: Float, p3: Float, t: Float): Float
{
    val u = 1.0f - t
    return u * u * u * cp + 3.0f * t * u * u * p1 + 3.0f * t * t * u * p2 + t * t * t * p3
}

/**
 * Compute several cubic interpolation
 *
 * @param cp        Start value
 * @param p1        First control point
 * @param p2        Second control point
 * @param p3        Third control point
 * @param precision Number of interpolation
 * @param cubic     Where write interpolations. If `null` or length too small, a new array is created
 * @return Interpolations
 */
fun cubic(cp: Float, p1: Float, p2: Float, p3: Float, precision: Int,
          cubic: FloatArray? = null): FloatArray
{
    var cubicLocal = cubic
    var actual: Float

    if (cubicLocal == null || cubicLocal.size < precision)
    {
        cubicLocal = FloatArray(precision)
    }

    val step = 1.0f / (precision - 1.0f)
    actual = 0.0f

    for (i in 0 until precision)
    {
        if (i == precision - 1)
        {
            actual = 1.0f
        }

        cubicLocal[i] = cubic(cp, p1, p2, p3, actual)
        actual += step
    }

    return cubicLocal
}


/**
 * Compute the quadratic interpolation
 *
 * @param cp Start value
 * @param p1 First control point
 * @param p2 Second control point
 * @param t  Factor in [0, 1]
 * @return Interpolation
 */
fun quadratic(cp: Float, p1: Float, p2: Float, t: Float): Float
{
    val u = 1.0f - t
    return u * u * cp + 2.0f * t * u * p1 + t * t * p2
}

/**
 * Compute several quadratic interpolation
 *
 * @param cp        Start value
 * @param p1        First control point
 * @param p2        Second control point
 * @param precision Number of interpolation
 * @param quadratic Where write interpolations
 * @return Interpolations
 */
fun quadratic(cp: Float, p1: Float, p2: Float, precision: Int,
              quadratic: FloatArray? = null): FloatArray
{
    var quadraticLocal = quadratic
    var actual: Float

    if (quadraticLocal == null || quadraticLocal.size < precision)
    {
        quadraticLocal = FloatArray(precision)
    }

    val step = 1.0f / (precision - 1.0f)
    actual = 0.0f

    for (i in 0 until precision)
    {
        if (i == precision - 1)
        {
            actual = 1.0f
        }

        quadraticLocal[i] = quadratic(cp, p1, p2, actual)
        actual += step
    }

    return quadraticLocal
}

/**
 * Compute the modulo of a real
 *
 * @param real   Real to modulate
 * @param modulo Modulo to use
 * @return Result
 */
fun modulo(real: Double, modulo: Double) = moduloInterval(real, 0.0, modulo)

/**
 * Compute the modulo of a real
 *
 * @param real   Real to modulate
 * @param modulo Modulo to use
 * @return Result
 */
fun modulo(real: Float, modulo: Float) = moduloInterval(real, 0f, modulo)

/**
 * Mathematical modulo.
 *
 * For computer -1 modulo 2 is -1, but in Mathematic -1[2]=1 (-1[2] : -1 modulo 2)
 *
 * @param integer Integer to modulate
 * @param modulo  Modulo to apply
 * @return Mathematical modulo : `integer[modulo]`
 */
fun modulo(integer: Int, modulo: Int): Int
{
    var integerLocal = integer
    integerLocal %= modulo

    if (integerLocal < 0 && modulo > 0 || integerLocal > 0 && modulo < 0)
    {
        integerLocal += modulo
    }

    return integerLocal
}

/**
 * Modulate an integer inside an interval
 *
 * @param integer Integer to modulate
 * @param min  Minimum of interval
 * @param max  Maximum of interval
 * @return Modulated value
 */
fun moduloInterval(integer: Int, min: Int, max: Int) = min + modulo(integer - min, max - min + 1)

/**
 * Modulate an integer inside an interval
 *
 * @param integer Integer to modulate
 * @param min  Minimum of interval
 * @param max  Maximum of interval
 * @return Modulated value
 */
fun moduloInterval(integer: Long, min: Long, max: Long) =
    min + modulo(integer - min, max - min + 1L)

/**
 * Mathematical modulo.
 *
 * For computer -1 modulo 2 is -1, but in Mathematic -1[2]=1 (-1[2] : -1 modulo 2)
 *
 * @param integer Integer to modulate
 * @param modulo  Modulo to apply
 * @return Mathematical modulo : `integer[modulo]`
 */
fun modulo(integer: Long, modulo: Long): Long
{
    var modulate = integer % modulo

    if (modulate < 0 && modulo > 0 || modulate > 0 && modulo < 0)
    {
        modulate += modulo
    }

    return modulate
}

/**
 * Modulate a real inside an interval
 *
 * @param real Real to modulate
 * @param min  Minimum of interval include
 * @param max  Maximum of interval exclude
 * @return Modulated value
 */
fun moduloInterval(real: Double, min: Double, max: Double): Double
{
    var realLocal = real
    var minLocal = min
    var maxLocal = max

    if (minLocal > maxLocal)
    {
        val temp = minLocal
        minLocal = maxLocal
        maxLocal = temp
    }

    if (realLocal >= minLocal && realLocal < maxLocal)
    {
        return realLocal
    }

    val space = maxLocal - minLocal

    if (space.nul)
    {
        throw IllegalArgumentException("Can't take modulo in empty interval")
    }

    realLocal = (realLocal - minLocal) / space

    return space * (realLocal - floor(realLocal)) + minLocal
}

/**
 * Modulate a real inside an interval
 *
 * @param real Real to modulate
 * @param min  Minimum of interval include
 * @param max  Maximum of interval exclude
 * @return Modulated value
 */
fun moduloInterval(real: Float, min: Float, max: Float): Float
{
    var realLocal = real
    var minLocal = min
    var maxLocal = max

    if (minLocal > maxLocal)
    {
        val temp = minLocal
        minLocal = maxLocal
        maxLocal = temp
    }

    if (realLocal >= minLocal && realLocal < maxLocal)
    {
        return realLocal
    }

    val space = maxLocal - minLocal

    if (space.nul)
    {
        throw IllegalArgumentException("Can't take modulo in empty interval")
    }

    realLocal = (realLocal - minLocal) / space

    return space * (realLocal - floor(realLocal)) + minLocal
}

/**
 * Convert degree to grade
 */
fun degreeToGrade(degree: Double): Double = degree * 0.9

/**
 * Convert radian to grade
 */
fun radianToGrade(radian: Double): Double = radian * 200.0 / PI

/**
 * Convert grade to degree
 */
fun gradeToDegree(grade: Double): Double = grade / 0.9

/**
 * Convert grade to radian
 */
fun gradeToRadian(grade: Double): Double = grade * PI / 200.0

/**
 * Convert degree to radian
 */
fun degreeToRadian(degree: Float): Float = degree * PI_FLOAT / 180.0f

/**
 * Convert radian to degree
 */
fun radianToDegree(radian: Float): Float = radian * 180.0f / PI_FLOAT

/**
 * Convert degree to grade
 */
fun degreeToGrade(degree: Float): Float = degree * 0.9f

/**
 * Convert radian to grade
 */
fun radianToGrade(radian: Float): Float = radian * 200.0f / PI_FLOAT

/**
 * Convert grade to degree
 */
fun gradeToDegree(grade: Float): Float = grade / 0.9f

/**
 * Convert grade to radian
 */
fun gradeToRadian(grade: Float): Float = grade * PI_FLOAT / 200.0f
