package fr.jhelp.tasks.extensions

import fr.jhelp.tasks.TaskType
import fr.jhelp.tasks.future.FutureResult
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.map

fun <P, R> Flow<P>.observedBy(taskType: TaskType = TaskType.SHORT_TASK, action: (P) -> R): FutureResult<Unit> =
    taskType.launch { this.collect { value -> action(value) } }

fun <P, R> Flow<P>.then(taskType: TaskType = TaskType.SHORT_TASK, action: (P) -> R): Flow<R> =
    this.map(action).flowOn(taskType.coroutineScope.coroutineContext)