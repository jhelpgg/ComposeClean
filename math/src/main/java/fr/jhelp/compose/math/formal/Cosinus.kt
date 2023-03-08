package fr.jhelp.compose.math.formal

/**
 * Cosinus function
 */
class Cosinus(parameter: MathFunction<*>) : UnaryOperator<Cosinus>(parameter)
{
    override fun newInstance(parameter: MathFunction<*>): Cosinus =
        cos(parameter)

    /**
     * One step in simplification
     */
    override fun simple(): MathFunction<*> =
        when (this.parameter)
        {
            INVALID       ->
                INVALID
            // cos(C1) -> C2 where C2=cos(C1)
            is Constant   ->
                kotlin.math.cos(this.parameter.value).const
            // cos(-F) -> cos(F)
            is UnaryMinus ->
                cos(this.parameter.parameter.simplify())
            // TODO other cases
            else          ->
                cos(this.parameter.simplify())
        }

    /**
     * Compute derivative on given variable
     */
    override fun derivative(variable: Variable): MathFunction<*> =
        -this.parameter.derivative(variable) * sin(this.parameter)

    override fun toString(): String =
        "cos(${this.parameter})"
}