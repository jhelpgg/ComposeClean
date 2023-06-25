package fr.jhelp.android.library.engine.dsl.animation.effect

import fr.jhelp.android.library.engine.animation.effect.ParticleEffect
import fr.jhelp.android.library.engine.scene.Scene3D

/**
 * Player of particle effect
 */
class ParticlePlayer(private val scene3D: Scene3D,
                     private val particleEffectReference: ParticleEffectReference)
{
    private var particleEffect: ParticleEffect? =
        null

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