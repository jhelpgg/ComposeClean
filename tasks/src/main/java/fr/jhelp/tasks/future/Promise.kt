package fr.jhelp.tasks.future

import fr.jhelp.tasks.TaskType

/**
 * When a task compute something in background,, it makes a promise do have a result in future.
 *
 * This object represents the prmise. Generally, the promise is created privately by the task
 * who do the computing and share the associated [FutureResult] to every process interest by the result
 */
class Promise<R : Any>
{
    /**Future associated to the promise. Give it to any one want follow the process result*/
    val future: FutureResult<R> = FutureResult<R>(this)

    /**Current cancel status*/
    var canceled: Boolean = false
        private set
    private lateinit var cancelReason: String
    private val listeners = ArrayList<Pair<TaskType, (String) -> Unit>>()
    private var resolvedd = false

    /**
     * For internal use : signal that cancel happen
     */
    internal fun cancel(reason: String)
    {
        this.resolvedd = true
        this.canceled = true
        this.cancelReason = reason

        synchronized(this.listeners)
        {
            this.listeners.forEach { listener -> listener.first.launch { listener.second(reason) } }
        }
    }

    /**
     * Publish the result
     */
    fun result(result: R)
    {
        this.resolvedd = true
        this.future.result(result)
    }

    /**
     * Publish a task error, result will never arrive
     */
    fun error(error: Exception)
    {
        this.resolvedd = true
        this.future.error(error)
    }

    /**
     * register to cancel event
     */
    fun onCancel(taskType: TaskType = TaskType.SHORT_TASK, cancelListener: (String) -> Unit)
    {
        if (this.canceled)
        {
            taskType.launch { cancelListener(this.cancelReason) }
            return
        }

        if (!this.resolvedd)
        {
            synchronized(this.listeners)
            {
                this.listeners.add(Pair(taskType, cancelListener))
            }
        }
    }
}