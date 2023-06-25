package fr.jhelp.android.library.tasks

import java.util.concurrent.atomic.AtomicBoolean
import java.util.concurrent.atomic.AtomicLong
import org.junit.Assert
import org.junit.Test

class LockerTests
{
    private val locked = AtomicBoolean(false)
    private val timeLocked = AtomicLong(0)

    @Test
    fun lockUnlockTest()
    {
        val locker = Locker()
        Thread { this@LockerTests.lockedThread(locker) }.start()
        Thread.sleep(130L)
        Assert.assertTrue(this.locked.get())
        Thread.sleep(130L)
        locker.unlock()
        Thread.sleep(128L)
        Assert.assertFalse(this.locked.get())
        Assert.assertTrue("Time locked = ${this.timeLocked.get()}",
                          this.timeLocked.get() in 256L until 256L + 128L)
    }

    @Test
    fun unlockLockTest()
    {
        val locker = Locker()
        locker.unlock()
        Thread { this@LockerTests.lockedThread(locker) }.start()
        Thread.sleep(128L)
        Assert.assertFalse(this.locked.get())
        Assert.assertTrue("Time locked = ${this.timeLocked.get()}",
                          this.timeLocked.get() < 128L)
    }

    private fun lockedThread(locker: Locker)
    {
        val startTime = System.currentTimeMillis()
        this.locked.set(true)
        locker.lock()
        this.locked.set(false)
        this.timeLocked.set(System.currentTimeMillis() - startTime)
    }
}
