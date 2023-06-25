/*
 *  <h1>License :</h1> <br/>
 * The following code is deliver as is. <br/>
 *  You can use, modify, the code as your need for any usage.<br/>
 *  But you can't do any action that avoid me or other person use, modify this code.<br/>
 *  The code is free for usage and modification, you can't change that fact.
 */

package fr.jhelp.android.library.sound

import androidx.annotation.RawRes
import fr.jhelp.compose.math.extensions.bounds

/**
 * Sound can change volume at any time
 *
 * @param loop Number of loop when play. Can use -1 for "infinite". Number play time is `loop + 1`
 */
class Sound(@RawRes internal val resource: Int, leftVolume: Float = 1f, rightVolume: Float = 1f,
            val loop: Int = 0)
{
    /**Current left volume*/
    var leftVolume: Float = leftVolume.bounds(0f, 1f)
        private set

    /**Current right volume*/
    var rightVolume: Float = rightVolume.bounds(0f, 1f)
        private set
    internal var soundId = -1
    internal var streamId = -1

    /**
     * Play the sound
     *
     * If an other volume control sound playing, it stops and the this one starts
     */
    fun play()
    {
        SoundManager.sound(this)
    }

    /**
     * Pause the sound
     */
    fun pause()
    {
        SoundManager.pause(this)
    }

    /**
     * Resume the sound
     */
    fun resume()
    {
        SoundManager.resume(this)
    }

    /**
     * Stop the sound
     */
    fun stop()
    {
        SoundManager.stop(this)
    }

    /**
     * Change the volume
     */
    fun setVolume(leftVolume: Float, rightVolume: Float = leftVolume)
    {
        this.leftVolume = leftVolume.bounds(0f, 1f)
        this.rightVolume = rightVolume.bounds(0f, 1f)
        SoundManager.changeVolume(this)
    }
}