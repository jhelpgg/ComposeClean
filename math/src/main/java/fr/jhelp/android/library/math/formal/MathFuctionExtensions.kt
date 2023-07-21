package fr.jhelp.android.library.math.formal

/**
 * Transform a Double to constant
 */
val Double.const: Constant
    get() =
        Constant(this).simplify() as Constant

/**
 * Transform a Number to constant
 */
val Number.const: Constant
    get() =
        this.toDouble().const

/**
 * Transform a String to variable
 */
val String.variable: Variable
    get() =
        Variable(this)

/** X variable */
val X: Variable = "X".variable

/** Y variable */
val Y: Variable = "Y".variable

/** Z variable */
val Z: Variable = "Z".variable

/** T variable */
val T: Variable = "T".variable

/** A variable */
val A: Variable = "A".variable

/** Alpha variable */
val alpha: Variable = "alpha".variable

/** Epsilon variable */
val epsilon: Variable = "epsilon".variable

/***
 * Function opposite
 */
operator fun MathFunction<*>.unaryMinus(): UnaryMinus =
    UnaryMinus(this)

/**
 * Add an other mathematical function
 */
operator fun MathFunction<*>.plus(mathFunction: MathFunction<*>): Addition =
    Addition(this, mathFunction)

/**
 * Subtract an other mathematical function
 */
operator fun MathFunction<*>.minus(mathFunction: MathFunction<*>): Subtraction =
    Subtraction(this, mathFunction)

/**
 * Multiply an other mathematical function
 */
operator fun MathFunction<*>.times(mathFunction: MathFunction<*>): Multiplication =
    Multiplication(this, mathFunction)

/**
 * Divide an other mathematical function
 */
operator fun MathFunction<*>.div(mathFunction: MathFunction<*>): Division =
    Division(this, mathFunction)

/**
 * Add a number
 */
operator fun MathFunction<*>.plus(number: Number): Addition =
    number.const + this

/**
 * Subtract a number
 */
operator fun MathFunction<*>.minus(number: Number): Addition =
    -number.const + this

/**
 * Multiply a number
 */
operator fun MathFunction<*>.times(number: Number): Multiplication =
    number.const * this

/**
 * Divide a number
 */
operator fun MathFunction<*>.div(number: Number): Multiplication =
    number.const.invert() * this

/** Add mathematical function to this number */
operator fun Number.plus(mathFunction: MathFunction<*>): Addition =
    this.const + mathFunction

/** Subtract mathematical function to this number */
operator fun Number.minus(mathFunction: MathFunction<*>): Subtraction =
    this.const - mathFunction

/** Multiply mathematical function to this number */
operator fun Number.times(mathFunction: MathFunction<*>): Multiplication =
    this.const * mathFunction

/** Divide mathematical function to this number */
operator fun Number.div(mathFunction: MathFunction<*>): Division =
    this.const / mathFunction

/** Add variable to mathematical function */
operator fun MathFunction<*>.plus(string: String): Addition =
    this + string.variable

/** Subtract variable to mathematical function */
operator fun MathFunction<*>.minus(string: String): Subtraction =
    this - string.variable

/** Multiply variable to mathematical function */
operator fun MathFunction<*>.times(string: String): Multiplication =
    this * string.variable

/** Divide variable to mathematical function */
operator fun MathFunction<*>.div(string: String): Division =
    this / string.variable

/** Compute cosinus of mathematical function */
fun cos(mathFunction: MathFunction<*>): Cosinus =
    Cosinus(mathFunction)

/** Compute sinus of mathematical function */
fun sin(mathFunction: MathFunction<*>): Sinus =
    Sinus(mathFunction)