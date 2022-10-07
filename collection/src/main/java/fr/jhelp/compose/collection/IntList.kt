package fr.jhelp.compose.collection

import kotlin.math.max
import kotlin.math.min

class IntList(initialCapacity: Int = 1024)
{
    var size: Int = 0
        private set
    val empty: Boolean get() = this.size == 0
    val notEmpty: Boolean get() = this.size != 0

    private var list = IntArray(max(128, initialCapacity))

    @Throws(IndexOutOfBoundsException::class)
    operator fun get(index: Int): Int
    {
        this.checkIndex(index)
        return this.list[index]
    }

    @Throws(IndexOutOfBoundsException::class)
    operator fun set(index: Int, value: Int)
    {
        this.checkIndex(index)
        this.list[index] = value
    }

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

    operator fun plus(value: Int): IntList
    {
        this.expandIfNeed(1)
        this.list[this.size] = value
        this.size++
        return this
    }

    operator fun plusAssign(value: Int)
    {
        this.expandIfNeed(1)
        this.list[this.size] = value
        this.size++
    }

    operator fun plus(intList: IntList): IntList
    {
        this.expandIfNeed(intList.size)
        System.arraycopy(intList.list, 0, this.list, this.size, intList.size)
        this.size += intList.size
        return this
    }

    operator fun plusAssign(intList: IntList)
    {
        this.expandIfNeed(intList.size)
        System.arraycopy(intList.list, 0, this.list, this.size, intList.size)
        this.size += intList.size
    }

    operator fun plus(intArray: IntArray): IntList
    {
        this.expandIfNeed(intArray.size)
        System.arraycopy(intArray, 0, this.list, this.size, intArray.size)
        this.size += intArray.size
        return this
    }

    operator fun plusAssign(intArray: IntArray)
    {
        this.expandIfNeed(intArray.size)
        System.arraycopy(intArray, 0, this.list, this.size, intArray.size)
        this.size += intArray.size
    }

    operator fun plus(intArray: Array<Int>): IntList
    {
        this.expandIfNeed(intArray.size)
        System.arraycopy(intArray, 0, this.list, this.size, intArray.size)
        this.size += intArray.size
        return this
    }

    operator fun plusAssign(intArray: Array<Int>)
    {
        this.expandIfNeed(intArray.size)
        System.arraycopy(intArray, 0, this.list, this.size, intArray.size)
        this.size += intArray.size
    }

    operator fun plus(listInt: List<Int>): IntList
    {
        this.expandIfNeed(listInt.size)
        System.arraycopy(listInt, 0, this.list, this.size, listInt.size)
        this.size += listInt.size
        return this
    }

    operator fun plusAssign(listInt: List<Int>)
    {
        this.expandIfNeed(listInt.size)
        System.arraycopy(listInt, 0, this.list, this.size, listInt.size)
        this.size += listInt.size
    }

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

    operator fun plusAssign(collection: Collection<Int>)
    {
        this.expandIfNeed(collection.size)

        for (value in collection)
        {
            this.list[this.size] = value
            this.size++
        }
    }

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

    operator fun contains(value: Int): Boolean =
        this.firstIndexOf(value) >= 0

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

    fun clear()
    {
        this.size = 0
    }

    fun <R> forEach(action: (value: Int) -> R)
    {
        for (index in 0 until this.size)
        {
            action(this.list[index])
        }
    }

    fun <R> forEachIndexed(action: (index: Int, value: Int) -> R)
    {
        for (index in 0 until this.size)
        {
            action(index, this.list[index])
        }
    }

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
