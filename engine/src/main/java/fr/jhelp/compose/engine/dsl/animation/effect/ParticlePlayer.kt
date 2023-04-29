package fr.jhelp.compose.engine.dsl.animation.effect

import fr.jhelp.compose.engine.animation.effect.ParticleEffect
import fr.jhelp.compose.engine.scene.Scene3D

/**
 * Player of particle effect
 */
class ParticlePlayer(private val scene3D: Scene3D,
                     private val particleEffectReference: ParticleEffectReference)
{
    private var particleEffect: ParticleEffect? = null

    /**
     * Launch the particle effect
     */
    fun play()
    {
        this.stop()
        this.particleEffect = this.particleEffectReference.particleEffect.particleEffect()
        this.scene3D.play(this.particleEffect!!)
    }

    /**
     * Stop the particle effect
     */
    fun stop()
    {
        this.particleEffect?.let { particleEffect -> this.scene3D.stop(particleEffect) }
    }
}