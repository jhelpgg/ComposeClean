package fr.jhelp.tasks.extensions

import fr.jhelp.tasks.TaskType
import fr.jhelp.tasks.future.FutureResult

fun <R : Any> (() -> R).parallel(taskType: TaskType = TaskType.SHORT_TASK): FutureResult<R> =
    taskType.launch(this)

fun <P : Any, R : Any> ((P) -> R).parallel(parameter: P,
                                           taskType: TaskType = TaskType.SHORT_TASK): FutureResult<R> =
    taskType.launch(parameter, this)
