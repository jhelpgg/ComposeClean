package fr.jhelp.compose.engine.animation.effect

import fr.jhelp.compose.animations.interpolation.Interpolation
import fr.jhelp.compose.animations.interpolation.LinearInterpolation
import fr.jhelp.compose.engine.scene.Color3D
import fr.jhelp.compose.engine.scene.GREY
import fr.jhelp.compose.engine.scene.Position3D
import fr.jhelp.compose.engine.scene.Texture
import fr.jhelp.android.library.math.random
import kotlin.math.max

/**
 * Particle node emits one or several [Particle]
 *
 * [Particle]s can be emits on same time or one after other.
 * To regulate emission :
 * * *startEmissionFrame* : Number of frames to wait before launch the first [Particle] after [ParticleEffect] is launch
 * * *endEmissionFrame* : Number of frames to wait for stop emitting [Particle] after [ParticleEffect] is launch
 * So [Particle] are emits between *startEmissionFrame* and *endEmissionFrame* :
 * * If they are equals, all particles are emits in same time.
 * * Else, particles are emitting regularly during the laps time.
 *
 * Each [Particle] will have the given life time, they disappear after the defined number of frame.
 *
 * [Particle]s can start at same position or in position choose randomly inside a box, with [setPosition].
 * If use the one coordinates version, all [Particle]s start at same position.
 * If use the two coordinates version, [Particle]s start position are chose randomly inside box defined by the two coordinates
 *
 * [Particle]s have a speed direction, this direction can be the same or chose inside a box, like position. With [setSpeedDirection]
 *
 * [Particle]s have an acceleration direction, this direction can be the same or chose inside a box, like position. With [setAccelerationDirection]
 *
 * [Particle]s rotation can change during animation [setAngle], [setSpeedRotation] and [setAccelerationRotation] regulate respectively the start angle, speed angle change and acceleration of angle.
 *
 * [Particle]s scale can change during animation [setScale], [setSpeedScale] and [setAccelerationScale] regulate respectively the start scale, speed cale direction and acceleration scale direction.
 *
 * [texture] defines the texture used by [Particle]s. If `null` no texture and only the diffuse color
 *
 * [Particle]s' diffuse color can change during animation and start/end can choose randomly depends on number arguments involved in [setDiffuseColor].
 * The change color is not necessary linear, an interpolation method can be set with [diffuseInterpolation]
 *
 * [Particle]s' alpha value can change during animation [setAlpha] defines the start and the end value.
 * The way of the change can be choose with [alphaInterpolation]
 */
