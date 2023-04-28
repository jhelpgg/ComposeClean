package fr.jhelp.tasks

import kotlinx.coroutines.sync.Semaphore


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
class LockerSuspend
{
    private val locker = Semaphore(1)

    /**
     * If their available key, it consumes one and let current thread continues
     *
     * If no available key, if makes current thread sleep utnil an available key
     */
    suspend fun lock()
    {
        this.locker.acquire()
    }

    /**
     * Provide a key to open a door
     *
     * If their at least one waiting thread, first of hem is wake up and key is consumed
     *
     * If their no waiting thread, the key is stored and will be consumed in a call of [lock]
     */
    fun unlock()
    {
        this.locker.release()
    }
}