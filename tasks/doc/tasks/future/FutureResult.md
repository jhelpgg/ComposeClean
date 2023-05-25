# Promises and futures

* [Promise](#promise) : Make a promise of a future result
* [FutureResult](#futureresult) : Follow a promised result
* [Future continuation](#future-continuation) : Link a continuation to the result
* [React to error or cancel individually](#react-to-error-or-cancel-individually)
* [Future tools](#future-tools) : Some tools for help to specific case
  * [Simplify FutureResult<FutureResult<R>>](#simplify-futureresultfutureresultr)
  * [Wait that several future complete before react](#wait-that-several-future-complete-before-react)
  * [Short cut when response already known](#short-cut-when-response-already-known)


When you do an action in n other thread, you may want to react when action succeed or failed.
Or have a way to cancel the task. This is the purpose here to give a mechanism to do it.

A task is called complete when its finished for any reason, that is to say, when it's succeed,
failed or canceled.

When a task is played in a separate thread, it made a promise that it will have a result in the
future.

The object [fr.jhelp.tasks.future.Promise](../../../src/main/java/fr/jhelp/tasks/future/Promise.kt)
represents the promise.
And the
object [fr.jhelp.tasks.future.FutureResult](../../../src/main/java/fr/jhelp/tasks/future/FutureResult.kt)
the result in the future.

## Promise

Usually the task have an instance of `Promise` in private and share the `FutureResult` associated.
That is to say something like :

```kotlin
import fr.jhelp.tasks.future.Promise
import fr.jhelp.tasks.future.FutureResult

// ...

class ExampleTask
{
    private val promise = Promise<Value>()
    val result: FutureResult<Value> = this.promise.future

    // ....

    private fun doTheTask()
    {
        // ...

        // If task failed
        this.promise.error(exception)

        // ...

        // When task succeed
        this.promise.result(value)
    }
}
```

Has you see in the code, to report when the result is known, we use the `result` method.
And to report an error, the `error` method.

When `result` or `error` are called the task is consider has complete, so the status can't be
changed.
In consequence, call one of them after have no effect. To report an other result, have to use an
other instance of `Promise`

A task can be tried to be canceled throw the instance of `FutureResult` shared.
To react to cancel request, we provided two possible ways (you can use both without issue).

First possibility is to register a cancel listener with `onCancel` method

```kotlin
import fr.jhelp.tasks.future.Promise
import fr.jhelp.tasks.future.FutureResult

// ...

class ExampleTask
{
    private val promise = Promise<Value>()
    val result: FutureResult<Value> = this.promise.future

    init
    {
        this.promise.onCancel(this::cancelReaction)
    }

    // ....

    private fun doTheTask()
    {
        // ...

        // If task failed
        this.promise.error(exception)

        // ...

        // When task succeed
        this.promise.result(value)
    }

    private fun cancelReaction(reason: String)
    {
        // ...
    }
}
```

The cancel listener lambda receive a `String` on parameter.
It's the reason given by the canceler why it tries to cancel.
In some advanced usage it may be use-full to have different reaction depends on the reason.

The other way is to use the field `canceled`, it can be use-full in a loop to stop it :

```kotlin
import fr.jhelp.tasks.future.Promise
import fr.jhelp.tasks.future.FutureResult

// ...

class ExampleTask
{
    private val promise = Promise<Value>()
    val result: FutureResult<Value> = this.promise.future

    // ....

    private fun doTheTask()
    {
        // ...

        while (!this.promise.canceled && !finished)
        {
            // ...

            if (failed)
            {
                this.promise.error(exception)
                return
            }
        }

        if (this.promise.canceled)
        {
            return
        }


        // Task succeed
        this.promise.result(value)
    }
}
```

By example.

## FutureResult

The `FutureResult` permits to react when task complete.

It have a status with `status()` method.
List of status is in the
enumeration [fr.jhelp.tasks.future.FutureResultStatus](../../../src/main/java/fr/jhelp/tasks/future/FutureResultStatus.kt)

By nature the `COMPUTING` status can change at any moment.
It is discouraged to loop on this status for wait the completion.
Their other methods for react properly.

To register with a classic listener, use `register` method.
The listener will callback when future complete in the specified `TaskType` (
See [TaskType](../TaskType.md)).
If the future is already complete, the listner is launched immediately.

The `invoke` method will block the thread that invoke it until completing.
No blocking if already complete.

* If the future succeed, the result is returned.
* If the future failed an exception is throws
* If the future is canceled a `CancellationException` is throws

Since this method is a special operator in `Kotlin` it can be short cut.

````kotlin
try
{
    // Bellow line code will block the current thread until future completion
    val result = future()
    // Do something with the result
}
catch (cancellationException: CancellationException)
{
    val cancelReason = cancellationException.reason
    // React to cancellation
}
catch (exception: Exception)
{
    // React to failure
}
````

The method `waitComplete` block current thread until the future complete.
If already complete, it not blocks.
It returns the final status, so succeed, failed or cancleed.
Since future is complete, it will not be computing.

The method `error` returns an exception if future is complete and failed.
If the future still computing or complete because of success or failure, `null`is returned.

The method `cancelReason` give the cancelation reason if future complete by cancellation.
If the future still compute or complete because of success or failure it returns `null`

The method `cancel` tries to cancel the task. It does nothing if the future already complete

## Future continuation

It's possible to program an operation to do after the future completion.
Those methods returns a future so that it can plan a chain of reaction.
The continuation is he task do do after.

`and` method program a task to do after a success.
The continuation is not called if the future failed or canceled.
The returned future represents the combination of this future follow by the given task.
The result future will :

* Succeed, if the future and continuation succeed
* Failed, if the future or the continuation fail
* Canceled if the future or the created future is canceled.

By example :

```kotlin
import fr.jhelp.tasks.future.FutureResult

fun add(more: Double, numberInStringFuture: FutureResult<String>): FutureResult<String>
{
    return numberInStringFuture
        .and { string -> string.toDouble() }
        .and { value -> more + value }
        .and { result -> result.toString() }
}
```

`andIf` method program a task to to after a success and the result respect a condition.
If the condition not fulfill, the future returns by the method will fail.
The other behavior are like `and` method.

`then` method program a task when the future complete.
The continuation will be called for any completion state.
The returned future represents the combination of this future follow by the given task.
The result future will :

* Succeed, if the continuation succeed
* Failed, if the continuation fail
* Canceled if the future or the created future is canceled.

````kotlin
import fr.jhelp.tasks.future.FutureResult
import fr.jhelp.tasks.future.FutureResultStatus

fun <R:Any> something(future: FutureResult<R>): FutureResult<Unit>
{
    return future
        .then { futureR ->
            when(futureR.status())
            {
                FutureResultStatus.SUCCEED ->
                {
                    val result = futureR()
                    // Do something with the result
                }
                
                FutureResultStatus.FAILED ->
                {
                    val exception = futureR.error()!!
                    // Do something with the exception
                }
                
                FutureResultStatus.CANCELED ->
                {
                    val reason = futureR.cancelReason()!!
                    // Do something with the reason
                }

                // Never happen                
                else -> Unit
            }
        }
}
````

Sometimes continuation returns a future of something.
To avoid to have a future of future of something, each previous methods have an `unwrap` version.
They work the same way :
* `andUnwrap` equivalent to `and` for continuations returns a future
* `andIfUnwrap` equivalent to `andIf` for continuations returns a future
* `thenUnwrap` equivalent to `then` for continuations returns a future

## React to error or cancel individually

It is possible to register listener for error or cancel reaction.

`onError` register a listener called if future failed.
It returns the future itself to be able to chaining.

`onCancel` register a listener called if future canceled.
It returns the future itself to be able to chaining.

## Future tools

* [Simplify FutureResult<FutureResult<R>>](#simplify-futureresultfutureresultr)
* [Wait that several future complete before react](#wait-that-several-future-complete-before-react)

### Simplify FutureResult<FutureResult<R>>

Sometimes we receive a future of future of something. 
It is possible to reduce it to future of something.
To do it, just use the `unwrap` extension

```kotlin
import fr.jhelp.tasks.future.unwrap
import fr.jhelp.tasks.future.FutureResult

fun <R:Any> receive(futureOfFuture:FutureResult<FutureResult<R>>)
{
    val future : FutureResult<R> = futureOfFuture.unwrap()
}

```

### Wait that several future complete before react

Sometimes it is useful to be able to wait that several futures are complete before do a reaction.
For this use the `join` extension.

```kotlin
import fr.jhelp.tasks.future.join
import fr.jhelp.tasks.future.FutureResult

fun average(futures: List<FutureResult<Double>>): FutureResult<Double>
{
    return futures.join().and { computeAverage(futures) }
}

private fun computeAverage(list: List<FutureResult<Double>>): Double
{
    var result = 0.0

    for (future in list)
    {
        result += future()
    }

    return result / list.size
}
```

### Short cut when response already known

Sometimes when want return a future it exists case that we don't need to compute something because:
* The result is obvious 
* It is obviously an error

`future` extension transform a value to a future succeed with the result

`futureError` extension transform an exception on future failed with the exception

```kotlin
import fr.jhelp.tasks.future.FutureResult
import fr.jhelp.tasks.future.future
import fr.jhelp.tasks.future.futureError

fun multiply(futureResult: FutureResult<Double>, number: Double): FutureResult<Double>
{
  if (number == 0.0)
  {
    return 0.0.future()
  }

  return futureResult.and { result -> result * number }
}


fun divide(futureResult: FutureResult<Double>, number: Double): FutureResult<Double>
{
  if (number == 0.0)
  {
    return ArithmeticException("Divide by zero").futureError()
  }

  return futureResult.and { result -> result / number }
}
```
