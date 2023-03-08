package fr.jhelp.compose.math.formal

import fr.jhelp.compose.math.extensions.compare
import fr.jhelp.compose.math.extensions.nul
import fr.jhelp.compose.math.extensions.same
import fr.jhelp.compose.math.extensions.sign

/**
 * Constant mathematical function
 */
class Constant(val value: Double) : MathFunction<Constant>()
{
    /**Indicates if constant is invalid*/
    val invalid = this.value.isNaN() || this.value.isInfinite()

    /**Indicates if constant is 0*/
    val zero = this.value.nul

    /**Indicates if constant is 1*/
    val one = this.value.compare(1.0) == 0

    /**Indicates if constant is -1*/
    val minusOne = this.value.compare(-1.0) == 0

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
    val sign = this.value.sign()

    override fun equalsIntern(mathFunction: Constant) =
        when
        {
            this.invalid         -> mathFunction.invalid
            mathFunction.invalid -> false
            else                 -> this.value.same(mathFunction.value)
        }

    override fun hash() =
        this.value.hashCode()

    /**
     * Compute derivative on given variable
     */
    override fun derivative(variable: Variable): MathFunction<*> = ZERO

    override fun toString(): String =
        if (this.invalid) "INVALID"
        else this.value.toString()

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

    operator fun unaryMinus(): Constant =
        when
        {
            this.invalid  -> INVALID
            this.zero     -> ZERO
            this.one      -> MINUS_ONE
            this.minusOne -> ONE
            else          -> Constant(-this.value)
        }

    operator fun div(value: Number): Constant =
        this / value.toDouble()

    operator fun div(value: Double): Constant =
        if (this.invalid || value.isNaN() || value.isInfinite() || value.nul) INVALID
        else this / Constant(value)

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

    operator fun Number.div(constant: Constant) =
        Constant(this.toDouble()) / constant

    operator fun minus(value: Number): Constant =
        this - value.toDouble()

    operator fun minus(value: Double): Constant =
        if (this.invalid || value.isNaN() || value.isInfinite()) INVALID
        else this - Constant(value)

    operator fun minus(constant: Constant): Constant =
        when
        {
            this.invalid || constant.invalid -> INVALID
            this.zero                        -> -constant
            constant.zero                    -> this
            this == constant                 -> ZERO
            else                             -> Constant(this.value - constant.value)
        }

    operator fun Number.minus(constant: Constant) =
        Constant(this.toDouble()) - constant

    operator fun plus(value: Number): Constant =
        this + value.toDouble()

    operator fun plus(value: Double): Constant =
        if (this.invalid || value.isNaN() || value.isInfinite()) INVALID
        else this + Constant(value)

    operator fun plus(constant: Constant): Constant =
        when
        {
            this.invalid || constant.invalid -> INVALID
            this.zero                        -> constant
            constant.zero                    -> this
            else                             -> Constant(this.value + constant.value)
        }

    operator fun Number.plus(constant: Constant) =
        Constant(this.toDouble()) + constant

    operator fun times(value: Number): Constant =
        this * value.toDouble()

    operator fun times(value: Double): Constant =
        if (this.invalid || value.isNaN() || value.isInfinite()) INVALID
        else this * Constant(value)

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

    operator fun Number.times(constant: Constant) =
        Constant(this.toDouble()) * constant

    fun invert() =
        when
        {
            this.invalid || this.zero -> INVALID
            this.one                  -> ONE
            this.minusOne             -> MINUS_ONE
            else                      -> Constant(1.0 / this.value)
        }
}

val INVALID = Constant(Double.NaN)

val ZERO = Constant(0.0)

val ONE = Constant(1.0)

val MINUS_ONE = Constant(-1.0)