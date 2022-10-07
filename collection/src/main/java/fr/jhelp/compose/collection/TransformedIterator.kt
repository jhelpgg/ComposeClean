package fr.jhelp.compose.collection

internal class TransformedIterator<P : Any, R : Any>(private val transformation: (P) -> R,
                                                     private val iterator: Iterator<P>) :
        Iterator<R>
{
    override fun hasNext(): Boolean =
        this.iterator.hasNext()

    override fun next(): R =
        this.transformation(this.iterator.next())
}