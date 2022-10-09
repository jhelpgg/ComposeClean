package fr.jhelp.compose.mutable

/**
 * Mutable value
 * @property get Value getter
 * @property set Value modifier
 */
class Mutable<T : Any>(val get: () -> T, val set: (T) -> Unit)

