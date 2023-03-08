package fr.jhelp.compose.math.formal

/**
 * Sinus function
 */
class Sinus(parameter: MathFunction<*>) : UnaryOperator<Sinus>(parameter)
{
    override fun newInstance(parameter: MathFunction<*>): Sinus =
        sin(parameter)

    /**
     * One step in simplification
     */
    override fun simple(): MathFunction<*> =
        when (this.parameter)
        {
            INVALID       ->
                INVALID
            // sin(C1) = C2 where C2=sin(C1)
            is Constant   ->
                kotlin.math.sin(this.parameter.value).const
            // sin(-F) -> -sin(F)
            is UnaryMinus ->
                -sin(this.parameter.parameter.simplify())
            // TODO other cases
            else          ->
                sin(this.parameter.simplify())
        }

    /**
     * Compute derivative on given variable
     */
    override fun derivative(variable: Variable): MathFunction<*> =
        this.parameter.derivative(variable) * cos(this.parameter)

    override fun toString(): String =
        "sin(${this.parameter})"
}