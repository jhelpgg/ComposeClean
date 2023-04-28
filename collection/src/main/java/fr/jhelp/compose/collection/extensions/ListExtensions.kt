package fr.jhelp.compose.collection.extensions

inline fun <E> List<E>.forEachReversed(action: (E) -> Unit)
{
    for (index in this.size - 1 downTo 0)
    {
        action(this[index])
    }
}