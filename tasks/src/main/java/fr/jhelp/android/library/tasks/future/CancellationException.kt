package fr.jhelp.android.library.tasks.future

/**
 * Exception when future is cancelled
 * @property reason Cancellation reason
 */
class CancellationException(val reason: String) : Exception("Cancelled because : $reason")