/*
 *  <h1>License :</h1> <br/>
 * The following code is deliver as is. <br/>
 *  You can use, modify, the code as your need for any usage.<br/>
 *  But you can't do any action that avoid me or other person use, modify this code.<br/>
 *  The code is free for usage and modification, you can't change that fact.
 */

/*
 *  <h1>License :</h1> <br/>
 * The following code is deliver as is. <br/>
 *  You can use, modify, the code as your need for any usage.<br/>
 *  But you can't do any action that avoid me or other person use, modify this code.<br/>
 *  The code is free for usage and modification, you can't change that fact.
 */

package fr.jhelp.compose.engine.scene

import androidx.annotation.RawRes
import fr.jhelp.compose.sound.Sound
import kotlin.math.abs
import kotlin.math.max
import kotlin.math.sqrt

class Sound3D(@RawRes soundResource: Int, x: Float = 0f, y: Float = 0f, z: Float = 1f,
              loop: Int = 0)
{
    var x = x
        private set
    var y = y
        private set
    var z = z
        private set

    private val sound = Sound(soundResource, 1f, 1f, loop)

    init
    {
        this.updateVolume()
    }

    fun position(x: Float, y: Float, z: Float)
    {
        this.x = x
        this.y = y
        this.z = z
        this.updateVolume()
    }

    fun translate(x: Float, y: Float, z: Float)
    {
        this.x += x
        this.y += y
        this.z += z
        this.updateVolume()
    }

    fun play()
    {
        this.sound.play()
    }

    fun pause()
    {
        this.sound.pause()
    }

    fun resume()
    {
        this.sound.resume()
    }

    fun stop()
    {
        this.sound.stop()
    }

    private fun updateVolume()
    {
        val zz = max(1f, abs(this.z))
        var left = 1f / sqrt(this.x * this.x + this.y * this.y + zz * zz)
        var right = left

        if (this.x >= 0)
        {
            left = max(0f, (1f - 0.5f * this.x) * left)
        }
        else
        {
            right = max(0f, (1f + 0.5f * this.x) * right)
        }

        this.sound.setVolume(left, right)
    }
}