class ParticleNode(private val numberParticle: Int,
                   private val lifeTimeInFrame: Float,
                   private val startEmissionFrame: Float = 0f,
                   private val stopEmissionFrame: Float = startEmissionFrame)
{
    /**Texture used by [Particle]s*/
    var texture: Texture? = null

    /**Way alpha evolve during animation*/
    var alphaInterpolation: Interpolation = LinearInterpolation

    /**Way diffuse color evolve during animation*/
    var diffuseInterpolation: Interpolation = LinearInterpolation

    private var position1 = Position3D()
    private var position2 = Position3D()
    private var speed1 = Position3D(scaleX = 0f, scaleY = 0f, scaleZ = 0f)
    private var speed2 = Position3D(scaleX = 0f, scaleY = 0f, scaleZ = 0f)
    private var acceleration1 = Position3D(scaleX = 0f, scaleY = 0f, scaleZ = 0f)
    private var acceleration2 = Position3D(scaleX = 0f, scaleY = 0f, scaleZ = 0f)

    private var alphaStart = 1.0f
    private var alphaEnd = 1.0f

    private var diffuseColorStart1 = GREY
    private var diffuseColorStart2 = GREY
    private var diffuseColorEnd1 = GREY
    private var diffuseColorEnd2 = GREY

    private var alreadyEmited: Int = 0
    private var lastEmittedFrame: Float = 0f
    private val stepFrameEmission =
        (this.stopEmissionFrame - this.startEmissionFrame) / this.numberParticle

    /**
     * [Particle]s start position box definition.
     *
     * Note :
     * > If use one coordination version or same coordinates for both, the box is reduced to one point, so all [Particle] will starts at same point
     */
    fun setPosition(x1: Float, y1: Float, z1: Float,
                    x2: Float = x1, y2: Float = y1, z2: Float = z1)
    {
        this.position1.x = x1
        this.position1.y = y1
        this.position1.z = z1

        this.position2.x = x2
        this.position2.y = y2
        this.position2.z = z2
    }

    /**
     * [Particle]s start angle definition in degree.
     *
     * Note :
     * > If use one angle version or same angle value for both, all [Particle]s will start with the same angle
     */
    fun setAngle(angle1: Float,
                 angle2: Float = angle1)
    {
        this.position1.angleZ = angle1
        this.position2.angleZ = angle2
    }

    /**
     * [Particle]s start scale box definition.
     *
     * Note :
     * > If use one scale version or same scale values for both, the box is reduced to one point, so all [Particle] will starts with same scale
     */
    fun setScale(scaleX1: Float, scaleY1: Float, scaleZ1: Float,
                 scaleX2: Float = scaleX1, scaleY2: Float = scaleY1, scaleZ2: Float = scaleZ1)
    {
        this.position1.scaleX = scaleX1
        this.position1.scaleY = scaleY1
        this.position1.scaleZ = scaleZ1

        this.position2.scaleX = scaleX2
        this.position2.scaleY = scaleY2
        this.position2.scaleZ = scaleZ2
    }

    /**
     * [Particle]s start scale value
     *
     * Note :
     * > If use one scale version or same scale values for both, all [Particle] will starts with same scale
     */
    fun setScale(scale1: Float, scale2: Float = scale1)
    {
        this.position1.scaleX = scale1
        this.position1.scaleY = scale1
        this.position1.scaleZ = scale1

        this.position2.scaleX = scale2
        this.position2.scaleY = scale2
        this.position2.scaleZ = scale2
    }

    /**
     * [Particle]s start speed direction box definition.
     *
     * Note :
     * > If use one coordination version or same coordinates for both, the box is reduced to one vector, so all [Particle] will starts with same speed
     */
    fun setSpeedDirection(x1: Float, y1: Float, z1: Float,
                          x2: Float = x1, y2: Float = y1, z2: Float = z1)
    {
        this.speed1.x = x1
        this.speed1.y = y1
        this.speed1.z = z1

        this.speed2.x = x2
        this.speed2.y = y2
        this.speed2.z = z2
    }

    /**
     * [Particle]s speed for rotation angle
     *
     * Note :
     * > If use one rotation angle value version or same rotation angle value for both, all [Particle] will have same speed for rotation angle
     */
    fun setSpeedRotation(angle1: Float,
                         angle2: Float = angle1)
    {
        this.speed1.angleZ = angle1
        this.speed2.angleZ = angle2
    }

    /**
     * [Particle]s start speed for scale direction box definition.
     *
     * Note :
     * > If use one coordination version or same coordinates for both, the box is reduced to one vector, so all [Particle] will starts with same speed for scale
     */
    fun setSpeedScale(scaleX1: Float, scaleY1: Float, scaleZ1: Float,
                      scaleX2: Float = scaleX1, scaleY2: Float = scaleY1, scaleZ2: Float = scaleZ1)
    {
        this.speed1.scaleX = scaleX1
        this.speed1.scaleY = scaleY1
        this.speed1.scaleZ = scaleZ1

        this.speed2.scaleX = scaleX2
        this.speed2.scaleY = scaleY2
        this.speed2.scaleZ = scaleZ2
    }

    /**
     * [Particle]s speed for scale in all direction
     *
     * Note :
     * > If use one scale value version or same scale value for both, all [Particle] will have same speed for scale
     */
    fun setSpeedScale(scale1: Float, scale2: Float = scale1)
    {
        this.speed1.scaleX = scale1
        this.speed1.scaleY = scale1
        this.speed1.scaleZ = scale1

        this.speed2.scaleX = scale2
        this.speed2.scaleY = scale2
        this.speed2.scaleZ = scale2
    }

    /**
     * [Particle]s acceleration direction box definition.
     *
     * Note :
     * > If use one coordination version or same coordinates for both, the box is reduced to one vector, so all [Particle] will have same acceleration
     */
    fun setAccelerationDirection(x1: Float, y1: Float, z1: Float,
                                 x2: Float = x1, y2: Float = y1, z2: Float = z1)
    {
        this.acceleration1.x = x1
        this.acceleration1.y = y1
        this.acceleration1.z = z1

        this.acceleration2.x = x2
        this.acceleration2.y = y2
        this.acceleration2.z = z2
    }

    /**
     * [Particle]s acceleration for rotation angle
     *
     * Note :
     * > If use one rotation angle value version or same rotation angle value for both, all [Particle] will have same acceleration for rotation angle
     */
    fun setAccelerationRotation(angle1: Float,
                                angle2: Float = angle1)
    {
        this.acceleration1.angleZ = angle1
        this.acceleration2.angleZ = angle2
    }

    /**
     * [Particle]s acceleration for scale direction box definition.
     *
     * Note :
     * > If use one coordination version or same coordinates for both, the box is reduced to one vector, so all [Particle] will have same acceleration  for scale
     */
    fun setAccelerationScale(scaleX1: Float, scaleY1: Float, scaleZ1: Float,
                             scaleX2: Float = scaleX1,
                             scaleY2: Float = scaleY1,
                             scaleZ2: Float = scaleZ1)
    {
        this.acceleration1.scaleX = scaleX1
        this.acceleration1.scaleY = scaleY1
        this.acceleration1.scaleZ = scaleZ1

        this.acceleration2.scaleX = scaleX2
        this.acceleration2.scaleY = scaleY2
        this.acceleration2.scaleZ = scaleZ2
    }

    /**
     * [Particle]s acceleration for scale in all direction
     *
     * Note :
     * > If use one scale value version or same scale value for both, all [Particle] will have same acceleration  for scale
     */
    fun setAccelerationScale(scale1: Float, scale2: Float = scale1)
    {
        this.acceleration1.scaleX = scale1
        this.acceleration1.scaleY = scale1
        this.acceleration1.scaleZ = scale1

        this.acceleration2.scaleX = scale2
        this.acceleration2.scaleY = scale2
        this.acceleration2.scaleZ = scale2
    }

    /**
     * Define diffuse color for particle
     *
     * Diffuse color at start of animation is choose between *diffuseColorStart1* and *diffuseColorStart2*.
     * So if they are the same, all [Particle] will start with same color
     *
     * Diffuse color at end of animation is choose between *diffuseColorEnd1* and *diffuseColorEnd2*.
     * So if they are the same, all [Particle] will end with same color
     *
     * To regulate how change progress see [diffuseInterpolation]
     */
    fun setDiffuseColor(diffuseColorStart1: Color3D,
                        diffuseColorEnd1: Color3D = diffuseColorStart1,
                        diffuseColorStart2: Color3D = diffuseColorStart1,
                        diffuseColorEnd2: Color3D = diffuseColorEnd1)
    {
        this.diffuseColorStart1 = diffuseColorStart1
        this.diffuseColorEnd1 = diffuseColorEnd1
        this.diffuseColorStart2 = diffuseColorStart2
        this.diffuseColorEnd2 = diffuseColorEnd2
    }

    /**
     * Define alpha value.
     *
     * To regulate how change progress see [alphaInterpolation]
     *
     * @param alphaStart Value of alpha at animation start
     * @param alphaEnd  Value of alpha at animation end
     */
    fun setAlpha(alphaStart: Float, alphaEnd: Float = alphaStart)
    {
        this.alphaStart = alphaStart
        this.alphaEnd = alphaEnd
    }

    internal fun resetEmission()
    {
        this.alreadyEmited = 0
        this.lastEmittedFrame = 0f
    }

    internal fun emitParticle(frame: Float, collector: (Particle) -> Unit): Boolean
    {
        if (this.lastEmittedFrame > this.stopEmissionFrame || this.alreadyEmited >= this.numberParticle)
        {
            this.lastEmittedFrame = frame
            return false
        }

        if (frame < this.startEmissionFrame)
        {
            this.lastEmittedFrame = frame
            return true
        }

        var emissionFrame = max(this.startEmissionFrame, this.lastEmittedFrame)

        if (emissionFrame >= this.stopEmissionFrame)
        {
            var particle: Particle

            for (number in this.alreadyEmited until this.numberParticle)
            {
                particle = Particle(this.stopEmissionFrame,
                                    this.randomPosition(),
                                    this.randomSpeed(),
                                    this.randomAcceleration(),
                                    this.lifeTimeInFrame)
                particle.material.texture = this.texture
                particle.alphaStart = this.alphaStart
                particle.alphaEnd = this.alphaEnd
                particle.alphaInterpolation = this.alphaInterpolation
                particle.diffuseStart = this.randomDiffuseColorStart()
                particle.diffuseEnd = this.randomDiffuseColorEnd()
                particle.diffuseInterpolation = this.diffuseInterpolation
                collector(particle)
            }

            this.alreadyEmited = this.numberParticle
            this.lastEmittedFrame = emissionFrame
        }
        else
        {
            while (emissionFrame <= frame && this.alreadyEmited < this.numberParticle)
            {
                collector(Particle(emissionFrame,
                                   this.randomPosition(),
                                   this.randomSpeed(),
                                   this.randomAcceleration(),
                                   this.lifeTimeInFrame))
                this.alreadyEmited++
                emissionFrame += this.stepFrameEmission
                this.lastEmittedFrame = emissionFrame
            }
        }

        return true
    }

    private fun randomPosition(): Position3D = this.random(this.position1, this.position2)

    private fun randomSpeed(): Position3D = this.random(this.speed1, this.speed2)

    private fun randomAcceleration(): Position3D =
        this.random(this.acceleration1, this.acceleration2)

    private fun random(position1: Position3D, position2: Position3D): Position3D =
        Position3D(random(position1.x, position2.x),
                   random(position1.y, position2.y),
                   random(position1.z, position2.z),
                   random(position1.angleX, position2.angleX),
                   random(position1.angleY, position2.angleY),
                   random(position1.angleZ, position2.angleZ),
                   random(position1.scaleX, position2.scaleX),
                   random(position1.scaleY, position2.scaleY),
                   random(position1.scaleZ, position2.scaleZ))

    private fun randomDiffuseColorStart(): Color3D =
        this.randomColor(this.diffuseColorStart1, this.diffuseColorStart2)

    private fun randomDiffuseColorEnd(): Color3D =
        this.randomColor(this.diffuseColorEnd1, this.diffuseColorEnd2)

    private fun randomColor(color1: Color3D, color2: Color3D): Color3D =
        Color3D(random(color1.red, color2.red),
                random(color1.green, color2.green),
                random(color1.blue, color2.blue),
                random(color1.alpha, color2.alpha))
}