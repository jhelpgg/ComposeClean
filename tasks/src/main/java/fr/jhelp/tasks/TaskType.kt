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

enum class TaskType(val coroutineScope: CoroutineScope)
{
    SHORT_TASK(CoroutineScope(Dispatchers.Default.limitedParallelism(8))), //newFixedThreadPoolContext(8, "ShortTasks")),
    HEAVY_TASK(CoroutineScope(Dispatchers.Default.limitedParallelism(4))),
    NETWORK(CoroutineScope(NetworkDispatcher)),
    IO(CoroutineScope(Dispatchers.IO)),
    MAIN(MainScope())
    ;

    fun <R : Any> launch(task: suspend () -> R): FutureResult<R> =
        this.delay(0L, task)

    fun <P : Any, R : Any> launch(parameter: P, task: suspend (P) -> R): FutureResult<R> =
        this.delay(0L, parameter, task)

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