package fr.jhelp.tasks.future

import fr.jhelp.tasks.TaskType
import fr.jhelp.tasks.extensions.parallel
import java.util.concurrent.atomic.AtomicReference

/**
 * Future result of something
 *
 * The result is said complete when computing finished.
 * That is to say when task succeed to compute OR an error happen while computing OR task canceled
 *
 * Its possible to chain an other task to do when task complete, by example say :
 * * When result succeed , use the result to do this other task
 * * When result complete, do something
 * * If result fail, then do this
 * * ...
 */
class FutureResult<R : Any> internal constructor(private val promise: Promise<R>) {
    private val status = AtomicReference<FutureResultStatus>(FutureResultStatus.COMPUTING)
    private val lock = Object()
    private lateinit var result: R
    private lateinit var cancelReason: String
    private lateinit var error: Exception
    private val listeners = ArrayList<Pair<(FutureResult<R>) -> Unit, TaskType>>()

    internal fun result(result: R) {
        synchronized(this.lock)
        {
            if (this.status.compareAndSet(FutureResultStatus.COMPUTING, FutureResultStatus.SUCCEED)) {
                this.result = result
                this.lock.notifyAll()
            }
        }

        this.fireListeners()
    }

    internal fun error(error: Exception) {
        synchronized(this.lock)
        {
            if (this.status.compareAndSet(FutureResultStatus.COMPUTING, FutureResultStatus.FAILED)) {
                this.error = error
                this.lock.notifyAll()
            }
        }

        this.fireListeners()
    }

    private fun fireListeners() {
        synchronized(this.listeners) {
            for (pair in this.listeners) {
                pair.second.launch { pair.first(this) }
            }

            this.listeners.clear()
            this.listeners.trimToSize()
        }
    }

    private fun <R1 : Any> andListener(continuation: (R) -> R1, promise: Promise<R1>) =
            this.andListener(continuation, promise) { true }

    private fun <R1 : Any> andListener(continuation: (R) -> R1, promise: Promise<R1>,
                                       condition: (R) -> Boolean) =
            { future: FutureResult<R> ->
                when (future.status()) {
                    FutureResultStatus.SUCCEED ->
                        try {
                            val result = future()

                            if (condition(result)) {
                                promise.result(continuation(result))
                            } else {
                                promise.error(Exception("Not matching condition"))
                            }
                        } catch (exception: Exception) {
                            promise.error(exception)
                        }
                    FutureResultStatus.FAILED ->
                        promise.error(future.error)
                    FutureResultStatus.CANCELED ->
                        promise.error(CancellationException(this.cancelReason))
                    else -> Unit
                }
            }

    private fun <R1 : Any> thenListener(continuation: (FutureResult<R>) -> R1,
                                        promise: Promise<R1>) =
            { future: FutureResult<R> ->
                try {
                    promise.result(continuation(future))
                } catch (exception: Exception) {
                    promise.error(exception)
                }
            }

    private fun errorListener(errorListener: (Exception) -> Unit,
                              taskType: TaskType): (FutureResult<R>) -> Unit {
        return { future: FutureResult<R> ->
            when (future.status()) {
                FutureResultStatus.FAILED ->
                    taskType.launch { errorListener(future.error) }
                FutureResultStatus.CANCELED ->
                    taskType.launch { errorListener(CancellationException(this.cancelReason)) }
                else -> Unit
            }
        }
    }

    private fun cancelListener(cancelListener: (String) -> Unit, taskType: TaskType) =
            { future: FutureResult<R> ->
                if (future.status() == FutureResultStatus.CANCELED) {
                    taskType.launch { cancelListener(this.cancelReason) }
                }
            }

    /**
     * Current future status.
     */
    fun status() = this.status.get()

    /**
     * Register listener called when result is known. If already know, listener is callback immediately
     *
     * @param listener Listener to register
     * @param taskType : Thread type where execute the listener
     */
    fun register(taskType: TaskType = TaskType.SHORT_TASK, listener: (FutureResult<R>) -> Unit) {
        synchronized(this.lock)
        {
            if (this.status.get() != FutureResultStatus.COMPUTING) {
                taskType.launch { listener(this) }
                return@register
            }

            synchronized(this.listeners)
            {
                this.listeners.add(Pair(listener, taskType))
            }
        }
    }

    /**
     * Block caller thread until result iis known, an error happen or cancel is triggered
     * If one of those events already happen, thread is not blocked.
     *
     * @return The result
     * @throws Exception if computation failed or cancel happen
     */
    operator fun invoke(): R {
        synchronized(this.lock)
        {
            if (this.status.get() == FutureResultStatus.COMPUTING) {
                this.lock.wait()
            }
        }

        when (this.status.get()) {
            FutureResultStatus.SUCCEED -> return this.result
            FutureResultStatus.FAILED -> throw this.error
            FutureResultStatus.CANCELED -> throw CancellationException(this.cancelReason)
            else ->
                throw RuntimeException("Should no goes here : ${this.status.get()}")
        }
    }

    /**
     *  Block caller thread until result iis known, an error happen or cancel is triggered
     * If one of those events already happen, thread is not blocked.
     *
     * @return Future complete status
     */
    fun waitComplete(): FutureResultStatus {
        synchronized(this.lock)
        {
            if (this.status.get() == FutureResultStatus.COMPUTING) {
                this.lock.wait()
            }
        }

        return this.status.get()
    }

    /**
     * Current error.
     *
     * If future still computing, there no actually an error, but may will have on the end
     */
    fun error() =
            synchronized(this.lock)
            {
                if (this.status.get() == FutureResultStatus.FAILED) this.error else null
            }

