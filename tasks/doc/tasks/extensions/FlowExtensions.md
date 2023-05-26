# Flow extensions

We add some extensions on `kotlinx.coroutines.flow.Flow`

`fr.jhelp.tasks.extensions.observedBy` play an action each time flow emits a value.
It returns a future to be able to cancel/stop the listening.

`fr.jhelp.tasks.extensions.then` Like `observedBy play an action each time flow emits`, but this
time,
it returns a flow.

`fr.jhelp.tasks.extensions.doWhen` listen the flow emission. And the first time when a given
condition fulfill,
an action is played. The action is played only one time.
The returned future is a way to cancel litening and not do the action before the condition met.
