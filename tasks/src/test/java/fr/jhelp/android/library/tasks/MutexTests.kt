package fr.jhelp.android.library.tasks

import kotlin.math.max
import org.junit.Assert
import org.junit.Test

class MutexTests
{
    private var numberActiveInsideMutex = 0
    private var maximumNumberActiveInsideMutex = 0
    private val mutex = Mutex()

    @Test
    fun mutexSafetyTest()
    {
        this.numberActiveInsideMutex = 0
        this.maximumNumberActiveInsideMutex = 0
        val threads = Array<Thread>(16) { Thread(this::threadAction) }

        for (thread in threads)
        {
            thread.start()
        }

        for (thread in threads)
        {
            thread.join()
        }

        Assert.assertEquals(0, this.numberActiveInsideMutex)
        Assert.assertEquals(1, this.maximumNumberActiveInsideMutex)
    }

    private fun threadAction()
    {
        Thread.sleep(256L)

        this.mutex {
            Thread.sleep(128L)
            this.numberActiveInsideMutex++
            Thread.sleep(128L)
            this.maximumNumberActiveInsideMutex =
                max(this.maximumNumberActiveInsideMutex, this.numberActiveInsideMutex)
            Thread.sleep(128L)
            this.numberActiveInsideMutex--
            Thread.sleep(128L)
        }
    }
}