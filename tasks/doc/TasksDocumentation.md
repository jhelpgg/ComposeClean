# Tasks

Set of tools and utilities for simplify thread manipulation

* [ThreadSafeQueue](tasks/ThreadSafeQueue.md) Queue where access in thread safe
* [TaskType](tasks/TaskType.md) Type of task in parallel
* [Mutex](tasks/Mutex.md) Mutual exclusion
* [Promises and futures](tasks/future/FutureResult.md) Do task in separate thread and react to completion 
  * [Promise](tasks/future/FutureResult.md#promise) : Make a promise of a future result
  * [FutureResult](tasks/future/FutureResult.md#futureresult) : Follow a promised result
  * [Future continuation](tasks/future/FutureResult.md#future-continuation) : Link a continuation to the result
  * [React to error or cancel individually](tasks/future/FutureResult.md#react-to-error-or-cancel-individually)
  * [Future tools](tasks/future/FutureResult.md#future-tools) : Some tools for help to specific case
      * [Simplify FutureResult<FutureResult<R>>](tasks/future/FutureResult.md#simplify-futureresultfutureresultr)
      * [Wait that several future complete before react](tasks/future/FutureResult.md#wait-that-several-future-complete-before-react)
      * [Short cut when response already known](tasks/future/FutureResult.md#short-cut-when-response-already-known)
* [Flow extensions](tasks/extensions/FlowExtensions.md) Some coroutines' flow extensions.