    /**
     * Cancellation reason.
     *
     * Have meaning only if future is canceled
     *
     * If future still computing, there no actually a cancel reason, but may will have on the end
     */
    fun cancelReason() =
            synchronized(this.lock)
            {
                if (this.status.get() == FutureResultStatus.CANCELED) this.cancelReason else null
            }

    /**
     * Try to cancel task associated to the thread
     *
     * @return `true` if cancel request is propagate to current task.
     * Else it means the future is already complete, so too late for cancel
     */
    fun cancel(reason: String): Boolean {
        val canceled =
                synchronized(this.lock)
                {
                    if (this.status.compareAndSet(FutureResultStatus.COMPUTING,
                                    FutureResultStatus.CANCELED)) {
                        this.cancelReason = reason
                        this.promise::cancel.parallel(reason)
                        true
                    } else {
                        false
                    }
                }

        if (canceled) {
            this.fireListeners()
        }

        return canceled
    }

    /**
     * Do task, in specified thread type, when result complete and if succeed to compute
     *
     * @return Future result represents the combination of the result follow by the task
     */
    fun <R1 : Any> and(taskType: TaskType = TaskType.SHORT_TASK,
                       continuation: (R) -> R1): FutureResult<R1> {
        val promise = Promise<R1>()
        promise.onCancel { reason -> this.cancel(reason) }
        this.register(taskType, this.andListener(continuation, promise))
        return promise.future
    }

    /**
     * Do task, in [TaskType.SHORT_TASK], when result complete and if succeed to compute and result match given condition
     */
    fun <R1 : Any> andIf(condition: (R) -> Boolean, continuation: (R) -> R1) =
            this.andIf(TaskType.SHORT_TASK, condition, continuation)

    /**
     * Do task, in specified thread type, when result complete and if succeed to compute and result match given condition
     *
     * @return Future result represents the combination of the result follow by the task
     */
    fun <R1 : Any> andIf(taskType: TaskType,
                         condition: (R) -> Boolean,
                         continuation: (R) -> R1): FutureResult<R1> {
        val promise = Promise<R1>()
        promise.onCancel { reason -> this.cancel(reason) }
        this.register(taskType, this.andListener(continuation, promise, condition))
        return promise.future
    }

    /**
     * Do task, in specified thread type, when result complete
     *
     * @return Future result represents the combination of the result follow by the task
     */
    fun <R1 : Any> then(taskType: TaskType = TaskType.SHORT_TASK,
                        continuation: (FutureResult<R>) -> R1): FutureResult<R1> {
        val promise = Promise<R1>()
        promise.onCancel { reason -> this.cancel(reason) }
        this.register(taskType, this.thenListener(continuation, promise))
        return promise.future
    }

    /**
     * Do task, in specified thread type, when result complete and if succeed to compute
     *
     * @return Future result represents the combination of the result follow by the task
     */
    fun <R1 : Any> andUnwrap(taskType: TaskType = TaskType.SHORT_TASK,
                             continuation: (R) -> FutureResult<R1>): FutureResult<R1> =
            this.and(taskType, continuation).unwrap()

    /**
     * Do task, in [TaskType.SHORT_TASK], when result complete and if succeed to compute and result match given condition
     *
     * @return Future result represents the combination of the result follow by the task
     */
    fun <R1 : Any> andIfUnwrap(condition: (R) -> Boolean, continuation: (R) -> FutureResult<R1>) =
            this.andIfUnwrap(TaskType.SHORT_TASK, condition, continuation)

    /**
     * Do task, in specified thread type, when result complete and if succeed to compute and result match given condition
     *
     * @return Future result represents the combination of the result follow by the task
     */
    fun <R1 : Any> andIfUnwrap(taskType: TaskType,
                               condition: (R) -> Boolean,
                               continuation: (R) -> FutureResult<R1>): FutureResult<R1> =
            this.andIf(taskType, condition, continuation).unwrap()

    /**
     * Do task, in specified thread type, when result complete
     *
     * @return Future result represents the combination of the result follow by the task
     */
    fun <R1 : Any> thenUnwrap(taskType: TaskType = TaskType.SHORT_TASK,
                              continuation: (FutureResult<R>) -> FutureResult<R1>): FutureResult<R1> =
            this.then(taskType, continuation).unwrap()

    /**
     * Do task, in specified thread type, when result failed
     *
     * @return This future result. Convenient for chaining
     */
    fun onError(taskType: TaskType = TaskType.SHORT_TASK,
                errorListener: (Exception) -> Unit): FutureResult<R> {
        this.register(TaskType.SHORT_TASK, this.errorListener(errorListener, taskType))
        return this
    }

    /**
     * Do task, in specified thread type, when result canceled
     *
     * @return This future result. Convenient for chaining
     */
    fun onCancel(taskType: TaskType = TaskType.SHORT_TASK,
                 cancelListener: (String) -> Unit): FutureResult<R> {
        this.register(TaskType.SHORT_TASK, this.cancelListener(cancelListener, taskType))
        return this
    }

    /**
     * String representation
     */
    override fun toString() =
            synchronized(this.lock)
            {
                when (this.status.get()) {
                    FutureResultStatus.SUCCEED -> "Succeed : ${this.result}"
                    FutureResultStatus.FAILED -> "Error : ${this.error}"
                    FutureResultStatus.CANCELED -> "Canceled because : ${this.cancelReason}"
                    FutureResultStatus.COMPUTING -> "Computing ..."
                    else -> "Why am I there ? : ${this.status.get()}"
                }
            }
}