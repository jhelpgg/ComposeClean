package fr.jhelp.android.library.collection.queue

/**
 * Linked queue of elements
 */
class Queue<E : Any>
{
    private var head: QueueElement<E>? = null
    private var tail: QueueElement<E>? = null

    /** Queue size */
    var size: Int = 0
        private set

    /** Indicates if queue is empty */
    val empty: Boolean get() = this.size == 0

    /** Indicates if queue is not empty */
    val notEmpty: Boolean get() = this.size != 0

    /**
     * Enqueue an element at the end of the queue
     */
    fun enqueue(element: E)
    {
        if (this.head == null)
        {
            this.head = QueueElement<E>(element)
            this.tail = this.head
            this.size = 1
            return
        }

        val tail = this.tail!!
        tail.next = QueueElement<E>(element)
        this.tail = tail.next
        this.size++
    }

    /**
     * Dequeue element from the start of the queue.
     * I read it, remove it form the queue and returned the read value.
     *
     * @throws IllegalStateException If queue is empty
     */
    @Throws(IllegalStateException::class)
    fun dequeue(): E
    {
        val head = this.head ?: throw IllegalStateException("The queue is empty")
        val element = head.element
        this.head = head.next
        this.size--

        if (this.head == null)
        {
            this.tail = null
            this.size = 0
        }

        return element
    }

    /**
     * Look the element in the head of the queue without remove it
     *
     * @throws IllegalStateException If queue is empty
     */
    @Throws(IllegalStateException::class)
    fun peek(): E = this.head?.element ?: throw IllegalStateException("The queue is empty")
}