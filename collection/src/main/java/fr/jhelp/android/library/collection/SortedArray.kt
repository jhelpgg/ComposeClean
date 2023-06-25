package fr.jhelp.android.library.collection

/**
 * Create a sorted array for comparable, order based on their "natural order"
 */
fun <C : Comparable<C>> sortedArray(unique: Boolean = false): SortedArray<C> =
    SortedArray<C>(NaturalComparator(), unique)

/**
 * Array of elements always sorted with the given comparator
 *
 * It can be creates in unique, mode, that is to say, two elements consider equals with given comparator can't be stored.
 * If try add an already present element in unique mode, the add is ignored
 * @property unique Indicates if we are in unique mode
 */
class SortedArray<T>(private val comparator: Comparator<T>, val unique: Boolean = false) :
        Iterable<T>
{
    private val array = ArrayList<T>()

    /** Array size */
    val size: Int get() = this.array.size

    /** Indicates if array is empty */
    val empty: Boolean get() = this.array.isEmpty()

    /** Indicates if array is not empty */
    val notEmpty: Boolean get() = this.array.isNotEmpty()

    /**
     * Add an element to the array
     *
     * @return `false` if array is in unique mode and element considered equals to one in the array
     */
    fun add(element: T): Boolean
    {
        val index = this.indexInsert(element)

        return when
        {
            index >= this.array.size                                                ->
                this.array.add(element)

            this.unique && this.comparator.compare(this.array[index], element) == 0 ->
                false

            else                                                                    ->
            {
                this.array.add(index, element)
                true
            }
        }
    }

    /**
     * Add all iterable elements
     */
    fun addAll(iterable: Iterable<T>)
    {
        for (element in iterable)
        {
            this.add(element)
        }
    }

    /**
     * Obtain element at given index
     */
    operator fun get(index: Int): T =
        this.array[index]

    /**
     * Indicates if an element present in the array
     */
    operator fun contains(element: T): Boolean =
        this.indexOf(element) >= 0

    /**
     * Indicates if all elements are in the array
     */
    fun containsAll(iterable: Iterable<T>): Boolean
    {
        for (element in iterable)
        {
            if (element !in this)
            {
                return false
            }
        }

        return true
    }

    /**
     * Element index or -1 if not found
     */
    fun indexOf(element: T): Int
    {
        val index = this.indexInsert(element)

        if (index < this.size && this.comparator.compare(this.array[index], element) == 0)
        {
            return index
        }

        return -1
    }

    /**
     * Remove an element
     *
     * @return `true`if effectively removed
     */
    fun remove(element: T): Boolean
    {
        val index = this.indexInsert(element)

        if (index < this.size && this.comparator.compare(this.array[index], element) == 0)
        {
            this.array.removeAt(index)
            return true
        }

        return false
    }

    /**
     * Remove element at given position
     */
    fun removeAt(index: Int): T =
        this.array.removeAt(index)

    /**
     * Remove all elements that match given condition
     */
    fun removeIf(condition: (T) -> Boolean)
    {
        val iterator = this.array.iterator()

        while (iterator.hasNext())
        {
            if (condition(iterator.next()))
            {
                iterator.remove()
            }
        }
    }

    /**
     * Clear the array
     */
    fun clear()
    {
        this.array.clear()
    }

    /**
     * Iterator over elements
     */
    override fun iterator(): Iterator<T> =
        this.array.iterator()

    private fun indexInsert(element: T): Int
    {
        if (this.array.isEmpty())
        {
            return 0
        }

        var max = this.array.size - 1
        var comparison = this.comparator.compare(element, this.array[max])

        if (comparison > 0)
        {
            return this.size
        }

        if (comparison == 0)
        {
            return max
        }

        comparison = this.comparator.compare(element, this.array[0])

        if (comparison <= 0)
        {
            return 0
        }

        var min = 0

        while (min + 1 < max)
        {
            val middle = (min + max) / 2
            comparison = this.comparator.compare(element, this.array[middle])

            when
            {
                comparison == 0 -> return middle
                comparison < 0  -> max = middle
                else            -> min = middle
            }
        }

        return max
    }

    /**
     * String representation
     */
    override fun toString(): String =
        this.array.toString()
}