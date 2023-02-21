package fr.jhelp.compose.engine.dsl.animation.effect

import fr.jhelp.compose.engine.animation.effect.ParticleEffect

class ParticleEffectCreator
{
    private val nodes = ArrayList<ParticleNodeCreator>()

    fun add(creator: ParticleNodeCreator.() -> Unit)
    {
        val particleNodeCreator = ParticleNodeCreator()
        creator(particleNodeCreator)
        this.nodes.add(particleNodeCreator)
    }

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