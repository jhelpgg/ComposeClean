package fr.jhelp.tasks.future

/**
 * Exception when future is cancelled
 * @property reason Cancellation reason
 */
class CancellationException(val reason: String) : Exception("Cancelled because : $reason")