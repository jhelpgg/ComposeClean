package fr.jhelp.compose.collection.extensions

import fr.jhelp.compose.collection.SelectedIterable
import fr.jhelp.compose.collection.TransformedIterable

/**
 * Creates iterable with elements filtered by a given criteria
 *
 * Most fast and take less memory than [Iterable.filter]
 */
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

/**
 * Creates iterable that each elements are transformed by given transformation
 *
 * Most fast and take less memory than [Iterable.map]
 */
fun <P : Any, R : Any> Iterable<P>.transform(transformation: (P) -> R): Iterable<R> =
    TransformedIterable<P, R>(transformation, this)
