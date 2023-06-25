package fr.jhelp.android.library.tasks.future

import java.util.concurrent.atomic.AtomicInteger

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