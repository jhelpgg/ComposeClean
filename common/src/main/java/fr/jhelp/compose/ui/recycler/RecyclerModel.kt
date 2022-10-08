package fr.jhelp.compose.ui.recycler

interface RecyclerModel<T : Any> : Iterable<T>
{
    val size: Int
    val empty: Boolean
    val notEmpty: Boolean

    operator fun get(index: Int): T
    operator fun set(index: Int, value: T)

    operator fun plusAssign(element: T)
    operator fun plusAssign(elements: Collection<T>)
    operator fun plusAssign(elements: Array<T>)

    operator fun minusAssign(element: T)
    operator fun minusAssign(elements: Collection<T>)
    operator fun minusAssign(elements: Array<T>)

    fun clear()
    fun sort(comparator: Comparator<T>)
    fun filter(filter: (T) -> Boolean)
    fun removeFilter()
}