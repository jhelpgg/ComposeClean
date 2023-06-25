package fr.jhelp.android.library.engine.dsl.animation.player

import fr.jhelp.android.library.animations.Animation
import fr.jhelp.android.library.engine.scene.Scene3D

/**
 * Generic animation player
 */
sealed class AnimationPlayer(private val scene3D: Scene3D)
{
    /**
     * Launch the animation
     */
    fun play()
    {
        this.scene3D.play(this.animation())
    }

    /**
     * Stop the animation
     */
    fun stop()
    {
        this.scene3D.stop(this.animation())
    }

    /**
     * Create the animation to play
     */
    protected abstract fun animation(): Animation
}