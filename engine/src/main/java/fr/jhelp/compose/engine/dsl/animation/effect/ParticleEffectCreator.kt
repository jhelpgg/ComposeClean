package fr.jhelp.compose.engine.dsl.animation.effect

import fr.jhelp.compose.engine.animation.effect.ParticleEffect

/**
 * Create a particle effect
 */
class ParticleEffectCreator
{
    private val nodes = ArrayList<ParticleNodeCreator>()

    /**
     * Add particle node source to the created particle effect
     */
    fun add(creator: ParticleNodeCreator.() -> Unit)
    {
        val particleNodeCreator = ParticleNodeCreator()
        creator(particleNodeCreator)
        this.nodes.add(particleNodeCreator)
    }

    /**
     * Add particle node source referred by particle node source reference to the created particle effect
     */
    operator fun ParticleNodeReference.unaryPlus()
    {
        this@ParticleEffectCreator.nodes.add(this.particleNodeCreator)
    }

    internal fun particleEffect(): ParticleEffect
    {
        val particleEffect = ParticleEffect()

        for (node in this.nodes)
        {
            particleEffect.addParticleNode(node.particleNode())
        }

        return particleEffect
    }
}