package fr.jhelp.compose.math.formal

import fr.jhelp.compose.math.extensions.compare
import fr.jhelp.compose.math.extensions.nul
import fr.jhelp.compose.math.extensions.same
import fr.jhelp.compose.math.extensions.sign

/**
 * Constant mathematical function
 * @property value Constant value
 */
class Constant(val value: Double) : MathFunction<Constant>()
{
    /**Indicates if constant is invalid*/
    val invalid: Boolean = this.value.isNaN() || this.value.isInfinite()

    /**Indicates if constant is 0*/
    val zero: Boolean = this.value.nul

    /**Indicates if constant is 1*/
    val one: Boolean = this.value.compare(1.0) == 0

    /**Indicates if constant is -1*/
    val minusOne: Boolean = this.value.compare(-1.0) == 0

    /**
     * Simplification one step
     */
    override fun simple(): MathFunction<*> =
        when
        {
            this.invalid  -> INVALID
            this.zero     -> ZERO
            this.one      -> ONE
            this.minusOne -> MINUS_ONE
            else          -> this
        }

    /**Constant sign*/
    val sign: Int = this.value.sign()

    override fun equalsIntern(mathFunction: Constant): Boolean =
        when
        {
            this.invalid         -> mathFunction.invalid
            mathFunction.invalid -> false
            else                 -> this.value.same(mathFunction.value)
        }

    /**
     * Hash code
     */
    override fun hash(): Int =
        this.value.hashCode()

    /**
     * Compute derivative on given variable
     */
    override fun derivative(variable: Variable): MathFunction<*> = ZERO

    override fun toString(): String =
        if (this.invalid) "INVALID"
        else this.value.toString()

    /**
     * Compare to an other constant
     */
    operator fun compareTo(other: Constant): Int
    {
        if (this.invalid)
        {
            if (other.invalid)
            {
                return 0
            }

            return 1
        }

        if (other.invalid)
        {
            return -1
        }

        return this.value.compare(other.value)
    }

    /**
     * Compute the opposite
     */
    operator fun unaryMinus(): Constant =
        when
        {
            this.invalid  -> INVALID
            this.zero     -> ZERO
            this.one      -> MINUS_ONE
            this.minusOne -> ONE
            else          -> Constant(-this.value)
        }

    /**
     * Divide per a number
     */
    operator fun div(value: Number): Constant =
        this / value.toDouble()

    /**
     * Divide per a double
     */
    operator fun div(value: Double): Constant =
        if (this.invalid || value.isNaN() || value.isInfinite() || value.nul) INVALID
        else this / Constant(value)

    /**
     * Divide per an other constant
     */
    operator fun div(constant: Constant): Constant =
        when
        {
            this.invalid || constant.invalid || constant.zero -> INVALID
            this.zero                                         -> ZERO
            constant.one                                      -> this
            constant.minusOne                                 -> -this
            else                                              ->
                Constant(this.value / constant.value)
        }

    /**
     * Divide a number per a constant
     */
    operator fun Number.div(constant: Constant): Constant =
        Constant(this.toDouble()) / constant

    /**
     * Subtract a number
     */
    operator fun minus(value: Number): Constant =
        this - value.toDouble()

    /**
     * Subtract a double
     */
    operator fun minus(value: Double): Constant =
        if (this.invalid || value.isNaN() || value.isInfinite()) INVALID
        else this - Constant(value)

    /**
     * Subtract a constant
     */
    operator fun minus(constant: Constant): Constant =
        when
        {
            this.invalid || constant.invalid -> INVALID
            this.zero                        -> -constant
            constant.zero                    -> this
            this == constant                 -> ZERO
            else                             -> Constant(this.value - constant.value)
        }

    /**
     * Subtract a number per a constant
     */
    operator fun Number.minus(constant: Constant): Constant =
        Constant(this.toDouble()) - constant

    /**
     * Add a number
     */
    operator fun plus(value: Number): Constant =
        this + value.toDouble()

    /**
     * Add a double
     */
    operator fun plus(value: Double): Constant =
        if (this.invalid || value.isNaN() || value.isInfinite()) INVALID
        else this + Constant(value)

    /**
     * Add a constant
     */
    operator fun plus(constant: Constant): Constant =
        when
        {
            this.invalid || constant.invalid -> INVALID
            this.zero                        -> constant
            constant.zero                    -> this
            else                             -> Constant(this.value + constant.value)
        }

    /**
     * Add a number with a constant
     */
    operator fun Number.plus(constant: Constant): Constant =
        Constant(this.toDouble()) + constant

    /**
     * Multiply a number
     */
    operator fun times(value: Number): Constant =
        this * value.toDouble()

    /**
     * Multiply a double
     */
    operator fun times(value: Double): Constant =
        if (this.invalid || value.isNaN() || value.isInfinite()) INVALID
        else this * Constant(value)

    /**
     * Multiply a constant
     */
    operator fun times(constant: Constant): Constant =
        when
        {
            this.invalid || constant.invalid -> INVALID
            this.zero || constant.zero       -> ZERO
            this.one                         -> constant
            constant.one                     -> this
            this.minusOne                    -> -constant
            constant.minusOne                -> -this
            else                             -> Constant(this.value * constant.value)
        }

    /**
     * Multiply a number with a constant
     */
    operator fun Number.times(constant: Constant): Constant =
        Constant(this.toDouble()) * constant

    /**
     * Compute the invert
     */
    fun invert(): Constant =
        when
        {
            this.invalid || this.zero -> INVALID
            this.one                  -> ONE
            this.minusOne             -> MINUS_ONE
            else                      -> Constant(1.0 / this.value)
        }
}

/** Invalid constant */
val INVALID: Constant = Constant(Double.NaN)

/** Zero constant */
val ZERO: Constant = Constant(0.0)

/** One constant */
val ONE: Constant = Constant(1.0)

/** Minus one constant */
val MINUS_ONE: Constant = Constant(-1.0)