package fr.jhelp.compose.provider

import kotlin.reflect.KClass

object ProviderManager {
    private val providedProducers = HashMap<String, Producer<*>>()

    @Synchronized
    fun <T : Any> provide(producer: () -> T, kclass: KClass<T>, qualifier: String, single: Boolean) {
        this.providedProducers["${kclass.qualifiedName}:$qualifier"] = Producer(single, producer)
    }

    @Synchronized
    fun <T : Any> forget(kclass: KClass<T>, qualifier: String) {
        this.providedProducers.remove("${kclass.qualifiedName}:$qualifier")
    }

    @Synchronized
    internal fun provided(qualifier: String) =
            try {
                this.providedProducers.getValue(qualifier)
                        .value()
            } catch (exception: Exception) {
                throw IllegalArgumentException(
                        "No definition for $qualifier, call 'provideSingle' or 'provideMultiple' before use the instance",
                        exception)
            }

    @Synchronized
    fun isProvided(qualifier: String) = qualifier in this.providedProducers
}