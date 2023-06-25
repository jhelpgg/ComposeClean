package fr.jhelp.composeclean.models.implementation

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import fr.jhelp.compose.math.extensions.bounds
import fr.jhelp.android.library.sound.Sound
import fr.jhelp.android.library.sound.SoundManager
import fr.jhelp.composeclean.models.shared.SoundManagerExampleModel

internal class SoundManagerExampleModelImplementation : SoundManagerExampleModel
{
    private val controlledSoundLeftVolumeMutable = mutableStateOf<Float>(1f)
    private val controlledSoundRightVolumeMutable = mutableStateOf<Float>(1f)
    private var controlledSound: Sound? = null

    override val controlledSoundLeftVolume: State<Float> = this.controlledSoundLeftVolumeMutable
    override val controlledSoundRightVolume: State<Float> = this.controlledSoundRightVolumeMutable

    override fun backgroundSound(soundResource: Int)
    {
        SoundManager.background(soundResource)
    }

    override fun effectSound(soundResource: Int)
    {
        SoundManager.effect(soundResource)
    }

    override fun controlledSound(soundResource: Int)
    {
        this.controlledSound = Sound(soundResource, 1f, 1f, -1)
        this.controlledSoundLeftVolumeMutable.value = 1f
        this.controlledSoundRightVolumeMutable.value = 1f
    }

    override fun playControlledSound()
    {
        SoundManager.stopSounds()
        this.controlledSound?.play()
    }

    override fun pauseControlledSound()
    {
        this.controlledSound?.pause()
    }

    override fun resumeControlledSound()
    {
        this.controlledSound?.resume()
    }

    override fun stopControlledSound()
    {
        this.controlledSound?.stop()
    }

    override fun leftControlledSoundVolume(volume: Float)
    {
        this.controlledSound?.setVolume(volume, this.controlledSoundRightVolumeMutable.value)
        this.controlledSoundLeftVolumeMutable.value = volume.bounds(0f, 1f)
    }

    override fun rightControlledSoundVolume(volume: Float)
    {
        this.controlledSound?.setVolume(this.controlledSoundLeftVolumeMutable.value, volume)
        this.controlledSoundRightVolumeMutable.value = volume.bounds(0f, 1f)
    }
}