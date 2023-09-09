package fr.jhelp.android.library.tasks.extensions

import fr.jhelp.android.library.tasks.TaskType
import fr.jhelp.android.library.tasks.future.FutureResult
import fr.jhelp.android.library.tasks.future.Promise
import java.util.concurrent.atomic.AtomicBoolean
import kotlinx.coroutines.flow.Flow

/**
 * Observe a flow change by given action on specified task type
 * @return Future to be able to be alert on issue. And be able to cancel to listen change
 */
fun <P, R> Flow<P>.observedBy(taskType: TaskType = TaskType.SHORT_TASK,
                              action: (P) -> R): FutureResult<Unit> =
    taskType.launch { this.collect { value -> action(value) } }

/**
 * Do something each time the flow produce a value
 *
 * The returned flow if the combination of this flow result where apply the given transformation
 */
fun <P, R> Flow<P>.then(taskType: TaskType = TaskType.SHORT_TASK, action: (P) -> R): Flow<R> =
    FlowThen<P, R>(this, taskType, action)

/**
 * React when flow have a specified condition.
 *
 * Action is played onne time when the condition is fulfilled.
 *
 * If condition already satisfied, play immediately
 *
 * @return Future to be able to be alert on issue. And be able to cancel to listen change
 */
fun <P, R> Flow<P>.doWhen(taskType: TaskType = TaskType.SHORT_TASK,
                          condition: (P) -> Boolean,
                          action: (P) -> R): FutureResult<Unit>
{
    val done = AtomicBoolean(false)
    val promise = Promise<Unit>()
    val attached = this.observedBy(taskType) { value ->
        if (condition(value) && done.compareAndSet(false, true))
        {
            try
            {
                action(value)
                promise.result(Unit)
            }
            catch (exception: Exception)
            {
                promise.error(exception)
            }
        }
    }


    promise.onCancel { reason -> attached.cancel(reason) }
    promise.future.then { _ -> attached.cancel("Done") }

    return promise.future
}
