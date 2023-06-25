/*
 *  <h1>License :</h1> <br/>
 * The following code is deliver as is. <br/>
 *  You can use, modify, the code as your need for any usage.<br/>
 *  But you can't do any action that avoid me or other person use, modify this code.<br/>
 *  The code is free for usage and modification, you can't change that fact.
 */

package fr.jhelp.android.library.sound

import android.content.Context
import android.media.AudioAttributes
import android.media.SoundPool
import android.util.SparseIntArray
import androidx.annotation.RawRes
import fr.jhelp.compose.provider.provided
import fr.jhelp.android.library.sound.SoundManager.background
import fr.jhelp.android.library.sound.SoundManager.effect
import fr.jhelp.android.library.sound.SoundManager.pause
import fr.jhelp.android.library.sound.SoundManager.resume
import fr.jhelp.android.library.sound.SoundManager.sound
import fr.jhelp.android.library.sound.SoundManager.stopSounds
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

/**
 * Manage 3 types of sound :
 * * Sound play in background on loop
 * * Sound play once like effect in video game or alert in application
 * * Sound can control volume at any time
 *
 * Call [pause] on [android.app.Activity.onPause] method to pause sound while
 * application/game pause, and [resume] on [android.app.Activity.onResume] method to resume sound.
 *
 * Its recommended to call [stopSounds] when no sounds are need, so in [android.app.Activity.onDestroy]
 * is a good place
 *
 * It supposes that the application context is provided by [fr.jhelp.compose.provider.provideSingle] or [fr.jhelp.compose.provider.provideMultiple] before call [sound], [background] or [effect]
 */
object SoundManager
{
    private val context by provided<Context>()
    private val soundPool =
        SoundPool.Builder()
            .setMaxStreams(3)
            .setAudioAttributes(AudioAttributes.Builder()
                                    .setUsage(AudioAttributes.USAGE_GAME)
                                    .setContentType(AudioAttributes.CONTENT_TYPE_MUSIC)
                                    .build())
            .build()

    private var effectSound = -1
    private var backgroundSound = -1
    private var currentSound: Sound? = null
    private val loadedSounds = SparseIntArray()
    private val scope = CoroutineScope(SupervisorJob() + Dispatchers.Default)


    /**
     * Start a background sound in loop.
     *
     * If a background sound playing, it stops and the given one starts
     *
     * It supposes that the application context is provided by [fr.jhelp.compose.provider.provideSingle] or [fr.jhelp.compose.provider.provideMultiple] before call it
     */
    fun background(@RawRes soundResource: Int)
    {
        this.play(soundResource, true)
    }

    /**
     * Play a sound one time
     *
     * If an effect sound playing, it stops and the given one starts
     *
     * It supposes that the application context is provided by [fr.jhelp.compose.provider.provideSingle] or [fr.jhelp.compose.provider.provideMultiple] before call it
     */
    fun effect(@RawRes soundResource: Int)
    {
        this.play(soundResource, false)
    }

    /**
     * Play a volume control sound
     *
     * If a volume control sound playing, it stops and the given one starts
     *
     * It supposes that the application context is provided by [fr.jhelp.compose.provider.provideSingle] or [fr.jhelp.compose.provider.provideMultiple] before call it
     */
    fun sound(sound: Sound)
    {
        this.currentSound?.let { current ->
            if (current.streamId >= 0)
            {
                this.soundPool.stop(current.streamId)
                sound.streamId = -1
            }
        }

        if (sound.soundId < 0)
        {
            sound.soundId =
                synchronized(this.loadedSounds)
                {
                    this.loadedSounds[sound.resource, -1]
                }


            if (sound.soundId < 0)
            {
                sound.soundId = this.soundPool.load(this.context, sound.resource, 1)
            }

            synchronized(this.loadedSounds)
            {
                this.loadedSounds.put(sound.resource, sound.soundId)
            }

            this.scope.launch {
                delay(1024)
                this@SoundManager.playSound(sound)
            }
        }
        else
        {
            this.playSound(sound)
        }
    }

