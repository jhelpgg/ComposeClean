package fr.jhelp.compose.engine.animation.effect

import fr.jhelp.compose.animations.interpolation.Interpolation
import fr.jhelp.compose.animations.interpolation.LinearInterpolation
import fr.jhelp.compose.engine.annotations.OpenGLThread
import fr.jhelp.compose.engine.extensions.times
import fr.jhelp.compose.engine.scene.Clone3D
import fr.jhelp.compose.engine.scene.Color3D
import fr.jhelp.compose.engine.scene.GREY
import fr.jhelp.compose.engine.scene.Material
import fr.jhelp.compose.engine.scene.Position3D
import fr.jhelp.compose.engine.scene.geometry.Plane
import javax.microedition.khronos.opengles.GL10

/**
 * A particle with its information and current state
 *
 * Use [ParticleNode] to emit one
 */
internal class Particle(private val startFrame: Float,
                        private val position: Position3D,
                        private val speed: Position3D,
                        private val acceleration: Position3D,
                        private val lifeTimeInFrame: Float)
{
    companion object
    {
        private val plane by lazy { Plane() }
    }

    private val plane = Clone3D(Particle.plane)
    var material: Material
        get() = this.plane.material
        set(material)
        {
            this.plane.material = material
        }
    private val currentPosition = Position3D()
    var diffuseStart = GREY
    var diffuseEnd = GREY
    var diffuseInterpolation: Interpolation = LinearInterpolation
    var alphaStart = 1.0f
    var alphaEnd = 1.0f
    var alphaInterpolation: Interpolation = LinearInterpolation

    @OpenGLThread
    fun draw(gl: GL10)
    {
        gl.glPushMatrix()
        this.currentPosition.locate(gl)
        this.material.render(gl)
        this.plane.render(gl)
        gl.glPopMatrix()
    }

    fun update(frame: Float): Boolean
    {
        var stillAlive = true
        var frameToUse = frame - this.startFrame

        if (frameToUse > this.lifeTimeInFrame)
        {
            stillAlive = false
            frameToUse = this.lifeTimeInFrame
        }

        val percent = frameToUse / this.lifeTimeInFrame
        var factor = this.diffuseInterpolation(percent)
        var rotcaf = 1.0f - factor
        val red = this.diffuseStart.red * rotcaf + this.diffuseEnd.red * factor
        val green = this.diffuseStart.green * rotcaf + this.diffuseEnd.green * factor
        val blue = this.diffuseStart.blue * rotcaf + this.diffuseEnd.blue * factor
        val alpha = this.diffuseStart.alpha * rotcaf + this.diffuseEnd.alpha * factor
        this.material.diffuse = Color3D(red, green, blue, alpha)

        factor = this.alphaInterpolation(percent)
        rotcaf = 1.0f - factor
        this.material.alpha = this.alphaStart * rotcaf + this.alphaEnd * factor

        val currentSpeed = this.speed + frameToUse * this.acceleration
        this.currentPosition.set(this.position + frameToUse * currentSpeed)

        return stillAlive
    }
}