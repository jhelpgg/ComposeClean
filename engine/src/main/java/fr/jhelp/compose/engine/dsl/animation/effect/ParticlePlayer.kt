package fr.jhelp.compose.engine.dsl.animation.effect

import fr.jhelp.compose.engine.animation.effect.ParticleEffect
import fr.jhelp.compose.engine.scene.Scene3D

class ParticlePlayer(private val scene3D: Scene3D,
                     private val particleEffectReference: ParticleEffectReference)
{
    private var particleEffect: ParticleEffect? = null

    fun play()
    {
        this.stop()
        this.particleEffect = this.particleEffectReference.particleEffect.particleEffect()
        this.scene3D.play(this.particleEffect!!)
    }

    fun stop()
    {
        this.particleEffect?.let { particleEffect -> this.scene3D.stop(particleEffect) }
    }
}