package fr.jhelp.compose.collection.queue

class Queue<E : Any>
{
    private var head: QueueElement<E>? = null
    private var tail: QueueElement<E>? = null
    var size = 0
        private set
    val empty: Boolean get() = this.size == 0
    val notEmpty: Boolean get() = this.size != 0

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

    @Throws(IllegalStateException::class)
    fun dequeue(): E
    {
        val head = this.head ?: throw  IllegalStateException("The queue is empty")
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
}