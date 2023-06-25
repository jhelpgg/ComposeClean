package fr.jhelp.android.library.provider

import kotlin.reflect.KProperty

/**
 * Reference to a provided element
 */
@Suppress("UNCHECKED_CAST")
class Provided<T>(private val identifier: String)
{
    /**
     * Obtain the provided value
     */
    operator fun getValue(thisRef: Any, property: KProperty<*>): T =
            ProviderManager.provided(this.identifier) as T
}
