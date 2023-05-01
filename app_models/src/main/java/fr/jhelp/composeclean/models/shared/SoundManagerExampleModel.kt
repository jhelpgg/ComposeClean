package fr.jhelp.composeclean.models.shared

import androidx.annotation.RawRes
import androidx.compose.runtime.State

/**
 * Example of sound manager model
 */
interface SoundManagerExampleModel
{
    /** Actual left volume of controlled sound */
    val controlledSoundLeftVolume: State<Float>

    /** Actual right volume of controlled sound */
    val controlledSoundRightVolume: State<Float>

    /**
     * Start a background sound
     */
    fun backgroundSound(@RawRes soundResource: Int)

    /**
     * Launch a sound effect
     */
    fun effectSound(@RawRes soundResource: Int)

    /**
     * Defines/change the controlled sound
     */
    fun controlledSound(@RawRes soundResource: Int)

    /**
     * Play the controlled sound
     */
    fun playControlledSound()

    /**
     * Pause the controlled sound
     */
    fun pauseControlledSound()

    /**
     * Resume the controlled sound
     */
    fun resumeControlledSound()

    /**
     * Stop the controlled sound
     */
    fun stopControlledSound()

    /**
     * Change controlled sound left volume (in [0, 1])
     */
    fun leftControlledSoundVolume(volume: Float)

    /**
     * Change controlled sound right volume (in [0, 1])
     */
    fun rightControlledSoundVolume(volume: Float)
}