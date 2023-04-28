package fr.jhelp.tasks.extensions

import fr.jhelp.tasks.TaskType
import fr.jhelp.tasks.future.FutureResult
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.map

/**
 * Observe a flow change by given action on specified task type
 * @return Future to be able to be alert on issuee. And be able to cancel to listen change
 */
fun <P, R> Flow<P>.observedBy(taskType: TaskType = TaskType.SHORT_TASK, action: (P) -> R): FutureResult<Unit> =
    taskType.launch { this.collect { value -> action(value) } }

/**
 * Do something each time the flow produce a value
 *
 * The returned flow if the combination of this flow result wher apply the given transformation
 */
fun <P, R> Flow<P>.then(taskType: TaskType = TaskType.SHORT_TASK, action: (P) -> R): Flow<R> =
    this.map(action).flowOn(taskType.coroutineScope.coroutineContext)