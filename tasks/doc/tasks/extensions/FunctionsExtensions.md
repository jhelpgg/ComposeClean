# Functions extensions

We add some extensions to functions, that is to say lambda or method reference to launch tasks in an
other thread.

Method `fr.jhelp.android.library.tasks.extensions.parallel` launch a task "immediately".

Method `fr.jhelp.android.library.tasks.extensions.delay` launch a task after a delay.

```kotlin
import fr.jhelp.android.library.tasks.extensions.parallel
import fr.jhelp.android.library.tasks.extensions.delay

/**
 * Some usage of `parallel`and `delay` samples
 */
fun sample()
{

    ({ /* Do something in an other thread */ }).parallel()

    ({ /* Do something in IO thread type */ }).parallel(TaskType.IO)

    ({ /* Do something in an other thread after 10 seconds */ }).delay(10_000L)

    ({ /* Do something in an other thread after 10 seconds in IO thread type */ }).delay(10_000L,
                                                                                         TaskType.IO)

    // Call doOperation with 42.73 as parameter in separate thread
    ::doOperation.parallel(42.73)

    // Call doOperation with 42.73 as parameter in IO thread type
    ::doOperation.parallel(42.73, TaskType.IO)

    // Call doOperation in 10 seconds with 42.73 as parameter in separate thread
    ::doOperation.delay(10_000L, 42.73)

    // Call doOperation in 10 seconds with 42.73 as parameter in IO thread type
    ::doOperation.delay(10_000L, 42.73, TaskType.IO)
}

/**
 * Do a operation
 */
fun doOperation(value: Double): Double
{
    return value * 2.0
}
```

Those methods returns
a [fr.jhelp.android.library.tasks.FutureResult](../../../src/main/java/fr/jhelp/android/library/tasks/future/FutureResult.kt)
so it possible to link when task is done or fialed. It is also possible to cancel the delay before
time expire.
See [FutureResult documentation](../future/FutureResult.md) for more explanations
~~~~
