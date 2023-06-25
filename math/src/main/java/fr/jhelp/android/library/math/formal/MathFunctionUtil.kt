package fr.jhelp.android.library.math.formal

/**
 * Indicates if function is simple, that is to say a constant or a variable
 */
fun MathFunction<*>.simpleType(): Boolean =
    this is Constant || this is Variable

/**
 * Make an addition of several functions
 */
fun addition(list: List<MathFunction<*>>): MathFunction<*>
{
    if (list.isEmpty())
    {
        return ZERO
    }

    var result = list[0]

    for (index in 1 until list.size)
    {
        result += list[index]
    }

    return result
}

/**
 * Make an addition of several functions
 */
fun addition(vararg functions: MathFunction<*>): MathFunction<*>
{
    if (functions.isEmpty())
    {
        return ZERO
    }

    var result = functions[0]

    for (index in 1 until functions.size)
    {
        result += functions[index]
    }

    return result
}

/**
 * Make an multiplication of several functions
 */
fun multiplication(list: List<MathFunction<*>>): MathFunction<*>
{
    if (list.isEmpty())
    {
        return ZERO
    }

    var result = list[0]

    for (index in 1 until list.size)
    {
        result *= list[index]
    }

    return result
}

/**
 * Make an multiplication of several functions
 */
fun multiplication(vararg functions: MathFunction<*>): MathFunction<*>
{
    if (functions.isEmpty())
    {
        return ZERO
    }

    var result = functions[0]

    for (index in 1 until functions.size)
    {
        result *= functions[index]
    }

    return result
}