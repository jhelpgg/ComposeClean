package fr.jhelp.tasks

import java.util.concurrent.Semaphore

/**
 * Locker of execution.
 *
 * If a thread call [lock], it goes in sleep until another thread calls [unlock]
 *
 * if [unlock] was called before [lock], the thread is not blocked.
 *
 * Developers must assure that in any situation there ae the same number of call of [lock] and [unlock].
 * Else it may lead to threads sleep forever.
 *
 * [lock] can be sees as a door, locked, until a key is available
 *
 * [unlock] provides a key to open one door, the key break once use, need one more key to open another door
 */
class Locker
{
    private val locker = Semaphore(0, true)

    /**
     * If their available key, it consumes one and let current thread continues
     *
     * If no available key, if makes current thread sleep until an available key
     */
    fun lock()
    {
        this.locker.acquire(1)
    }

    /**
     * Provide a key to open a door
     *
     * If their at least one waiting thread, first of them is wake up and key is consumed
     *
     * If their no waiting thread, the key is stored and will be consumed in a call of [lock]
     */
    fun unlock()
    {
        this.locker.release(1)
    }
}