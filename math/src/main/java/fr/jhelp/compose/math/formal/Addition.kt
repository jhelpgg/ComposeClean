package fr.jhelp.compose.math.formal

/**
 * Addition between two functions
 */
class Addition(parameter1: MathFunction<*>, parameter2: MathFunction<*>) :
    BinaryOperator<Addition>(parameter1, parameter2, false)
{
    override fun newInstance(parameter1: MathFunction<*>, parameter2: MathFunction<*>): Addition =
        parameter1 + parameter2

    /**
     * One step in simplification
     */
    override fun simple(): MathFunction<*> =
        when
        {
            this.parameter1 == INVALID || this.parameter2 == INVALID                                                                                                        ->
                INVALID
            // 0 + F -> F
            this.parameter1 == ZERO                                                                                                                                         ->
                this.parameter2.simplify()
            // F + 0 -> F
            this.parameter2 == ZERO                                                                                                                                         ->
                this.parameter1.simplify()
            // C1 + C2 -> C3 where C3 = C1 + C2
            this.parameter1 is Constant && this.parameter2 is Constant                                                                                                      ->
                this.parameter1 + this.parameter2
            // C + F -> C + F
            this.parameter1 is Constant                                                                                                                                     ->
                this.parameter1 + this.parameter2.simplify()
            // F + C -> C + F
            this.parameter2 is Constant                                                                                                                                     ->
                this.parameter2 + this.parameter1.simplify()
            // -F1 + -F2 -> -(F1 + F2)
            this.parameter1 is UnaryMinus && this.parameter2 is UnaryMinus                                                                                                  ->
                -(this.parameter1.parameter.simplify() + this.parameter2.parameter.simplify())
            // -F1 + F2 -> F2 - F1
            this.parameter1 is UnaryMinus                                                                                                                                   ->
                this.parameter2.simplify() - this.parameter1.parameter.simplify()
            // F1 + -F2 -> F1 - F2
            this.parameter2 is UnaryMinus                                                                                                                                   ->
                this.parameter1.simplify() - this.parameter2.parameter.simplify()
            // F + F -> 2 * F
            this.parameter1 == this.parameter2                                                                                                                              ->
                2.0 * this.parameter1.simplify()
            // (C * F) + F -> (C + 1) * F
            this.parameter1 is Multiplication && this.parameter1.parameter1 is Constant && this.parameter1.parameter2 == this.parameter2                                    ->
                (this.parameter1.parameter1 + 1) * this.parameter2.simplify()
            // (F * C) + F -> (C + 1) * F
            this.parameter1 is Multiplication && this.parameter1.parameter2 is Constant && this.parameter1.parameter1 == this.parameter2                                    ->
                (this.parameter1.parameter2 + 1) * this.parameter2.simplify()
            // F + (C * F) -> (C + 1) * F
            this.parameter2 is Multiplication && this.parameter2.parameter1 is Constant && this.parameter1 == this.parameter2.parameter2                                    ->
                (this.parameter2.parameter1 + 1) * this.parameter1.simplify()
            // F + (F * C) -> (C + 1) * F
            this.parameter2 is Multiplication && this.parameter2.parameter2 is Constant && this.parameter1 == this.parameter2.parameter1                                    ->
                (this.parameter2.parameter2 + 1) * this.parameter1.simplify()
            // (C1 * F) + (C2 * F) -> (C1 + C2) * F
            this.parameter1 is Multiplication && this.parameter1.parameter1 is Constant
            && this.parameter2 is Multiplication && this.parameter2.parameter1 is Constant
            && this.parameter1.parameter2 == this.parameter2.parameter2                                                                                                     ->
                (this.parameter1.parameter1 + this.parameter2.parameter1) * this.parameter1.parameter2.simplify()
            // (C1 * F) + (F * C2) -> (C1 + C2) * F
            this.parameter1 is Multiplication && this.parameter1.parameter1 is Constant
            && this.parameter2 is Multiplication && this.parameter2.parameter2 is Constant
            && this.parameter1.parameter2 == this.parameter2.parameter1                                                                                                     ->
                (this.parameter1.parameter1 + this.parameter2.parameter2) * this.parameter1.parameter2.simplify()
            // (F * C1) + (C2 * F) -> (C1 + C2) * F
            this.parameter1 is Multiplication && this.parameter1.parameter2 is Constant
            && this.parameter2 is Multiplication && this.parameter2.parameter1 is Constant
            && this.parameter1.parameter1 == this.parameter2.parameter2                                                                                                     ->
                (this.parameter1.parameter2 + this.parameter2.parameter1) * this.parameter1.parameter1.simplify()
            // (F * C1) + (F * C2) -> (C1 + C2) * F
            this.parameter1 is Multiplication && this.parameter1.parameter2 is Constant
            && this.parameter2 is Multiplication && this.parameter2.parameter2 is Constant
            && this.parameter1.parameter1 == this.parameter2.parameter1                                                                                                     ->
                (this.parameter1.parameter2 + this.parameter2.parameter2) * this.parameter1.parameter1.simplify()
            // cos(F) * cos(F) + sin(F) * sin(F) -> 1
            this.parameter1 is Multiplication && this.parameter1.parameter1 is Cosinus && this.parameter1.parameter2 is Cosinus && this.parameter1.parameter1.parameter == this.parameter1.parameter2.parameter
            && this.parameter2 is Multiplication && this.parameter2.parameter1 is Sinus && this.parameter2.parameter2 is Sinus && this.parameter2.parameter1.parameter == this.parameter1.parameter2.parameter
            && this.parameter1.parameter1.parameter == this.parameter2.parameter2.parameter                                                                                 ->
                ONE
            // sin(F) * sin(F) + cos(F) * cos(F)  -> 1
            this.parameter1 is Multiplication && this.parameter1.parameter1 is Sinus && this.parameter1.parameter2 is Sinus && this.parameter1.parameter1.parameter == this.parameter1.parameter2.parameter
            && this.parameter2 is Multiplication && this.parameter2.parameter1 is Cosinus && this.parameter2.parameter2 is Cosinus && this.parameter2.parameter1.parameter == this.parameter1.parameter2.parameter
            && this.parameter1.parameter1.parameter == this.parameter2.parameter2.parameter                                                                                 ->
                ONE
            // cos(F1) * cos(F2) + sin(F1) * sin(F2) -> cos(F1-F2)
            this.parameter1 is Multiplication && this.parameter1.parameter1 is Cosinus && this.parameter1.parameter2 is Cosinus
            && this.parameter2 is Multiplication && this.parameter2.parameter1 is Sinus && this.parameter2.parameter2 is Sinus
            && this.parameter1.parameter1.parameter == this.parameter2.parameter1.parameter && this.parameter1.parameter2.parameter == this.parameter2.parameter2.parameter ->
                cos(this.parameter1.parameter1.parameter.simplify() - this.parameter1.parameter2.parameter.simplify())
            // cos(F1) * cos(F2) + sin(F2) * sin(F1) -> cos(F1-F2)
            this.parameter1 is Multiplication && this.parameter1.parameter1 is Cosinus && this.parameter1.parameter2 is Cosinus
            && this.parameter2 is Multiplication && this.parameter2.parameter1 is Sinus && this.parameter2.parameter2 is Sinus
            && this.parameter1.parameter1.parameter == this.parameter2.parameter2.parameter && this.parameter1.parameter2.parameter == this.parameter2.parameter1.parameter ->
                cos(this.parameter1.parameter1.parameter.simplify() - this.parameter1.parameter2.parameter.simplify())
            //  sin(F1) * sin(F2)  + cos(F1) * cos(F2) -> cos(F1-F2)
            this.parameter1 is Multiplication && this.parameter1.parameter1 is Sinus && this.parameter1.parameter2 is Sinus
            && this.parameter2 is Multiplication && this.parameter2.parameter1 is Cosinus && this.parameter2.parameter2 is Cosinus
            && this.parameter1.parameter1.parameter == this.parameter2.parameter1.parameter && this.parameter1.parameter2.parameter == this.parameter2.parameter2.parameter ->
                cos(this.parameter1.parameter1.parameter.simplify() - this.parameter1.parameter2.parameter.simplify())
            //  sin(F2) * sin(F1) + cos(F1) * cos(F2) -> cos(F1-F2)
            this.parameter1 is Multiplication && this.parameter1.parameter1 is Sinus && this.parameter1.parameter2 is Sinus
            && this.parameter2 is Multiplication && this.parameter2.parameter1 is Cosinus && this.parameter2.parameter2 is Cosinus
            && this.parameter1.parameter1.parameter == this.parameter2.parameter2.parameter && this.parameter1.parameter2.parameter == this.parameter2.parameter1.parameter ->
                cos(this.parameter1.parameter1.parameter.simplify() - this.parameter1.parameter2.parameter.simplify())
            // cos(F1) * sin(F2) + sin(F1) * cos(F2) -> sin(F1+F2)
            this.parameter1 is Multiplication && this.parameter1.parameter1 is Cosinus && this.parameter1.parameter2 is Sinus
            && this.parameter2 is Multiplication && this.parameter2.parameter1 is Sinus && this.parameter2.parameter2 is Cosinus
            && this.parameter1.parameter1.parameter == this.parameter2.parameter1.parameter && this.parameter1.parameter2.parameter == this.parameter2.parameter2.parameter ->
                sin(this.parameter1.parameter1.parameter.simplify() + this.parameter1.parameter2.parameter.simplify())
            // cos(F1) * sin(F2) +  cos(F2) * sin(F1) -> sin(F1+F2)
            this.parameter1 is Multiplication && this.parameter1.parameter1 is Cosinus && this.parameter1.parameter2 is Sinus
            && this.parameter2 is Multiplication && this.parameter2.parameter1 is Cosinus && this.parameter2.parameter2 is Sinus
            && this.parameter1.parameter1.parameter == this.parameter2.parameter2.parameter && this.parameter1.parameter2.parameter == this.parameter2.parameter1.parameter ->
                sin(this.parameter1.parameter1.parameter.simplify() + this.parameter1.parameter2.parameter.simplify())
            // sin(F2) * cos(F1) + sin(F1) * cos(F2) -> sin(F1+F2)
            this.parameter1 is Multiplication && this.parameter1.parameter1 is Sinus && this.parameter1.parameter2 is Cosinus
            && this.parameter2 is Multiplication && this.parameter2.parameter1 is Sinus && this.parameter2.parameter2 is Cosinus
            && this.parameter1.parameter1.parameter == this.parameter2.parameter2.parameter && this.parameter1.parameter2.parameter == this.parameter2.parameter1.parameter ->
                sin(this.parameter1.parameter1.parameter.simplify() + this.parameter1.parameter2.parameter.simplify())
            // sin(F2) * cos(F1)  +  cos(F2) * sin(F1) -> sin(F1+F2)
            this.parameter1 is Multiplication && this.parameter1.parameter1 is Sinus && this.parameter1.parameter2 is Cosinus
            && this.parameter2 is Multiplication && this.parameter2.parameter1 is Cosinus && this.parameter2.parameter2 is Sinus
            && this.parameter1.parameter1.parameter == this.parameter2.parameter1.parameter && this.parameter1.parameter2.parameter == this.parameter2.parameter2.parameter ->
                sin(this.parameter1.parameter1.parameter.simplify() + this.parameter1.parameter2.parameter.simplify())
            // (F1 - F2) + (F3 - F4) -> (F1 + F3) - (F2 + F4)
            this.parameter1 is Subtraction && this.parameter2 is Subtraction                                                                                                ->
                (this.parameter1.parameter1.simplify() + this.parameter2.parameter1.simplify()) - (this.parameter1.parameter2.simplify() + this.parameter2.parameter2.simplify())
            // (F1 - F2) + F3 -> F1 + (F3 - F2)
            this.parameter1 is Subtraction ->
                this.parameter1.parameter1.simplify() + (this.parameter2 - this.parameter1.parameter2).simplify()
            // F1 + (F2 - F3) -> (F1 - F3) + F2
            this.parameter2 is Subtraction ->
                (this.parameter1 - this.parameter2.parameter2).simplify() + this.parameter2.parameter1.simplify()

            // TODO other cases
            else                                                                                                                                                            ->
                this.parameter2.simplify() + this.parameter1.simplify()
        }

    /**
     * Compute derivative on given variable
     */
    override fun derivative(variable: Variable): MathFunction<*> =
        this.parameter1.derivative(variable) + this.parameter2.derivative(variable)

    override fun toString(): String =
        this.classicString("+") { mf -> mf is Subtraction }
}