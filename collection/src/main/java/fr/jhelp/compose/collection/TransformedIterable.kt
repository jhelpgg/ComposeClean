package fr.jhelp.compose.collection

internal class TransformedIterable<P : Any, R : Any>(private val transformation: (P) -> R,
                                                     private val iterable: Iterable<P>) :
        Iterable<R>
{
    override fun iterator(): Iterator<R> =
        TransformedIterator<P, R>(this.transformation, this.iterable.iterator())
}
