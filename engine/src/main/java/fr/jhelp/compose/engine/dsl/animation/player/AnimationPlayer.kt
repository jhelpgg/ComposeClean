package fr.jhelp.compose.engine.dsl.animation.player

import fr.jhelp.compose.animations.Animation
import fr.jhelp.compose.engine.scene.Scene3D

sealed class AnimationPlayer(private val scene3D: Scene3D)
{
    fun play()
    {
        this.scene3D.play(this.animation())
    }

    fun stop()
    {
        this.scene3D.stop(this.animation())
    }

    protected abstract fun animation(): Animation
}