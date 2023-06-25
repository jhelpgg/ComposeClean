package fr.jhelp.android.library.provider

import java.util.concurrent.atomic.AtomicBoolean

internal class Producer<T : Any>(private val single: Boolean, private val producer: () -> T)
{
    private val created = AtomicBoolean(false)
    private lateinit var value: T

    fun value() =
            if (this.single)
            {
                if (!this.created.getAndSet(true))
                {
                    this.value = this.producer()
                }

                this.value
            }
            else
            {
                this.producer()
            }
}
