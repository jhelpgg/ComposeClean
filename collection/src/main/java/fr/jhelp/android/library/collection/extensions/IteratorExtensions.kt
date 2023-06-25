package fr.jhelp.android.library.collection.extensions

import fr.jhelp.android.library.collection.SelectedIterator
import fr.jhelp.android.library.collection.TransformedIterator


/**
 * Creates iterator with elements filtered by a given criteria
 */
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

/**
 * Creates iterator that each elements are transformed by given transformation
 */
fun <P : Any, R : Any> Iterator<P>.transform(transformation: (P) -> R): Iterator<R> =
    TransformedIterator<P, R>(transformation, this)
