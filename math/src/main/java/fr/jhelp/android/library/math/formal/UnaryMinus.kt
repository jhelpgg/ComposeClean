package fr.jhelp.android.library.math.formal

/**
 * Represents unary minus function
 */
class UnaryMinus(parameter: MathFunction<*>) : UnaryOperator<UnaryMinus>(parameter)
{
    override fun newInstance(parameter: MathFunction<*>): UnaryMinus = -parameter

    /**
     * One step in simplification
     */
    override fun simple(): MathFunction<*> =
        when (this.parameter)
        {
            INVALID       -> INVALID
            // -0 -> 0
            ZERO          -> ZERO
            // -(1) -> -1
            ONE           -> MINUS_ONE
            // -(-1) -> 1
            MINUS_ONE     -> ONE
            // -(C1) -> C2 where C2 = -C1
            is Constant   -> -this.parameter
            // -(-F) -> F
            is UnaryMinus -> this.parameter.parameter.simplify()
            // TODO other cases
            else          -> -this.parameter.simplify()
        }

    /**
     * Compute derivative on given variable
     */
    override fun derivative(variable: Variable): MathFunction<*> =
        -this.parameter.derivative(variable)

    override fun toString(): String =
        if (this.parameter.simpleType() || this.parameter is UnaryOperator) "-${this.parameter}"
        else "-(${this.parameter})"
}