package fr.jhelp.android.library.collection

/**
 * Iterable with transformed values
 */
internal class TransformedIterable<P : Any, R : Any>(private val transformation: (P) -> R,
                                                     private val iterable: Iterable<P>) :
        Iterable<R>
{
    override fun iterator(): Iterator<R> =
        TransformedIterator<P, R>(this.transformation, this.iterable.iterator())
}
