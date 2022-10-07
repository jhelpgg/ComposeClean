package fr.jhelp.compose.collection.extensions

import fr.jhelp.compose.collection.SelectedIterator
import fr.jhelp.compose.collection.TransformedIterator


fun <T : Any> Iterator<T>.select(criteria: (T) -> Boolean): Iterator<T> =
    if (this is SelectedIterator)
    {
        SelectedIterator<T>({ element -> this.criteria(element) && criteria(element) },
                            this.iterator)
    }
    else
    {
        SelectedIterator<T>(criteria, this)
    }

fun <P : Any, R : Any> Iterator<P>.transform(transformation: (P) -> R): Iterator<R> =
    TransformedIterator<P, R>(transformation, this)
