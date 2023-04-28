package fr.jhelp.tasks

import java.util.concurrent.Semaphore

/**
 * Play action in critical section
 *
 * When first thread enter in critical section, it is certain to not be interrupted.
 * If another thread try to enter in critical section while a thread in playing in it.
 * The other thread is put in sleep and put in waiting queue.
 * When a Thread exit from critical section,
 * a Thread from waiting queue is awake (if waiting is not empty), and permits entering in critical section
 *
 * It is recommended to not do heavy stuff inside critical section, else some threads may wait very long.
 */
class Mutex
{
    private val mutex = Semaphore(1, true)

    /**
     * Do action in critical section
     *
     * Action may wait its turn
     */
    operator fun <T> invoke(action : () -> T) : T
    {
        var exceptionHappen : Exception? = null
        var result : T? = null

        this.mutex.acquire(1)

        try
        {
            result = action()
        }
        catch (exception : Exception)
        {
            exceptionHappen = exception
        }

        this.mutex.release(1)

        if (exceptionHappen != null)
        {
            throw exceptionHappen
        }

        return result !!
    }
}
