package fr.jhelp.android.library.provider

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
    fun <T : Any> provide(producer: () -> T, kClass: KClass<T>, qualifier: String, single: Boolean)
    {
        this.providedProducers["${kClass.qualifiedName}:$qualifier"] = Producer(single, producer)
    }

    /**
     * Forget a previous provided instance
     */
    @Synchronized
    fun <T : Any> forget(kClass: KClass<T>, qualifier: String)
    {
        this.providedProducers.remove("${kClass.qualifiedName}:$qualifier")
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