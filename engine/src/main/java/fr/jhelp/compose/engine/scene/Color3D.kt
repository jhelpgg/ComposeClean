package fr.jhelp.compose.engine.scene

import androidx.core.graphics.alpha
import androidx.core.graphics.blue
import androidx.core.graphics.green
import androidx.core.graphics.red
import fr.jhelp.compose.engine.buffer.floatBuffer
import java.nio.FloatBuffer

/**
 * Color used for 3D
 *
 * Each part are in [0, 1]
 */
data class Color3D(val red: Float, val green: Float, val blue: Float, val alpha: Float = 1f)
{
    private val floatBuffer = floatBuffer(4)

    /**
     * Grey color
     */
    constructor(grey: Float, alpha: Float = 1f) : this(grey, grey, grey, alpha)

    /**
     * Color from ARGB Int version
     */
    constructor(color: Int) : this(color.red / 255f, color.green / 255f, color.blue / 255f,
                                   color.alpha / 255f)

    init
    {
        this.floatBuffer.put(this.red)
        this.floatBuffer.put(this.green)
        this.floatBuffer.put(this.blue)
        this.floatBuffer.put(this.alpha)
    }

    fun floatBuffer(): FloatBuffer
    {
        this.floatBuffer.rewind()
        return this.floatBuffer
    }
}
