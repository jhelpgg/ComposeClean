package fr.jhelp.compose.engine.buffer

import java.nio.FloatBuffer
import java.util.concurrent.atomic.AtomicBoolean

/**
 * Dynamic sized Float array associated to a pending Float buffer.
 *
 * To save memory it can be sealed. But can't change once it's sealed
 */
class BufferFloat
{
    private var array: FloatArray? = FloatArray(128)
    private val dirty = AtomicBoolean(true)
    private lateinit var floatBuffer: FloatBuffer
    var size = 0
        private set

    val sealed get() = synchronized(this.dirty) { this.array == null }

    private fun expand(more: Int)
    {
        val array = this.array ?: return

        if (this.size + more >= array.size)
        {
            var newSize = this.size + more
            newSize += newSize shr 3
            val buffer = FloatArray(newSize)
            System.arraycopy(array, 0, buffer, 0, this.size)
            this.array = buffer
        }
    }

    fun add(vararg floats: Float)
    {
        synchronized(this.dirty)
        {
            this.expand(floats.size)
            val array = this.array ?: return
            this.dirty.set(true)

            System.arraycopy(floats, 0, array, this.size, floats.size)
            this.size += floats.size
        }
    }

    fun buffer(): FloatBuffer
    {
        synchronized(this.dirty)
        {
            if (this.dirty.getAndSet(false))
            {
                this.floatBuffer = floatBuffer(this.array!!, 0, this.size)
            }

            return this.floatBuffer
        }
    }

    fun seal()
    {
        synchronized(this.dirty)
        {
            if (this.dirty.getAndSet(false))
            {
                this.floatBuffer = floatBuffer(this.array!!, 0, this.size)
            }

            this.array = null
        }
    }
}