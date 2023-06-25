package fr.jhelp.android.library.tasks.network

import fr.jhelp.android.library.tasks.Locker
import fr.jhelp.android.library.tasks.ThreadSafeQueue
import fr.jhelp.android.library.tasks.extensions.observedBy
import fr.jhelp.android.library.tasks.future.FutureResult
import java.util.concurrent.atomic.AtomicBoolean
import kotlin.coroutines.CoroutineContext
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.Runnable
import kotlinx.coroutines.launch

/**
 * Dispatcher used for network ask type
 */
object NetworkDispatcher : CoroutineDispatcher()
{
    private val scope = CoroutineScope(Dispatchers.IO.limitedParallelism(1))
    private val futureNetworkStatus: FutureResult<Unit>
    private var job: Job? = null
    private var networkAvailable = false
    private val playing = AtomicBoolean(false)
    private val queue = ThreadSafeQueue<Runnable>()
    private val locked = AtomicBoolean(false)
    private val locker = Locker()

    init
    {
        this.futureNetworkStatus =
            NetworkStatusCallback.availableFlow.observedBy(action = this::networkAvailable)
    }

    override fun dispatch(context: CoroutineContext, block: Runnable)
    {
        this.queue.enqueue(block)

        if (this.playing.compareAndSet(false, true))
        {
            this.job = this.scope.launch { this@NetworkDispatcher.play() }
        }
    }

    internal fun stop()
    {
        this.futureNetworkStatus.cancel("Stop")
        this.job?.cancel()
        this.playing.set(false)
    }

    private fun networkAvailable(networkAvailable: Boolean)
    {
        NetworkDispatcher.networkAvailable = networkAvailable

        if (networkAvailable && this.locked.compareAndSet(true, false))
        {
            this.locker.unlock()
        }
    }

    private fun play()
    {
        var block: Runnable?

        do
        {
            block =
                if (this.queue.empty)
                {
                    null
                }
                else
                {
                    this.queue.dequeue()
                }


            if (block != null && this.job?.isCancelled == false)
            {
                if (!this.networkAvailable)
                {
                    this.locked.set(true)
                    this.locker.lock()
                }

                if (this.job?.isCancelled == false)
                {
                    try
                    {
                        block.run()
                    }
                    catch (exception: Exception)
                    {
                        // If fails and no network,
                        // we suppose that's because there no Internet
                        // So we try to do the task later when network comes back
                        if (!this.networkAvailable)
                        {
                            this.queue.enqueue(block)
                        }
                    }
                }
                else
                {
                    // Job is cancelled, re-inject the task to do it later
                    this.queue.enqueue(block)
                }
            }
            else if (block != null)
            {
                // Job is cancelled, re-inject the task to do it later
                this.queue.enqueue(block)
            }
        }
        while (block != null && this.job?.isCancelled == false)

        this.playing.set(false)
    }
}