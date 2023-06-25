package fr.jhelp.android.library.tasks

import fr.jhelp.compose.collection.queue.Queue

/**
 * Thread safe queue.
 *
 * Queue to use in concurrent context. Access is slower than normal queue, so be sure the tread safety is necessary
 */
class ThreadSafeQueue<E : Any>
{
    private val mutex = Mutex()
    private val queue = Queue<E>()

    /** Queue size */
    val size: Int get() = this.mutex { this.queue.size }

    /** Indicates if queue is empty */
    val empty: Boolean get() = this.mutex { this.queue.empty }

    /** Indicates if queue is not empty */
    val notEmpty: Boolean get() = this.mutex { this.queue.notEmpty }

    /**
     * Enqueue element at the end of the queue
     */
    fun enqueue(element: E): Unit = this.mutex { this.queue.enqueue(element) }

    /**
     * Dequeue first element from th queue
     */
    @Throws(IllegalStateException::class)
    fun dequeue(): E = this.mutex { this.queue.dequeue() }
}