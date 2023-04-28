package fr.jhelp.compose.collection.queue

internal class QueueElement<E : Any>(val element: E, var next: QueueElement<E>? = null)