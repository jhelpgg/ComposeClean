package fr.jhelp.compose.math.formal

import java.util.TreeSet

/**
 * Generic mathematical function with two parameters
 *
 * @param orderImportant Indicates if first follow by second is different from second follow by first.
 * By example `X - Y` is different of `Y - X`. Or not like `X + Y`and `Y + X`
 */
abstract class BinaryOperator<BO : BinaryOperator<BO>>
internal constructor(val parameter1: MathFunction<*>, val parameter2: MathFunction<*>,
                     private val orderImportant: Boolean) :
    MathFunction<BO>()
{
    /**
     * Create specific type new instance with given parameters
     */
    protected abstract fun newInstance(parameter1: MathFunction<*>,
                                       parameter2: MathFunction<*>): BO

    /**
     * Do an action on this function, if its match the given class or create same operation type with try do action on parameters
     */
    @Suppress("UNCHECKED_CAST")
    final override fun <M : MathFunction<M>> applyDeep(clazz: Class<M>,
                                                       action: (M) -> MathFunction<*>): MathFunction<*> =
        if (clazz == this.javaClass) action(this as M)
        else this.newInstance(this.parameter1.applyDeep(clazz, action),
                              this.parameter2.applyDeep(clazz, action))

    /**
     * Indicates if given function equals.
     * The given function is guarantee to have the same type
     */
    final override fun equalsIntern(mathFunction: BO): Boolean =
        (this.parameter1 == mathFunction.parameter1 && this.parameter2 == mathFunction.parameter2) ||
        (!this.orderImportant && this.parameter1 == mathFunction.parameter2 && this.parameter2 == mathFunction.parameter1)

    final override fun hash(): Int =
        this.parameter1.hashCode() + 31 * this.parameter2.hashCode()

    /**
     * Collect variables
     */
    final override fun collectVariables(collector: TreeSet<Variable>)
    {
        this.parameter1.collectVariables(collector)
        this.parameter2.collectVariables(collector)
    }

    /**
     * Classic binary operation String representation :
     * ````
     * P1 <Symbol> P2
     * ````
     * @param addParenthesis : Condition on parameter to add parenthesis around it or not
     */
    protected fun classicString(symbol: String,
                                addParenthesis: (MathFunction<*>) -> Boolean = { mf -> !mf.simpleType() }): String
    {
        val stringBuilder = StringBuilder()
        val addParenthesis1 = addParenthesis(this.parameter1)
        val addParenthesis2 = addParenthesis(this.parameter2)

        if (addParenthesis1)
        {
            stringBuilder.append('(')
        }

        stringBuilder.append(this.parameter1)

        if (addParenthesis1)
        {
            stringBuilder.append(')')
        }

        stringBuilder.append(' ')
        stringBuilder.append(symbol)
        stringBuilder.append(' ')

        if (addParenthesis2)
        {
            stringBuilder.append('(')
        }

        stringBuilder.append(this.parameter2)

        if (addParenthesis2)
        {
            stringBuilder.append(')')
        }

        return stringBuilder.toString()
    }

    /**
     * Replace a variable by given function
     */
    final override fun replace(variable: Variable, mathFunction: MathFunction<*>): MathFunction<*> =
        this.newInstance(this.parameter1.replace(variable, mathFunction),
                         this.parameter2.replace(variable, mathFunction))
}