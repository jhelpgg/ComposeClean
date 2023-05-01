package fr.jhelp.tasks

import org.junit.Assert
import org.junit.Test

class ThreadSafeQueueTests
{
    @Test
    fun basicTests()
    {
        val queue = ThreadSafeQueue<String>()
        this.checkEmpty(queue)
        queue.enqueue("Hello")
        this.checkNotEmpty(1, queue)
        Assert.assertEquals("Hello", queue.dequeue())
        this.checkEmpty(queue)
        queue.enqueue("Hello")
        this.checkNotEmpty(1, queue)
        queue.enqueue("World")
        this.checkNotEmpty(2, queue)
        queue.enqueue("!")
        this.checkNotEmpty(3, queue)
        Assert.assertEquals("Hello", queue.dequeue())
        this.checkNotEmpty(2, queue)
        queue.enqueue("One more")
        this.checkNotEmpty(3, queue)
        Assert.assertEquals("World", queue.dequeue())
        this.checkNotEmpty(2, queue)
        Assert.assertEquals("!", queue.dequeue())
        this.checkNotEmpty(1, queue)
        Assert.assertEquals("One more", queue.dequeue())
        this.checkEmpty(queue)
    }

    @Test
    fun threadSafeTest()
    {
        val queue = ThreadSafeQueue<Int>()
        val threads = Array<Thread>(10) { index -> Thread { this.threadWork(index, queue) } }

        for (thread in threads)
        {
            thread.start()
        }

        for (thread in threads)
        {
            thread.join()
        }

        // 10 + 11 +12 + 13 + 14 +15 + 16 + 17 + 18 + 19 = 145

        Assert.assertEquals(145, queue.size)
    }

    private fun <T : Any> checkEmpty(queue: ThreadSafeQueue<T>)
    {
        Assert.assertEquals(0, queue.size)
        Assert.assertTrue(queue.empty)
        Assert.assertFalse(queue.notEmpty)

        try
        {
            @Suppress("UNUSED_VARIABLE") val ignored = queue.dequeue()
            Assert.fail("Should have an IllegalStateException since queue is empty")
        }
        catch (exception: IllegalStateException)
        {
            // That's what we expect
        }
    }

    private fun <T : Any> checkNotEmpty(expectedSize: Int, queue: ThreadSafeQueue<T>)
    {
        Assert.assertEquals(expectedSize, queue.size)
        Assert.assertFalse(queue.empty)
        Assert.assertTrue(queue.notEmpty)
    }

    private fun threadWork(threadID: Int, queue: ThreadSafeQueue<Int>)
    {
        Thread.sleep(128L)
        val number = threadID + 10

        for (index in 0 until number)
        {
            queue.enqueue(threadID * 100 + index)
        }
    }
}