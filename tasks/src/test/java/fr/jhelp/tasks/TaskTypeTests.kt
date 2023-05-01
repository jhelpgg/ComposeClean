package fr.jhelp.tasks

import java.util.concurrent.atomic.AtomicBoolean
import org.junit.Assert
import org.junit.Test

class TaskTypeTests
{
    @Test
    fun cancelDelayAfterTimeout()
    {
        val called = AtomicBoolean(false)
        val future = TaskType.SHORT_TASK.delay(512L) { called.set(true) }
        Thread.sleep(1024L)
        future.cancel("Cancel")
        Assert.assertTrue(called.get())
    }

    @Test
    fun cancelDelayBeforeTimeout()
    {
        val called = AtomicBoolean(false)
        val future = TaskType.SHORT_TASK.delay(512L) { called.set(true) }
        Thread.sleep(256L)
        future.cancel("Cancel")
        Thread.sleep(1024L)
        Assert.assertFalse(called.get())
    }
}