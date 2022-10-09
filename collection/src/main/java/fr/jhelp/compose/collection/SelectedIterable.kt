package fr.jhelp.compose.collection

/**
 * Iterable with filtered values
 */
internal class SelectedIterable<T : Any>(val criteria: (T) -> Boolean,
                                         val iterable: Iterable<T>) :
        Iterable<T>
{
    override fun iterator(): Iterator<T> =
        SelectedIterator<T>(this.criteria, this.iterable.iterator())
}
