package fr.jhelp.android.library.math.formal

import fr.jhelp.android.library.math.extensions.compareAlphabet
import java.util.Stack
import java.util.TreeSet

/**
 * Generic mathematical function
 */
abstract class MathFunction<MF : MathFunction<MF>> internal constructor() :
        Comparable<MathFunction<*>>
{
    private fun collectAddition(): MutableList<MathFunction<*>>
    {
        if (this !is Addition)
        {
            return ArrayList()
        }

        val list = ArrayList<MathFunction<*>>()
        val stack = Stack<Addition>()
        stack.push(this)
        var current: Addition

        while (stack.isNotEmpty())
        {
            current = stack.pop()

            if (current.parameter1 is Addition)
            {
                stack.push(current.parameter1 as Addition)
            }
            else
            {
                list.add(current.parameter1)
            }

            if (current.parameter2 is Addition)
            {
                stack.push(current.parameter2 as Addition)
            }
            else
            {
                list.add(current.parameter2)
            }
        }

        list.sort()
        return list
    }

    private fun collectMultiplication(): MutableList<MathFunction<*>>
    {
        if (this !is Multiplication)
        {
            return ArrayList()
        }

        val list = ArrayList<MathFunction<*>>()
        val stack = Stack<Multiplication>()
        stack.push(this)
        var current: Multiplication

        while (stack.isNotEmpty())
        {
            current = stack.pop()

            if (current.parameter1 is Multiplication)
            {
                stack.push(current.parameter1 as Multiplication)
            }
            else
            {
                list.add(current.parameter1)
            }

            if (current.parameter2 is Multiplication)
            {
                stack.push(current.parameter2 as Multiplication)
            }
            else
            {
                list.add(current.parameter2)
            }
        }

        list.sort()
        return list
    }

    /**
     * One step in simplification
     */
    protected abstract fun simple(): MathFunction<*>

    /**
     * Do an action on this function, if its match the given class or create same operation type with try do action on parameters
     */
    @Suppress("UNCHECKED_CAST")
    internal open fun <M : MathFunction<M>> applyDeep(clazz: Class<M>,
                                                      action: (M) -> MathFunction<*>): MathFunction<*> =
        if (clazz == this.javaClass) action(this as M)
        else this


    /**
     * Simplify one time
     */
    fun simplify(): MathFunction<*>
    {
        var simplified = this.simple()

        simplified = simplified.applyDeep(Subtraction::class.java)
        { subtraction ->
            var result: MathFunction<*> = subtraction
            val list1 = subtraction.parameter1.collectAddition()
            val list2 = subtraction.parameter2.collectAddition()

            if (list1.isNotEmpty())
            {
                if (list2.isNotEmpty())
                {
                    var someRemove = false
                    val iterator = list1.listIterator()
                    var current: MathFunction<*>

                    while (iterator.hasNext())
                    {
                        current = iterator.next()

                        if (current in list2)
                        {
                            someRemove = true
                            iterator.remove()
                            list2.remove(current)
                        }
                    }

                    if (someRemove)
                    {
                        result =
                            when
                            {
                                list1.isEmpty() && list2.isEmpty() -> ZERO
                                list1.isEmpty()                    -> -addition(list2)
                                list2.isEmpty()                    -> addition(list1)
                                else                               ->
                                    addition(list1) - addition(list2)
                            }
                    }
                }
                else if (subtraction.parameter2 in list1)
                {
                    list1.remove(subtraction.parameter2)
                    result = addition(list1)
                }
            }
            else if (list2.isNotEmpty() && subtraction.parameter1 in list2)
            {
                list2.remove(subtraction.parameter1)
                result = -addition(list2)
            }

            result
        }

        return simplified.order()
    }

    /**
     * Replace a variable by a function
     */
    open fun replace(variable: Variable, mathFunction: MathFunction<*>): MathFunction<*> = this

    /**
     * Replace a variable by a value
     */
    fun replace(variable: Variable, number: Number): MathFunction<*> =
        this.replace(variable, number.const)

    /**
     * Collect all used variables
     */
    open fun collectVariables(collector: TreeSet<Variable>): Unit = Unit

    /**
     * Indicates if given function equals.
     * The given function is guarantee to have the same type
     */
    protected abstract fun equalsIntern(mathFunction: MF): Boolean

    /**
     * Hash code
     */
    protected abstract fun hash(): Int

    /**
     * Compute derivative on given variable
     */
    abstract fun derivative(variable: Variable): MathFunction<*>

    @Suppress("UNCHECKED_CAST")
    final override fun equals(other: Any?): Boolean
    {
        if (this === other)
        {
            return true
        }

        if (null === other || other.javaClass != this.javaClass)
        {
            return false
        }

        return this.equalsIntern(other as MF)
    }

    final override fun hashCode(): Int
    {
        return this.javaClass.name.hashCode() + 31 * this.hash()
    }

    private fun order(): MathFunction<*>
    {
        var list = this.collectAddition()

        if (list.isNotEmpty())
        {
            val lastIndex = list.size - 1
            var mathFunction = list[lastIndex]

            for (index in lastIndex - 1 downTo 0)
            {
                mathFunction = list[index] + mathFunction
            }

            return mathFunction
        }

        list = this.collectMultiplication()

        if (list.isNotEmpty())
        {
            val lastIndex = list.size - 1
            var mathFunction = list[lastIndex]

            for (index in lastIndex - 1 downTo 0)
            {
                mathFunction = list[index] * mathFunction
            }

            return mathFunction
        }

        return this
    }

    /**
     * Simplify as much as actually can
     */
    fun simplifyMax(spy: (MathFunction<*>) -> Unit = {}): MathFunction<*>
    {
        spy(this)
        val alreadySeen = ArrayList<MathFunction<*>>()
        var simplified = this.simplify()
        spy(simplified)

        do
        {
            alreadySeen.add(simplified)
            simplified = simplified.simplify()
            spy(simplified)
        }
        while (simplified !in alreadySeen)

        return simplified
    }

    override fun compareTo(other: MathFunction<*>): Int =
        when
        {
            this is Constant && other is Constant                                                  ->
                this.compareTo(other)

            this is Constant                                                                       ->
                -1

            other is Constant                                                                      ->
                1

            this is Variable && other is Variable                                                  ->
                this.name.compareAlphabet(other.name)

            this is Variable                                                                       ->
                -1

            other is Variable                                                                      ->
                1

            this.javaClass == other.javaClass && this is UnaryOperator && other is UnaryOperator   ->
                this.parameter.compareTo(other.parameter)

            this.javaClass == other.javaClass && this is BinaryOperator && other is BinaryOperator ->
            {
                val comparision = this.parameter1.compareTo(other.parameter1)

                if (comparision != 0)
                {
                    comparision
                }
                else
                {
                    this.parameter2.compareTo(other.parameter2)
                }
            }

            else                                                                                   ->
                this.javaClass.name.compareAlphabet(other.javaClass.name)
        }
}