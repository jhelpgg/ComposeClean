package fr.jhelp.compose.engine.animation.effect

import android.os.SystemClock
import fr.jhelp.compose.engine.annotations.OpenGLThread
import javax.microedition.khronos.opengles.GL10

/**
 * Particle effect is composed of one or several [ParticleNode] that needs play on same particle effect animation
 *
 * Add [ParticleNode] with [addParticleNode] to create one effect
 *
 * To play the effect use [fr.jhelp.compose.engine.scene.Scene3D.play] each time the effect needs to be played
 */
class ParticleEffect
{
    private val particleNodes = ArrayList<ParticleNode>()
    private val aliveParticles = ArrayList<Particle>()
    private var statTime: Long = 0L

    /**
     * Add a particle node generation in the particle effect
     */
    fun addParticleNode(particleNode: ParticleNode)
    {
        synchronized(this.particleNodes)
        {
            this.particleNodes.add(particleNode)
        }
    }

    internal fun start()
    {
        synchronized(this.aliveParticles)
        {
            this.aliveParticles.clear()
        }

        synchronized(this.particleNodes)
        {
            for (particleNode in this.particleNodes)
            {
                particleNode.resetEmission()
            }
        }

        this.statTime = SystemClock.uptimeMillis()
        this.update()
    }

    internal fun stop()
    {
        synchronized(this.aliveParticles)
        {
            this.aliveParticles.clear()
        }
    }

    internal fun update(): Boolean
    {
        var alive = false
        val frame = ((SystemClock.uptimeMillis() - this.statTime) * 25f) / 1000f
        val collector: (Particle) -> Unit = { particle -> this.aliveParticles.add(particle) }

        synchronized(this.particleNodes)
        {
            synchronized(this.particleNodes)
            {
                for (particleNode in this.particleNodes)
                {
                    if (particleNode.emitParticle(frame, collector))
                    {
                        alive = true
                    }
                }
            }

            var particle: Particle

            for (index in this.aliveParticles.size - 1 downTo 0)
            {
                particle = this.aliveParticles[index]

                if (particle.update(frame))
                {
                    alive = true
                }
                else
                {
                    this.aliveParticles.removeAt(index)
                }
            }
        }

        return alive
    }

    @OpenGLThread
    internal fun render(gl: GL10)
    {
        synchronized(this.aliveParticles)
        {
            for (particle in this.aliveParticles)
            {
                particle.draw(gl)
            }
        }
    }
}