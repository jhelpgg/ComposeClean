package fr.jhelp.compose.collection

import kotlin.math.max
import kotlin.math.min

/**
 * List of `Int`
 * More efficient than : `ArrayList<Int>`
 */
class IntList(initialCapacity: Int = 1024)
{
    /** Number of elements in list */
    var size: Int = 0
        private set

    /** Indicates if list is empty */
    val empty: Boolean get() = this.size == 0

    /** Indicates if list is not empty */
    val notEmpty: Boolean get() = this.size != 0

    private var list = IntArray(max(128, initialCapacity))

    /**
     * Obtain an `Int` from the list at given index
     *
     * Allow to use `[]` syntax
     *
     * ```kotlin
     * val value = intList[6]
     * ```
     */
    @Throws(IndexOutOfBoundsException::class)
    operator fun get(index: Int): Int
    {
        this.checkIndex(index)
        return this.list[index]
    }

    /**
     * Change an element to the list at given index
     *
     * Allow to use `[]` syntax
     *
     * ```kotlin
     * intList[42] = 73
     * ```
     */
    @Throws(IndexOutOfBoundsException::class)
    operator fun set(index: Int, value: Int)
    {
        this.checkIndex(index)
        this.list[index] = value
    }

    /**
     * Insert a value at given index
     *
     * If index is lower or equals to 0  value will be insert at the first place
     *
     * If index is uppper or equals to the list size, element will be add to the end of the list
     */
    fun insert(index: Int, value: Int): IntList
    {
        this.expandIfNeed(1)

        when
        {
            this.empty         -> this.list[0] = value

            index <= 0         ->
            {
                System.arraycopy(this.list, 0, this.list, 1, this.size)
                this.list[0] = value
            }

            index >= this.size -> this.list[this.size] = value

            else               ->
            {
                System.arraycopy(this.list, index, this.list, index + 1, this.size - index)
                this.list[index] = value
            }
        }

        this.size++
        return this
    }

    /**
     * Add one element to the end of list and return the list to able to chain
     *
     * Allows `+` syntax
     *
     * ```kotlin
     * intList + 9
     * (intList + 10) + 5
     * ```
     */
    operator fun plus(value: Int): IntList
    {
        this.expandIfNeed(1)
        this.list[this.size] = value
        this.size++
        return this
    }

    /**
     * Add one int list to the end of list and return the list to able to chain
     *
     * Allows `+` syntax
     *
     * ```kotlin
     * intList + intList2
     * (intList + intList3) + intList5
     * ```
     */
    operator fun plus(intList: IntList): IntList
    {
        this.expandIfNeed(intList.size)
        System.arraycopy(intList.list, 0, this.list, this.size, intList.size)
        this.size += intList.size
        return this
    }

    /**
     * Add one int array to the end of list and return the list to able to chain
     *
     * Allows `+` syntax
     *
     * ```kotlin
     * intList + intArray
     * (intList + intArray2) + intArray3
     * ```
     */
    operator fun plus(intArray: IntArray): IntList
    {
        this.expandIfNeed(intArray.size)
        System.arraycopy(intArray, 0, this.list, this.size, intArray.size)
        this.size += intArray.size
        return this
    }

    /**
     * Add one int array to the end of list
     *
     * Allows `+=` syntax
     *
     * ```kotlin
     * intList += intArray
     * ```
     */
    operator fun plusAssign(intArray: IntArray)
    {
        this.expandIfNeed(intArray.size)
        System.arraycopy(intArray, 0, this.list, this.size, intArray.size)
        this.size += intArray.size
    }

    /**
     * Add one int array to the end of list and return the list to able to chain
     *
     * Allows `+` syntax
     *
     * ```kotlin
     * intList + arrayInt
     * (intList + arrayInt2) + arrayInt3
     * ```
     */
    operator fun plus(intArray: Array<Int>): IntList
    {
        this.expandIfNeed(intArray.size)
        System.arraycopy(intArray, 0, this.list, this.size, intArray.size)
        this.size += intArray.size
        return this
    }

    /**
     * Add one int array to the end of list
     *
     * Allows `+=` syntax
     *
     * ```kotlin
     * intList += arrayInt
     * ```
     */
    operator fun plusAssign(intArray: Array<Int>)
    {
        this.expandIfNeed(intArray.size)
        System.arraycopy(intArray, 0, this.list, this.size, intArray.size)
        this.size += intArray.size
    }

    /**
     * Add one int list to the end of list and return the list to able to chain
     *
     * Allows `+` syntax
     *
     * ```kotlin
     * intList + intList
     * (intList + intList2) + intList3
     * ```
     */
    operator fun plus(listInt: List<Int>): IntList
    {
        this.expandIfNeed(listInt.size)
        System.arraycopy(listInt.toTypedArray(), 0, this.list, this.size, listInt.size)
        this.size += listInt.size
        return this
    }

