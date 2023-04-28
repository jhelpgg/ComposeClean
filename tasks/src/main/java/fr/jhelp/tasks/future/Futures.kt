package fr.jhelp.tasks.future

import java.util.concurrent.atomic.AtomicInteger

/**
 * Simplify a FutureResult<FutureResult&lt;R&gt;> to a FutureResult&lt;R&gt;
 */
fun <R : Any> FutureResult<FutureResult<R>>.unwrap(): FutureResult<R> {
    val promise = Promise<R>()
    promise.onCancel { reason -> this.cancel(reason) }

    this.then { future ->
        try {
            promise.result(future()())
        } catch (exception: Exception) {
            promise.error(exception)
        }
    }

    return promise.future
}

/**
 * Create a future that wait all futures are finished
 */
fun List<FutureResult<*>>.join(): FutureResult<List<FutureResult<*>>> {
    val promise = Promise<List<FutureResult<*>>>()
    val counter = AtomicInteger(this.size)
    val continuation =
            { _: FutureResult<*> ->
                if (counter.decrementAndGet() == 0) {
                    promise.result(this)
                }
            }

    promise.onCancel { reason ->
        for (future in this) {
            future.cancel(reason)
        }
    }

    for (future in this) {
        future.then(continuation = continuation)
    }

    return promise.future
}

fun <T : Any> T.future(): FutureResult<T> {
    val promise = Promise<T>()
    promise.result(this)
    return promise.future
}

fun <T : Any> Exception.futureError(): FutureResult<T> {
    val promise = Promise<T>()
    promise.error(this)
    return promise.future
}

fun <T : Any> String.futureCancel(): FutureResult<T> {
    val promise = Promise<T>()
    promise.future.cancel(this)
    return promise.future
}