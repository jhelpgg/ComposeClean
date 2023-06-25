package fr.jhelp.android.library.common.ui.recycler

/**
 * Represents a recycler model.
 * Its a list of something
 */
interface RecyclerModel<T : Any> : Iterable<T>
{
    /** Number of elements stored */
    val size: Int

    /** Indicates if model is empty */
    val empty: Boolean

    /** Indicates if model is not empty */
    val notEmpty: Boolean

    /**
     * Obtain an element an given index.
     *
     * Operator to allow `[]` syntax :
     *
     * ```kotlin
     * val value = recyclerModel[5]
     * ```
     *
     * @param index Element's index
     * @return Requested element
     */
    operator fun get(index: Int): T

    /**
     * Change a value of the list.
     *
     * Operator to allow `[]` syntax :
     *
     * ```kotlin
     * recyclerModel[3] = computeValue()
     * ```
     *
     * @param index Element's index
     * @param value New value to put at given index
     */
    operator fun set(index: Int, value: T)


    /**
     * Add element at the end of the list
     *
     * Operator to allow `+=` syntax :
     *
     * ```kotlin
     * recyclerModel += newValue()
     * ```
     *
     * @param element Element to add
     */
    operator fun plusAssign(element: T)

    /**
     * Add elements of collection at the end of the list
     *
     * Operator to allow `+=` syntax :
     *
     * ```kotlin
     * recyclerModel += listOfElements
     * ```
     *
     * @param elements Collection of elements to add
     */
    operator fun plusAssign(elements: Collection<T>)

    /**
     * Add elements of array at the end of the list
     *
     * Operator to allow `+=` syntax :
     *
     * ```kotlin
     * recyclerModel += arrayOfElements
     * ```
     *
     * @param elements Array of elements to add
     */
    operator fun plusAssign(elements: Array<T>)


    /**
     * Remove an element of the list
     *
     * Operator to allow `-=` syntax :
     *
     * ```kotlin
     * recyclerModel -= value
     * ```
     *
     * @param element Element to remove
     */
    operator fun minusAssign(element: T)

    /**
     * Remove elements in collection of the list
     *
     * Operator to allow `-=` syntax :
     *
     * ```kotlin
     * recyclerModel -= values
     * ```
     *
     * @param elements Collection of elements to remove
     */
    operator fun minusAssign(elements: Collection<T>)

    /**
     * Remove elements in array of the list
     *
     * Operator to allow `-=` syntax :
     *
     * ```kotlin
     * recyclerModel -= values
     * ```
     *
     * @param elements Array of elements to remove
     */
    operator fun minusAssign(elements: Array<T>)


    /**
     * Remove all elements in the list
     */
    fun clear()

    /**
     * Sort elements on using the comparator
     */
    fun sort(comparator: Comparator<T>)

    /**
     * Filter elements.
     * It let only visible elements that match the given filter
     */
    fun filter(filter: (T) -> Boolean)

    /**
     * Remove last filter.
     * All elements will become visible
     */
    fun removeFilter()
}