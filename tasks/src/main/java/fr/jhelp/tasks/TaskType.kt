package fr.jhelp.tasks

import fr.jhelp.tasks.future.FutureResult
import fr.jhelp.tasks.future.Promise
import fr.jhelp.tasks.network.NetworkDispatcher
import java.util.concurrent.atomic.AtomicBoolean
import kotlinx.coroutines.CancellationException
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.launch

/**
 * Task type context for play parallel tasks
 */
enum class TaskType(internal val coroutineScope: CoroutineScope)
{
    /** For short, not heavy tasks */
    SHORT_TASK(CoroutineScope(Dispatchers.Default.limitedParallelism(8))),

    /** For long or heavy tasks */
    HEAVY_TASK(CoroutineScope(Dispatchers.Default.limitedParallelism(4))),

    /** Play task when network is available */
    NETWORK(CoroutineScope(NetworkDispatcher)),

    /** Play task that do read/write on files/database */
    IO(CoroutineScope(Dispatchers.IO)),

    /** Play task on main/UI thread */
    MAIN(MainScope())
    ;

    /**
     * Launch a task
     * @return A future to be able to react on completion
     */
    fun <R : Any> launch(task: suspend () -> R): FutureResult<R> =
        this.delay(0L, task)

    /**
     * Launch a task
     * @return A future to be able to react on completion
     */
    fun <P : Any, R : Any> launch(parameter: P, task: suspend (P) -> R): FutureResult<R> =
        this.delay(0L, parameter, task)

    /**
     * Delay a task to do it later
     * @return A future to be able to react on completion or cancel before th delay
     */
    fun <R : Any> delay(delayMilliseconds: Long, task: suspend () -> R): FutureResult<R>
    {
        val promise = Promise<R>()
        val cancelled = AtomicBoolean(false)

        val job = this.coroutineScope.launch {
            if (delayMilliseconds > 0L)
            {
                kotlinx.coroutines.delay(delayMilliseconds)
            }

            if (cancelled.get())
            {
                return@launch
            }

            try
            {
                promise.result(task())
            }
            catch (exception: Exception)
            {
                promise.error(exception)
            }
        }


        promise.onCancel { reason ->
            cancelled.set(true)
            job.cancel(CancellationException(reason))
        }

        return promise.future
    }

    /**
     * Delay a task to do it later
     * @return A future to be able to react on completion or cancel before th delay
     */
    fun <P : Any, R : Any> delay(delayMilliseconds: Long,
                                 parameter: P,
                                 task: suspend (P) -> R): FutureResult<R>
    {
        val promise = Promise<R>()
        val cancelled = AtomicBoolean(false)

        val job = this.coroutineScope.launch {
            if (delayMilliseconds > 0L)
            {
                kotlinx.coroutines.delay(delayMilliseconds)
            }

            if (cancelled.get())
            {
                return@launch
            }

            try
            {
                promise.result(task(parameter))
            }
            catch (exception: Exception)
            {
                promise.error(exception)
            }
        }


        promise.onCancel { reason ->
            cancelled.set(true)
            job.cancel(CancellationException(reason))
        }

        return promise.future
    }
}