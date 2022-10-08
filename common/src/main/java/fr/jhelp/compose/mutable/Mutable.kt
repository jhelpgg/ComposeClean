package fr.jhelp.compose.mutable

class Mutable<T : Any>(val get: () -> T, val set: (T) -> Unit)