    /**
     * Add one int list to the end of list
     *
     * Allows `+=` syntax
     *
     * ```kotlin
     * intList += intList
     * ```
     */
    operator fun plusAssign(listInt: List<Int>)
    {
        this.expandIfNeed(listInt.size)
        System.arraycopy(listInt.toTypedArray(), 0, this.list, this.size, listInt.size)
        this.size += listInt.size
    }

    /**
     * Add one int collection to the end of list and return the list to able to chain
     *
     * Allows `+` syntax
     *
     * ```kotlin
     * intList + intCollection
     * (intList + intCollection2) + intCollection3
     * ```
     */
    operator fun plus(collection: Collection<Int>): IntList
    {
        this.expandIfNeed(collection.size)

        for (value in collection)
        {
            this.list[this.size] = value
            this.size++
        }
        return this
    }

    /**
     * Add one int collection to the end of list
     *
     * Allows `+=` syntax
     *
     * ```kotlin
     * intList += intCollection
     * ```
     */
    operator fun plusAssign(collection: Collection<Int>)
    {
        this.expandIfNeed(collection.size)

        for (value in collection)
        {
            this.list[this.size] = value
            this.size++
        }
    }

    /**
     * Returns the index of first element at given offset or after that equals to given one
     *
     * @param value Value searched
     * @param startIndex Index to start the search. 0 by default to start at the beginning
     * @return Value index OR -1 if not found
     */
    fun firstIndexOf(value: Int, startIndex: Int = 0): Int
    {
        for (index in max(0, startIndex) until this.size)
        {
            if (this.list[index] == value)
            {
                return index
            }
        }

        return -1
    }

    /**
     * Returns the index of first element at given offset or after that match a condition
     *
     * @param condition Condition that value must match
     * @param startIndex Index to start the search. 0 by default to start at the beginning
     * @return Value index OR -1 if not found
     */
    fun firstIndexOf(condition: (value: Int) -> Boolean, startIndex: Int = 0): Int
    {
        for (index in max(0, startIndex) until this.size)
        {
            if (condition(this.list[index]))
            {
                return index
            }
        }

        return -1
    }

    /**
     * Returns the index of first element at given offset or before that equals to given one
     *
     * @param value Value searched
     * @param startIndex Index to start the search. Last index by default to start at the end
     * @return Value index OR -1 if not found
     */
    fun lastIndexOf(value: Int, startIndex: Int = this.size - 1): Int
    {
        for (index in max(0, min(this.size - 1, startIndex)) downTo 0)
        {
            if (this.list[index] == value)
            {
                return index
            }
        }

        return -1
    }

    /**
     * Returns the index of first element at given offset or before that match a condition
     *
     * @param condition Condition that value must match
     * @param startIndex Index to start the search. Last index by default to start at the end
     * @return Value index OR -1 if not found
     */
    fun lastIndexOf(condition: (value: Int) -> Boolean, startIndex: Int = this.size - 1): Int
    {
        for (index in max(0, min(this.size - 1, startIndex)) downTo 0)
        {
            if (condition(this.list[index]))
            {
                return index
            }
        }

        return -1
    }

    /**
     * Indicates if a value is in the array
     */
    operator fun contains(value: Int): Boolean =
        this.firstIndexOf(value) >= 0

    /**
     * Remove an element by its index
     */
    @Throws(IndexOutOfBoundsException::class)
    fun remove(index: Int): Int
    {
        this.checkIndex(index)
        val value = this.list[index]

        if (index < this.size - 1)
        {
            System.arraycopy(this.list, index + 1, this.list, index, this.size - 1 - index)
        }

        this.size--
        return value
    }

    /**
     * Remove all elements of the list
     */
    fun clear()
    {
        this.size = 0
    }

    /**
     * Do an action for each elements of the list
     *
     * Action will received value
     */
    fun <R> forEach(action: (value: Int) -> R)
    {
        for (index in 0 until this.size)
        {
            action(this.list[index])
        }
    }

    /**
     * Do an action for each elements of the list
     *
     * Action will received value's index and value
     */
    fun <R> forEachIndexed(action: (index: Int, value: Int) -> R)
    {
        for (index in 0 until this.size)
        {
            action(index, this.list[index])
        }
    }

    /**
     * Do an action for each elements of the list but element are delivered in reversed order
     */
    fun <R> forEachReversed(action: (value: Int) -> R)
    {
        for (index in this.size - 1 downTo 0)
        {
            action(this.list[index])
        }
    }

    @Throws(IndexOutOfBoundsException::class)
    private fun checkIndex(index: Int)
    {
        if (index < 0 || index >= this.size)
        {
            throw IndexOutOfBoundsException("Index must be in [0, ${this.size}[, not $index")
        }
    }

    private fun expandIfNeed(numberElementToAdd: Int)
    {
        if (this.size + numberElementToAdd < this.list.size)
        {
            return
        }

        var newCapacity = this.list.size + numberElementToAdd
        newCapacity += newCapacity shr 3
        val newList = IntArray(newCapacity)
        System.arraycopy(this.list, 0, newList, 0, this.size)
        this.list = newList
    }
}
