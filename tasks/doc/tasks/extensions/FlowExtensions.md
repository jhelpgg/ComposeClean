# Flow extensions

We add some extensions on `kotlinx.coroutines.flow.Flow`

`fr.jhelp.android.library.tasks.observedBy` play an action each time flow emits a value.
It returns a future to be able to cancel/stop the listening.

`fr.jhelp.android.library.tasks.then` Like `observedBy play an action each time flow emits`, but this
time,
it returns a flow.

`fr.jhelp.android.library.tasks.doWhen` listen the flow emission. And the first time when a given
condition fulfill,
an action is played. The action is played only one time.
The returned future is a way to cancel listening and not do the action before the condition met.
