package fr.jhelp.compose.math.formal

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
 * Function oposite
 */
operator fun MathFunction<*>.unaryMinus(): MathFunction<*> =
    UnaryMinus(this)

/**
 * Add an other mathematical function
 */
operator fun MathFunction<*>.plus(mathFunction: MathFunction<*>): MathFunction<*> =
    Addition(this, mathFunction)

/**
 * Subtract an other mathematical function
 */
operator fun MathFunction<*>.minus(mathFunction: MathFunction<*>): MathFunction<*> =
    Subtraction(this, mathFunction)

/**
 * Multiply an other mathematical function
 */
operator fun MathFunction<*>.times(mathFunction: MathFunction<*>): MathFunction<*> =
    Multiplication(this, mathFunction)

/**
 * Divide an other mathematical function
 */
operator fun MathFunction<*>.div(mathFunction: MathFunction<*>): MathFunction<*> =
    Division(this, mathFunction)

/**
 * Add a number
 */
operator fun MathFunction<*>.plus(number: Number): MathFunction<*> =
    number.const + this

/**
 * Subtract a number
 */
operator fun MathFunction<*>.minus(number: Number): MathFunction<*> =
    -number.const + this

/**
 * Multiply a number
 */
operator fun MathFunction<*>.times(number: Number): MathFunction<*> =
    number.const * this

/**
 * Divide a number
 */
operator fun MathFunction<*>.div(number: Number): MathFunction<*> =
    number.const.invert() * this

/** Add mathematical function to this number */
operator fun Number.plus(mathFunction: MathFunction<*>): MathFunction<*> =
    this.const + mathFunction

/** Subtract mathematical function to this number */
operator fun Number.minus(mathFunction: MathFunction<*>): MathFunction<*> =
    this.const - mathFunction

/** Multiply mathematical function to this number */
operator fun Number.times(mathFunction: MathFunction<*>): MathFunction<*> =
    this.const * mathFunction

/** Divide mathematical function to this number */
operator fun Number.div(mathFunction: MathFunction<*>): MathFunction<*> =
    this.const / mathFunction

/** Add variable to mathematical function */
operator fun MathFunction<*>.plus(string: String): MathFunction<*> =
    this + string.variable

/** Subtract variable to mathematical function */
operator fun MathFunction<*>.minus(string: String): MathFunction<*> =
    this - string.variable

/** Multiply variable to mathematical function */
operator fun MathFunction<*>.times(string: String): MathFunction<*> =
    this * string.variable

/** Divide variable to mathematical function */
operator fun MathFunction<*>.div(string: String): MathFunction<*> =
    this / string.variable

/** Compute cosinus of mathematical function */
fun cos(mathFunction: MathFunction<*>): MathFunction<*> =
    Cosinus(mathFunction)

/** Compute sinus of mathematical function */
fun sin(mathFunction: MathFunction<*>): MathFunction<*> =
    Sinus(mathFunction)