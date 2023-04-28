package fr.jhelp.compose.math.formal

import java.util.TreeSet
import java.util.regex.Pattern

/**
 * A variable
 */
class Variable(name: String) : MathFunction<Variable>()
{
    companion object
    {
        val VARIABLE_NAME_REGEX: Pattern = Pattern.compile("[a-zA-Z][a-zA-Z0-9_]*")
    }

    val name: String

    init
    {
        if (!Variable.VARIABLE_NAME_REGEX.matcher(name).matches())
        {
            throw IllegalArgumentException(
                "'$name' is not acceptable variable name. Variable valid name must start with letter follow by zero or more letter or digit or underscore")
        }

        this.name = name
    }

    /**
     * One step in simplification
     */
    override fun simple(): MathFunction<*> = this

    override fun equalsIntern(mathFunction: Variable) =
        this.name == mathFunction.name

    override fun hash() =
        this.name.hashCode()

    /**
     * Compute derivative on given variable
     */
    override fun derivative(variable: Variable): MathFunction<*> =
        if (this.name == variable.name) ONE
        else ZERO

    /**
     * Collect variables used by the function
     */
    override fun collectVariables(collector: TreeSet<Variable>)
    {
        collector.add(this)
    }

    override fun toString(): String =
        this.name

    /**
     * Replace a variable by a function
     */
    override fun replace(variable: Variable, mathFunction: MathFunction<*>): MathFunction<*> =
        if (this.name == variable.name) mathFunction
        else this
}