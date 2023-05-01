# TaskType

The enumeration [fr.jhelp.tasks.TaskType](../../src/main/java/fr/jhelp/tasks/TaskType.kt)

Tasks could be launch in a different depends what they are doing.
To this `TaskType` represents the environment/context/condition where task is played.

We have created 5 type of possible `TaskType` :

* `SHORT_TASK` : The default one, dedicated to simple task that takes short time and memory.
  Tasks here no need particular resources or access.
* `HEAVY_TASK` : Dedicated for long task or heavy on memory.
  Tasks here no need particular resources or access.
* `NETWORK` : For tasks that requires a network access.
  See [NetworkStatusManager](network/NetworkStatusManager.md) for more explanation how initialize
  the system and mmek it work
    * When their network, tasks are played
    * When their no network, tasks are asleep until network comes.
* `IO` : For tasks needs file or database operations
* `MAIN` : For (and only for) tasks that manipulate the UI.
  Like change a view component color, visibility, text, ...

`TaskType` have four methods to launch tasks. Two for launch tasks "immediately", and two for launch
them later.

All methods returns a `FutureResult`  (See [FutureResult](future/FutureResult.md)) to be able to :

* Do know when its complete (succeed, failed or canceled)
* Do something else after a success
* React if task failed
* Be able to try to cancel the task.
  This part work well with delayed tasks before delay expired, to do like a timeout.
  For other case it will depends on the task itself

## Methods `lacunh`

They launch a task "immediately" (in fact as soon as possible).
One is for tasks that no need parameter.
Other for tasks with one parameter and specified the parameter to use.

## Methods `delay`

They launch a task in a given delay specified in milliseconds.
If the result `FutureResult` if canceled before the delay, the task will never be launched.
One is for tasks that no need parameter.
Other for tasks with one parameter and specified the parameter to use.
