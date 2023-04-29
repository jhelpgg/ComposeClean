package fr.jhelp.compose.engine.dsl.animation.effect

import fr.jhelp.compose.animations.interpolation.Interpolation
import fr.jhelp.compose.animations.interpolation.LinearInterpolation
import fr.jhelp.compose.engine.animation.effect.Particle
import fr.jhelp.compose.engine.animation.effect.ParticleNode
import fr.jhelp.compose.engine.dsl.TextureReference
import fr.jhelp.compose.engine.scene.Color3D
import fr.jhelp.compose.engine.scene.GREY
import fr.jhelp.compose.math.extensions.bounds
import kotlin.math.max

/**
 * Creator of particle node source
 */
class ParticleNodeCreator
{
    /** Number of particle to create */
    var numberParticle: Int = 1
        set(value)
        {
            field = max(value, 1)
        }

    /** Each particle life time in frame duration */
    var lifeTimeInFrame: Float = 25.0f
        set(value)
        {
            field = max(1.0f, value)
        }

    /** Frame where start particle emission */
    var startEmissionFrame: Float = 0f
        set(value)
        {
            field = max(0f, value)
            this.stopEmissionFrame = max(field, this.stopEmissionFrame)
        }

    /** Frame of the last emitted particle */
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

    /** X of the first corner of start particles box */
    var firstStartPositionX: Float = 0f

    /** Y of the first corner of start particles box */
    var firstStartPositionY: Float = 0f

    /** Z of the first corner of start particles box */
    var firstStartPositionZ: Float = 0f

    /** X of the second corner of start particles box */
    var secondStartPositionX: Float = 0f

    /** Y of the second corner of start particles box */
    var secondStartPositionY: Float = 0f

    /** Z of the second corner of start particles box */
    var secondStartPositionZ: Float = 0f

    /** First start angle limit */
    var firstStartAngle: Float = 0f

    /** Second start angle limit */
    var secondStartAngle: Float = 0f

    /** First start scale limit on X */
    var firstStartScaleX: Float = 1f
        set(value)
        {
            field = max(0.01f, value)
        }

    /** First start scale limit on Y */
    var firstStartScaleY: Float = 1f
        set(value)
        {
            field = max(0.01f, value)
        }

    /** First start scale limit on Z */
    var firstStartScaleZ: Float = 1f
        set(value)
        {
            field = max(0.01f, value)
        }

    /** Second start scale limit on X */
    var secondStartScaleX: Float = 1f
        set(value)
        {
            field = max(0.01f, value)
        }

    /** Second start scale limit on Y */
    var secondStartScaleY: Float = 1f
        set(value)
        {
            field = max(0.01f, value)
        }

    /** Second start scale limit on Z */
    var secondStartScaleZ: Float = 1f
        set(value)
        {
            field = max(0.01f, value)
        }

    /** X of first speed direction */
    var firstSpeedDirectionX: Float = 0f

    /** Y of first speed direction */
    var firstSpeedDirectionY: Float = 0f

    /** Z of first speed direction */
    var firstSpeedDirectionZ: Float = 0f

    /** X of second speed direction */
    var secondSpeedDirectionX: Float = 0f

    /** Y of second speed direction */
    var secondSpeedDirectionY: Float = 0f

    /** Z of second speed direction */
    var secondSpeedDirectionZ: Float = 0f

    /** First speed angle */
    var firstSpeedAngle: Float = 0f

    /** Second speed angle */
    var secondSpeedAngle: Float = 0f

    /** First speed scale on X */
    var firstSpeedScaleX: Float = 0f
        set(value)
        {
            field = max(0f, value)
        }

    /** First speed scale on Y */
    var firstSpeedScaleY: Float = 0f
        set(value)
        {
            field = max(0f, value)
        }

    /** First speed scale on Z */
    var firstSpeedScaleZ: Float = 0f
        set(value)
        {
            field = max(0f, value)
        }

    /** Second speed scale on X */
    var secondSpeedScaleX: Float = 0f
        set(value)
        {
            field = max(0f, value)
        }

    /** Second speed scale on Y */
    var secondSpeedScaleY: Float = 0f
        set(value)
        {
            field = max(0f, value)
        }

    /** Second speed scale on Z */
    var secondSpeedScaleZ: Float = 0f
        set(value)
        {
            field = max(0f, value)
        }


    /** X of first acceleration vector  */
    var firstAccelerationDirectionX: Float = 0f

    /** Y of first acceleration vector  */
    var firstAccelerationDirectionY: Float = 0f

    /** Z of first acceleration vector  */
    var firstAccelerationDirectionZ: Float = 0f

    /** X of second acceleration vector  */
    var secondAccelerationDirectionX: Float = 0f

    /** Y of second acceleration vector  */
    var secondAccelerationDirectionY: Float = 0f

    /** Z of second acceleration vector  */
    var secondAccelerationDirectionZ: Float = 0f

    /** First acceleration angle */
    var firstAccelerationAngle: Float = 0f

    /** Second acceleration angle */
    var secondAccelerationAngle: Float = 0f

    /** First acceleration scale on X */
    var firstAccelerationScaleX: Float = 0f
        set(value)
        {
            field = max(0f, value)
        }

    /** First acceleration scale on Y */
    var firstAccelerationScaleY: Float = 0f
        set(value)
        {
            field = max(0f, value)
        }

    /** First acceleration scale on Z */
    var firstAccelerationScaleZ: Float = 0f
        set(value)
        {
            field = max(0f, value)
        }

    /** Second acceleration scale on X */
    var secondAccelerationScaleX: Float = 0f
        set(value)
        {
            field = max(0f, value)
        }

    /** Second acceleration scale on Y */
    var secondAccelerationScaleY: Float = 0f
        set(value)
        {
            field = max(0f, value)
        }

    /** Second acceleration scale on Z */
    var secondAccelerationScaleZ: Float = 0f
        set(value)
        {
            field = max(0f, value)
        }

    /** First particle diffuse limit on start of particle life */
    var firstStartDiffuseColor: Color3D = GREY

    /** Second particle diffuse limit on start of particle life  */
    var secondStartDiffuseColor: Color3D = GREY

    /** First particle diffuse limit on end of particle life */
    var firstEndDiffuseColor: Color3D = GREY

    /** Second particle diffuse limit on end of particle life  */
    var secondEndDiffuseColor: Color3D = GREY

    /** Particle opacity/aplha at start of particle life */
    var alphaStart: Float = 1.0f
        set(value)
        {
            field = value.bounds(0f, 1.0f)
        }

    /** Particle opacity/aplha at end of particle life */
    var alphaEnd: Float = 1.0f
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