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

    /** Buffer current size */
    var size: Int = 0
        private set

    /** Indicates if buffer is sealed ot not */
    val sealed: Boolean get() = synchronized(this.dirty) { this.array == null }

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

    /**
     * Add some floats to the buffer
     *
     * Does nothing if buffer is sealed
     */
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

    /**
     * Make th buffer empty
     *
     * Does nothing if buffer is sealed
     */
    fun clear()
    {
        synchronized(this.dirty)
        {
            if (this.array == null)
            {
                return
            }

            this.size = 0
            this.dirty.set(true)
        }
    }

    /**
     * Convert the buffer to NIO buffer, so that can be send to OpenGL
     */
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

    /**
     * Seal the buffer
     */
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