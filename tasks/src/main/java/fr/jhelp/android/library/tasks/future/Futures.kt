package fr.jhelp.android.library.tasks.future

import fr.jhelp.android.library.tasks.Mutex
import fr.jhelp.android.library.tasks.TaskType
import java.util.concurrent.atomic.AtomicInteger
import java.util.concurrent.atomic.AtomicReference

/**
 * Simplify a FutureResult<FutureResult&lt;R&gt;> to a FutureResult&lt;R&gt;
 */
fun <R : Any> FutureResult<FutureResult<R>>.unwrap(): FutureResult<R>
{
    val promise = Promise<R>()
    promise.onCancel { reason -> this.cancel(reason) }

    this.then { future ->
        try
        {
            promise.result(future.invoke().invoke())
        }
        catch (exception: Exception)
        {
            promise.error(exception)
        }
    }

    return promise.future
}

/**
 * Create a future that wait all futures are finished
 */
fun List<FutureResult<*>>.join(): FutureResult<List<FutureResult<*>>>
{
    val promise = Promise<List<FutureResult<*>>>()
    val counter = AtomicInteger(this.size)
    val continuation =
        { _: FutureResult<*> ->
            if (counter.decrementAndGet() == 0)
            {
                promise.result(this)
            }
        }

    promise.onCancel { reason ->
        for (future in this)
        {
            future.cancel(reason)
        }
    }

    for (future in this)
    {
        future.then(continuation = continuation)
    }

    return promise.future
}

/**
 * Create a future that succeed and have the object instance as value
 */
fun <T : Any> T.future(): FutureResult<T>
{
    val promise = Promise<T>()
    promise.result(this)
    return promise.future
}

/**
 * Create a future that failed and the error is the exception instance
 */
fun <T : Any> Exception.futureError(): FutureResult<T>
{
    val promise = Promise<T>()
    promise.error(this)
    return promise.future
}

/**
 * Create future canceled. Use the String as cancellation reason
 */
fun <T : Any> String.futureCancel(): FutureResult<T>
{
    val promise = Promise<T>()
    promise.future.cancel(this)
    return promise.future
}

/**
 * Combine two future to one.
 *
 * When the both futures succeed, their result used the combiner to create the final result.
 *
 * If one of futures complete without success, the final future will fail or cancel
 */
fun <R1 : Any, R2 : Any, R3 : Any> combine(future1: FutureResult<R1>,
                                           future2: FutureResult<R2>,
                                           combiner: (R1, R2) -> R3,
                                           taskType: TaskType = TaskType.SHORT_TASK): FutureResult<R3>
{
    val promiseCombination = Promise<R3>()
    future1.onError { exception -> promiseCombination.error(exception) }
    future1.onCancel { reason -> promiseCombination.future.cancel(reason) }
    future2.onError { exception -> promiseCombination.error(exception) }
    future2.onCancel { reason -> promiseCombination.future.cancel(reason) }
    promiseCombination.onCancel { reason ->
        future1.cancel(reason)
        future2.cancel(reason)
    }

    val mutex = Mutex()
    val result1 = AtomicReference<R1>()
    val result2 = AtomicReference<R2>()

    val actionCheck: () -> Unit = {
        val r1 = result1.get()
        val r2 = result2.get()

        if (r1 != null && r2 != null)
        {
            try
            {
                val r3 = combiner(r1, r2)
                promiseCombination.result(r3)
            }
            catch (exception: Exception)
            {
                promiseCombination.error(exception)
            }
        }
    }

    future1.and(taskType) { r1 ->
        mutex {
            result1.set(r1)
            actionCheck()
        }
    }


    future2.and(taskType) { r2 ->
        mutex {
            result2.set(r2)
            actionCheck()
        }
    }

    return promiseCombination.future
}
