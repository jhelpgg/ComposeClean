package fr.jhelp.compose.provider

import kotlin.reflect.KClass

/**
 * Manager of provided instances
 */
object ProviderManager
{
    private val providedProducers = HashMap<String, Producer<*>>()

    /**
     * Register a provided instance
     */
    @Synchronized
    fun <T : Any> provide(producer: () -> T, kclass: KClass<T>, qualifier: String, single: Boolean)
    {
        this.providedProducers["${kclass.qualifiedName}:$qualifier"] = Producer(single, producer)
    }

    /**
     * Forget a previous provided instance
     */
    @Synchronized
    fun <T : Any> forget(kclass: KClass<T>, qualifier: String)
    {
        this.providedProducers.remove("${kclass.qualifiedName}:$qualifier")
    }

    @Synchronized
    internal fun provided(qualifier: String) =
        try
        {
            this.providedProducers.getValue(qualifier)
                .value()
        }
        catch (exception: Exception)
        {
            throw IllegalArgumentException(
                "No definition for $qualifier, call 'provideSingle' or 'provideMultiple' before use the instance",
                exception)
        }

    /**
     * Indicated if an instance is provided
     */
    @Synchronized
    fun isProvided(qualifier: String): Boolean = qualifier in this.providedProducers
}