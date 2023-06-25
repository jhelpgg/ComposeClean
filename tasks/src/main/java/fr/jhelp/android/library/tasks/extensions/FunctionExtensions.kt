package fr.jhelp.android.library.tasks.extensions

import fr.jhelp.android.library.tasks.TaskType
import fr.jhelp.android.library.tasks.future.FutureResult

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
 *@param parameter Parameter give at action when its effectively executed
 * @return Future to be able to react on completion (success, failure or cancel)
 */
fun <P : Any, R : Any> ((P) -> R).parallel(parameter: P,
                                           taskType: TaskType = TaskType.SHORT_TASK): FutureResult<R> =
    taskType.launch(parameter, this)

/**
 * Launch the function/lambda in parallel thread with a delay
 *
 * @return Future to be able to react on completion (success, failure or cancel)
 */
fun <R : Any> (() -> R).delay(delayInMilliseconds: Long,
                              taskType: TaskType = TaskType.SHORT_TASK): FutureResult<R> =
    taskType.delay(delayInMilliseconds, this)

/**
 * Launch the function/lambda in parallel thread with a delay
 *
 * @param parameter Parameter give at action when its effectively executed
 * @return Future to be able to react on completion (success, failure or cancel)
 */
fun <P : Any, R : Any> ((P) -> R).delay(delayInMilliseconds: Long, parameter: P,
                                        taskType: TaskType = TaskType.SHORT_TASK): FutureResult<R> =
    taskType.delay(delayInMilliseconds, parameter, this)