    /**
     * Pause a volume control sound
     */
    fun pause(sound: Sound)
    {
        if (sound == this.currentSound && sound.streamId >= 0)
        {
            this.soundPool.pause(sound.streamId)
        }
    }

    /**
     * Resume a volume control sound
     */
    fun resume(sound: Sound)
    {
        if (sound == this.currentSound && sound.streamId >= 0)
        {
            this.soundPool.resume(sound.streamId)
        }
    }

    /**
     * Stop a volume control sound
     */
    fun stop(sound: Sound)
    {
        if (sound == this.currentSound && sound.streamId >= 0)
        {
            this.soundPool.stop(sound.streamId)
            sound.streamId = -1
        }
    }

    internal fun changeVolume(sound: Sound)
    {
        if (this.currentSound == sound && sound.streamId >= 0)
        {
            this.soundPool.setVolume(sound.streamId, sound.leftVolume, sound.rightVolume)
        }
    }

    private fun playSound(sound: Sound)
    {
        this.currentSound = sound
        sound.streamId =
            this.soundPool.play(sound.soundId, sound.leftVolume, sound.rightVolume, 1, sound.loop,
                                1f)
    }

    /**
     * It supposes that the application context is provided by [fr.jhelp.compose.provider.provideSingle] or [fr.jhelp.compose.provider.provideMultiple] before call it
     */
    private fun play(@RawRes soundResource: Int, background: Boolean)
    {
        var soundId =
            synchronized(this.loadedSounds)
            {
                this.loadedSounds[soundResource, -1]
            }

        if (soundId > 0)
        {
            if (background)
            {
                this.playBackground(soundId)
            }
            else
            {
                this.playEffect(soundId)
            }
        }
        else
        {
            soundId = this.soundPool.load(this.context, soundResource, 1)

            synchronized(this.loadedSounds)
            {
                this.loadedSounds.put(soundResource, soundId)
            }

            if (background)
            {
                this.scope.launch {
                    delay(1024)
                    this@SoundManager.playBackground(soundId)
                }
            }
            else
            {
                this.scope.launch {
                    delay(1024)
                    this@SoundManager.playEffect(soundId)
                }
            }
        }
    }

    /**
     * Pause all playing sounds
     */
    fun pause()
    {
        if (this.backgroundSound >= 0)
        {
            this.soundPool.pause(this.backgroundSound)
        }

        if (this.effectSound >= 0)
        {
            this.soundPool.stop(this.effectSound)
        }

        this.currentSound?.let { current -> this.soundPool.pause(current.streamId) }
    }

    /**
     * Resume playing sounds
     */
    fun resume()
    {
        if (this.backgroundSound >= 0)
        {
            this.soundPool.resume(this.backgroundSound)
        }

        this.currentSound?.let { current -> this.soundPool.resume(current.streamId) }
    }

    /**
     * Stop all sounds
     */
    fun stopSounds()
    {
        if (this.backgroundSound >= 0)
        {
            this.soundPool.stop(this.backgroundSound)
            this.backgroundSound = -1
        }

        if (this.effectSound > 0)
        {
            this.soundPool.stop(this.effectSound)
            this.effectSound = -1
        }

        this.currentSound?.let { current ->
            if (current.streamId >= 0)
            {
                this.soundPool.stop(current.streamId)
            }

            current.soundId = -1
            current.streamId = -1
        }

        synchronized(this.loadedSounds)
        {
            for (index in 0 until this.loadedSounds.size())
            {
                this.soundPool.unload(this.loadedSounds.valueAt(index))
            }

            this.loadedSounds.clear()
        }
    }

    private fun playBackground(soundId: Int)
    {
        if (this.backgroundSound >= 0)
        {
            this.soundPool.stop(this.backgroundSound)
        }

        this.backgroundSound = this.soundPool.play(soundId, 1f, 1f, 1, -1, 1f)
    }

    private fun playEffect(soundId: Int)
    {
        if (this.effectSound > 0)
        {
            this.soundPool.stop(this.effectSound)
        }

        this.effectSound = this.soundPool.play(soundId, 1f, 1f, 1, 0, 1f)
    }
}