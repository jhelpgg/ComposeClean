package fr.jhelp.tasks.extensions

import fr.jhelp.tasks.TaskType
import fr.jhelp.tasks.future.FutureResult

/**
 * Launch the function/lambda in parallel thread
 *
 * @return Future to be able to react on completion (success, failure or cancel)
 */
fun <R : Any> (() -> R).parallel(taskType: TaskType = TaskType.SHORT_TASK): FutureResult<R> =
    taskType.launch(this)

/**
 * Launch the function/lambda in parallel thread
 *
 *@param parameter Parmeter give at action when its effectively executed
 * @return Future to be able to react on completion (success, failure or cancel)
 */
fun <P : Any, R : Any> ((P) -> R).parallel(parameter: P,
                                           taskType: TaskType = TaskType.SHORT_TASK): FutureResult<R> =
    taskType.launch(parameter, this)
