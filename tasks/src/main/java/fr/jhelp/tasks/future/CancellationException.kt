package fr.jhelp.tasks.future

class CancellationException(val reason: String) : Exception("Cancelled because : $reason")