package fr.jhelp.android.library.collection.queue

internal class QueueElement<E : Any>(val element: E, var next: QueueElement<E>? = null)