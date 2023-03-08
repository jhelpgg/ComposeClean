package fr.jhelp.compose.engine.dsl.animation.effect

import fr.jhelp.compose.animations.interpolation.Interpolation
import fr.jhelp.compose.animations.interpolation.LinearInterpolation
import fr.jhelp.compose.engine.animation.effect.Particle
import fr.jhelp.compose.engine.animation.effect.ParticleNode
import fr.jhelp.compose.engine.dsl.TextureReference
import fr.jhelp.compose.engine.scene.GREY
import fr.jhelp.compose.math.extensions.bounds
import kotlin.math.max

class ParticleNodeCreator
{
    var numberParticle: Int = 1
        set(value)
        {
            field = max(value, 1)
        }
    var lifeTimeInFrame: Float = 25.0f
        set(value)
        {
            field = max(1.0f, value)
        }
    var startEmissionFrame: Float = 0f
        set(value)
        {
            field = max(0f, value)
            this.stopEmissionFrame = max(value, this.stopEmissionFrame)
        }
    var stopEmissionFrame: Float = 0f
        set(value)
        {
            field = max(this.startEmissionFrame, value)
        }

    /**Texture used by [Particle]s*/
    var texture: TextureReference? = null

    /**Way alpha evolve during animation*/
    var alphaInterpolation: Interpolation = LinearInterpolation

    /**Way diffuse color evolve during animation*/
    var diffuseInterpolation: Interpolation = LinearInterpolation

    var firstStartPositionX = 0f
    var firstStartPositionY = 0f
    var firstStartPositionZ = 0f

    var secondStartPositionX = 0f
    var secondStartPositionY = 0f
    var secondStartPositionZ = 0f

    var firstStartAngle = 0f
    var secondStartAngle = 0f

    var firstStartScaleX = 1f
        set(value)
        {
            field = max(0.01f, value)
        }
    var firstStartScaleY = 1f
        set(value)
        {
            field = max(0.01f, value)
        }
    var firstStartScaleZ = 1f
        set(value)
        {
            field = max(0.01f, value)
        }

    var secondStartScaleX = 1f
        set(value)
        {
            field = max(0.01f, value)
        }
    var secondStartScaleY = 1f
        set(value)
        {
            field = max(0.01f, value)
        }
    var secondStartScaleZ = 1f
        set(value)
        {
            field = max(0.01f, value)
        }

    var firstSpeedDirectionX = 0f
    var firstSpeedDirectionY = 0f
    var firstSpeedDirectionZ = 0f

    var secondSpeedDirectionX = 0f
    var secondSpeedDirectionY = 0f
    var secondSpeedDirectionZ = 0f

    var firstSpeedAngle = 0f
    var secondSpeedAngle = 0f

    var firstSpeedScaleX = 0f
        set(value)
        {
            field = max(0f, value)
        }
    var firstSpeedScaleY = 0f
        set(value)
        {
            field = max(0f, value)
        }
    var firstSpeedScaleZ = 0f
        set(value)
        {
            field = max(0f, value)
        }

    var secondSpeedScaleX = 0f
        set(value)
        {
            field = max(0f, value)
        }
    var secondSpeedScaleY = 0f
        set(value)
        {
            field = max(0f, value)
        }
    var secondSpeedScaleZ = 0f
        set(value)
        {
            field = max(0f, value)
        }


    var firstAccelerationDirectionX = 0f
    var firstAccelerationDirectionY = 0f
    var firstAccelerationDirectionZ = 0f

    var secondAccelerationDirectionX = 0f
    var secondAccelerationDirectionY = 0f
    var secondAccelerationDirectionZ = 0f

    var firstAccelerationAngle = 0f
    var secondAccelerationAngle = 0f

    var firstAccelerationScaleX = 0f
        set(value)
        {
            field = max(0f, value)
        }
    var firstAccelerationScaleY = 0f
        set(value)
        {
            field = max(0f, value)
        }
    var firstAccelerationScaleZ = 0f
        set(value)
        {
            field = max(0f, value)
        }

    var secondAccelerationScaleX = 0f
        set(value)
        {
            field = max(0f, value)
        }
    var secondAccelerationScaleY = 0f
        set(value)
        {
            field = max(0f, value)
        }
    var secondAccelerationScaleZ = 0f
        set(value)
        {
            field = max(0f, value)
        }

    var firstStartDiffuseColor = GREY
    var secondStartDiffuseColor = GREY
    var firstEndDiffuseColor = GREY
    var secondEndDiffuseColor = GREY

    var alphaStart = 1.0f
        set(value)
        {
            field = value.bounds(0f, 1.0f)
        }
    var alphaEnd = 1.0f
        set(value)
        {
            field = value.bounds(0f, 1.0f)
        }

    internal fun particleNode(): ParticleNode
    {
        val particleNode = ParticleNode(this.numberParticle,
                                        this.lifeTimeInFrame,
                                        this.startEmissionFrame,
                                        this.stopEmissionFrame)
        this.texture?.let { texture -> particleNode.texture = texture.textureSource.texture }
        particleNode.alphaInterpolation = this.alphaInterpolation
        particleNode.diffuseInterpolation = this.diffuseInterpolation
        particleNode.setPosition(this.firstStartPositionX,
                                 this.firstStartPositionY,
                                 this.firstStartPositionZ,
                                 this.secondStartPositionX,
                                 this.secondStartPositionY,
                                 this.secondStartPositionZ)
        particleNode.setAngle(this.firstStartAngle, this.secondStartAngle)
        particleNode.setScale(this.firstStartScaleX,
                              this.firstStartScaleY,
                              this.firstStartScaleZ,
                              this.secondStartScaleX,
                              this.secondStartScaleY,
                              this.secondStartScaleZ)
        particleNode.setSpeedDirection(this.firstSpeedDirectionX,
                                       this.firstSpeedDirectionY,
                                       this.firstSpeedDirectionZ,
                                       this.secondSpeedDirectionX,
                                       this.secondSpeedDirectionY,
                                       this.secondSpeedDirectionZ)
        particleNode.setSpeedRotation(this.firstSpeedAngle, this.secondSpeedAngle)
        particleNode.setSpeedScale(this.firstSpeedScaleX,
                                   this.firstSpeedScaleY,
                                   this.firstSpeedScaleZ,
                                   this.secondSpeedScaleX,
                                   this.secondSpeedScaleY,
                                   this.secondSpeedScaleZ)
        particleNode.setAccelerationDirection(this.firstAccelerationDirectionX,
                                              this.firstAccelerationDirectionY,
                                              this.firstAccelerationDirectionZ,
                                              this.secondAccelerationDirectionX,
                                              this.secondAccelerationDirectionY,
                                              this.secondAccelerationDirectionZ)
        particleNode.setAccelerationRotation(this.firstAccelerationAngle,
                                             this.secondAccelerationAngle)
        particleNode.setAccelerationScale(this.firstAccelerationScaleX,
                                          this.firstAccelerationScaleY,
                                          this.firstAccelerationScaleZ,
                                          this.secondAccelerationScaleX,
                                          this.secondAccelerationScaleY,
                                          this.secondAccelerationScaleZ)
        particleNode.setAlpha(this.alphaStart, this.alphaEnd)
        particleNode.setDiffuseColor(this.firstStartDiffuseColor, this.firstEndDiffuseColor,
                                     this.secondStartDiffuseColor, this.secondEndDiffuseColor)
        return particleNode
    }
}