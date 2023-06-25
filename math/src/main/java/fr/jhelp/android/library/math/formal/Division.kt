package fr.jhelp.android.library.math.formal

/**
 * Division on two functions
 */
class Division(parameter1: MathFunction<*>, parameter2: MathFunction<*>) :
        BinaryOperator<Division>(parameter1, parameter2, true)
{
    override fun newInstance(parameter1: MathFunction<*>, parameter2: MathFunction<*>): Division =
        parameter1 / parameter2

    /**
     * One step in simplification
     */
    override fun simple(): MathFunction<*> =
        when
        {
            // F / 0 -> INVALID
            this.parameter1 == INVALID || this.parameter2 == INVALID || this.parameter2 == ZERO                        ->
                INVALID
            // 0 / F -> 0
            this.parameter1 == ZERO                                                                                    ->
                ZERO
            // F / 1 -> F
            this.parameter2 == ONE                                                                                     ->
                this.parameter1.simplify()
            // F / -1 -> -F
            this.parameter2 == MINUS_ONE                                                                               ->
                -this.parameter1.simplify()
            // C1 / C2 -> C3 where C3 = C1/C2
            this.parameter1 is Constant && this.parameter2 is Constant                                                 ->
                this.parameter1 / this.parameter2
            // C1 / (C2 * F) -> C3 / F where C3=C1/C2
            this.parameter1 is Constant && this.parameter2 is Multiplication && this.parameter2.parameter1 is Constant ->
                (this.parameter1 / this.parameter2.parameter1) / this.parameter2.parameter2.simplify()
            // C1 / (F * C2) -> C3 / F where C3=C1/C2
            this.parameter1 is Constant && this.parameter2 is Multiplication && this.parameter2.parameter2 is Constant ->
                (this.parameter1 / this.parameter2.parameter2) / this.parameter2.parameter1.simplify()
            // (C1 * F) / C2 -> C3 * F where C3=C1/C2
            this.parameter1 is Multiplication && this.parameter1.parameter1 is Constant && this.parameter2 is Constant ->
                (this.parameter1.parameter1 / this.parameter2) * this.parameter1.parameter2.simplify()
            // (F * C1) / C2 -> C3 * F where C3=C1/C2
            this.parameter1 is Multiplication && this.parameter1.parameter2 is Constant && this.parameter2 is Constant ->
                (this.parameter1.parameter2 / this.parameter2) * this.parameter1.parameter1.simplify()
            // (C1 * F1) / (C2 * F2) -> C3 * (F1 / F2) where C3 = C1/C2
            this.parameter1 is Multiplication && this.parameter1.parameter1 is Constant
            && this.parameter2 is Multiplication && this.parameter2.parameter1 is Constant                             ->
                (this.parameter1.parameter1 / this.parameter2.parameter1) * (this.parameter1.parameter2.simplify() / this.parameter2.parameter2.simplify())
            // (C1 * F1) / (F2 * C2) -> C3 * (F1 / F2) where C3 = C1/C2
            this.parameter1 is Multiplication && this.parameter1.parameter1 is Constant
            && this.parameter2 is Multiplication && this.parameter2.parameter2 is Constant                             ->
                (this.parameter1.parameter1 / this.parameter2.parameter2) * (this.parameter1.parameter2.simplify() / this.parameter2.parameter1.simplify())
            // (F1 * C1) / (C2 * F2) -> C3 * (F1 / F2) where C3 = C1/C2
            this.parameter1 is Multiplication && this.parameter1.parameter2 is Constant
            && this.parameter2 is Multiplication && this.parameter2.parameter1 is Constant                             ->
                (this.parameter1.parameter2 / this.parameter2.parameter1) * (this.parameter1.parameter1.simplify() / this.parameter2.parameter2.simplify())
            // (F1 * C1) / (F2 * C2) -> C3 * (F1 / F2) where C3 = C1/C2
            this.parameter1 is Multiplication && this.parameter1.parameter2 is Constant
            && this.parameter2 is Multiplication && this.parameter2.parameter2 is Constant                             ->
                (this.parameter1.parameter2 / this.parameter2.parameter2) * (this.parameter1.parameter1.simplify() / this.parameter2.parameter1.simplify())
            // C / F -> C / F
            this.parameter1 is Constant                                                                                ->
                this.parameter1 / this.parameter2.simplify()
            // F / C1 -> C2 * F where C2 = 1/C1
            this.parameter2 is Constant                                                                                ->
                this.parameter2.invert() * this.parameter1.simplify()
            // (-F1) / (-F2) -> F1 / F2
            this.parameter1 is UnaryMinus && this.parameter2 is UnaryMinus                                             ->
                this.parameter1.parameter.simplify() / this.parameter2.parameter.simplify()
            // (-F1) / F2 -> -(F1 / F2)
            this.parameter1 is UnaryMinus                                                                              ->
                -(this.parameter1.parameter.simplify() / this.parameter2.simplify())
            // F1 / (-F2) -> -(F1 / F2)
            this.parameter2 is UnaryMinus                                                                              ->
                -(this.parameter1.simplify() / this.parameter2.parameter.simplify())
            // F / F -> 1
            this.parameter1 == this.parameter2                                                                         ->
                ONE
            // TODO other cases
            else                                                                                                       ->
                this.parameter1.simplify() / this.parameter2.simplify()
        }

    /**
     * Compute derivative on given variable
     */
    override fun derivative(variable: Variable): MathFunction<*> =
        (this.parameter1.derivative(variable) * this.parameter2 -
         this.parameter1 * this.parameter2.derivative(variable)) /
        (this.parameter2 * this.parameter2)

    override fun toString(): String =
        this.classicString("/")
}