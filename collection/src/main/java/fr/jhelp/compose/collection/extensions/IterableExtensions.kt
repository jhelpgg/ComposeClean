package fr.jhelp.compose.collection.extensions

import fr.jhelp.compose.collection.SelectedIterable
import fr.jhelp.compose.collection.TransformedIterable


fun <T : Any> Iterable<T>.select(criteria: (T) -> Boolean): Iterable<T> =
    if (this is SelectedIterable)
    {
        SelectedIterable<T>({ element -> this.criteria(element) && criteria(element) },
                            this.iterable)
    }
    else
    {
        SelectedIterable<T>(criteria, this)
    }

fun <P : Any, R : Any> Iterable<P>.transform(transformation: (P) -> R): Iterable<R> =
    TransformedIterable<P, R>(transformation, this)
