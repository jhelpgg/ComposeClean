package fr.jhelp.compose.provider

import kotlin.reflect.KProperty

@Suppress("UNCHECKED_CAST")
class Provided<T>(private val identifier: String)
{
    operator fun getValue(thisRef: Any, property: KProperty<*>): T =
            ProviderManager.provided(this.identifier) as T
}
