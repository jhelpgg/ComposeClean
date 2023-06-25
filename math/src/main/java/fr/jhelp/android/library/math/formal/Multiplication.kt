package fr.jhelp.android.library.math.formal

/**
 * Multiplication between two functions
 */
class Multiplication(parameter1: MathFunction<*>, parameter2: MathFunction<*>) :
        BinaryOperator<Multiplication>(parameter1, parameter2, false)
{
    override fun newInstance(parameter1: MathFunction<*>,
                             parameter2: MathFunction<*>): Multiplication =
        parameter1 * parameter2

    /**
     * One step in simplification
     */
    override fun simple(): MathFunction<*> =
        when
        {
            this.parameter1 == INVALID || this.parameter2 == INVALID                                                   ->
                INVALID
            // F * 0  = 0  || 0 * F -> 0
            this.parameter1 == ZERO || this.parameter2 == ZERO                                                         ->
                ZERO
            // 1 * F -> F
            this.parameter1 == ONE                                                                                     ->
                this.parameter2.simplify()
            // -1 * F -> -F
            this.parameter1 == MINUS_ONE                                                                               ->
                -this.parameter2.simplify()
            // F * 1 -> F
            this.parameter2 == ONE                                                                                     ->
                this.parameter1.simplify()
            // F * -1 -> -F
            this.parameter2 == MINUS_ONE                                                                               ->
                -this.parameter1.simplify()
            // C1 * C2 = C3 where C3 = C1*C2
            this.parameter1 is Constant && this.parameter2 is Constant                                                 ->
                this.parameter1 * this.parameter2
            // (-F1) * (-F2) -> F1 * F2
            this.parameter1 is UnaryMinus && this.parameter2 is UnaryMinus                                             ->
                this.parameter1.parameter.simplify() * this.parameter2.parameter.simplify()
            // (-F1) * F2 -> -(F1 * F2)
            this.parameter1 is UnaryMinus                                                                              ->
                -(this.parameter1.parameter.simplify() * this.parameter2.simplify())
            // F1 * (-F2) -> -(F1 * F2)
            this.parameter2 is UnaryMinus                                                                              ->
                -(this.parameter1.simplify() * this.parameter2.parameter.simplify())
            // C1 * (F2 * C2) -> C3 * F2 where C3 = C1 * C2
            this.parameter1 is Constant && this.parameter2 is Multiplication && this.parameter2.parameter2 is Constant ->
                (this.parameter1 * this.parameter2.parameter2) * this.parameter2.parameter1.simplify()
            // C1 * (C2 * F2) -> C3 * F2 where C3 = C1 * C2
            this.parameter1 is Constant && this.parameter2 is Multiplication && this.parameter2.parameter1 is Constant ->
                (this.parameter1 * this.parameter2.parameter1) * this.parameter2.parameter2.simplify()
            // C * F -> C * F
            this.parameter1 is Constant                                                                                ->
                this.parameter1 * this.parameter2.simplify()
            // (F1 * C1) * C2 -> C3 * F1 where C3 = C1 * C2
            this.parameter2 is Constant && this.parameter1 is Multiplication && this.parameter1.parameter2 is Constant ->
                (this.parameter1.parameter2 * this.parameter2) * this.parameter1.parameter1.simplify()
            // (C1 * F1) * C2 -> C3 * F1 where C3 = C1 * C2
            this.parameter2 is Constant && this.parameter1 is Multiplication && this.parameter1.parameter1 is Constant ->
                (this.parameter1.parameter1 * this.parameter2) * this.parameter1.parameter2.simplify()
            // F * C -> C * F
            this.parameter2 is Constant                                                                                ->
                this.parameter2 * this.parameter1.simplify()
            // (C1 * F1) * (C2 * F2) -> C3 * F1 * F2 where C3 = C1 * C2
            this.parameter1 is Multiplication && this.parameter1.parameter1 is Constant
            && this.parameter2 is Multiplication && this.parameter2.parameter1 is Constant                             ->
                (this.parameter1.parameter1 * this.parameter2.parameter1) * this.parameter1.parameter2.simplify() * this.parameter2.parameter2.simplify()
            // (C1 * F1) * (F2 * C2) -> C3 * F1 * F2 where C3 = C1 * C2
            this.parameter1 is Multiplication && this.parameter1.parameter1 is Constant
            && this.parameter2 is Multiplication && this.parameter2.parameter2 is Constant                             ->
                (this.parameter1.parameter1 * this.parameter2.parameter2) * this.parameter1.parameter2.simplify() * this.parameter2.parameter1.simplify()
            // (F1 * C1) * (C2 * F2) -> C3 * F1 * F2 where C3 = C1 * C2
            this.parameter1 is Multiplication && this.parameter1.parameter2 is Constant
            && this.parameter2 is Multiplication && this.parameter2.parameter1 is Constant                             ->
                (this.parameter1.parameter2 * this.parameter2.parameter1) * this.parameter1.parameter1.simplify() * this.parameter2.parameter2.simplify()
            // (F1 * C1) * (F2 * C2) -> C3 * F1 * F2 where C3 = C1 * C2
            this.parameter1 is Multiplication && this.parameter1.parameter2 is Constant
            && this.parameter2 is Multiplication && this.parameter2.parameter2 is Constant                             ->
                (this.parameter1.parameter2 * this.parameter2.parameter2) * this.parameter1.parameter1.simplify() * this.parameter2.parameter1.simplify()
            // (F1 / F2) * (F3 / F4) -> (F1 * F3) / (F2 * F4)
            this.parameter1 is Division && this.parameter2 is Division                                                 ->
                (this.parameter1.parameter1.simplify() * this.parameter2.parameter1.simplify()) /
                (this.parameter1.parameter2.simplify() * this.parameter2.parameter2.simplify())
            // (F1 / F2) * F3 -> (F1 * F3) / F2
            this.parameter1 is Division                                                                                ->
                (this.parameter1.parameter1.simplify() * this.parameter2.simplify()) / this.parameter1.parameter2.simplify()
            // F1 * (F2 / F3) -> (F1 * F2) / F3
            this.parameter2 is Division                                                                                ->
                (this.parameter1.simplify() * this.parameter2.parameter1.simplify()) / this.parameter2.parameter2.simplify()
            // TODO other cases
            else                                                                                                       ->
                this.parameter2.simplify() * this.parameter1.simplify()
        }

    /**
     * Compute derivative on given variable
     */
    override fun derivative(variable: Variable): MathFunction<*> =
        this.parameter1.derivative(variable) * this.parameter2 +
        this.parameter1 * this.parameter2.derivative(variable)

    override fun toString(): String =
        this.classicString("*") { mf -> !mf.simpleType() && mf !is Multiplication }
}