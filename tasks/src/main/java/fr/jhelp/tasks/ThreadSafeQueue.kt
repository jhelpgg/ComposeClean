package fr.jhelp.tasks

import fr.jhelp.compose.collection.queue.Queue

class ThreadSafeQueue<E : Any>
{
    private val mutex = Mutex()
    private val queue = Queue<E>()
    val size: Int get() = this.mutex { this.queue.size }
    val empty: Boolean get() = this.mutex { this.queue.empty }
    val notEmpty: Boolean get() = this.mutex { this.queue.notEmpty }

    fun enqueue(element: E) = this.mutex { this.queue.enqueue(element) }

    @Throws(IllegalStateException::class)
    fun dequeue(): E = this.mutex { this.queue.dequeue() }
}