# Particle effect

A particle effect is an animation of particles.
It usually used for effects like explosion, fireworks, flicker, shine, ...

A particle is an entity with a size, location, speed, acceleration, material and transparency level,
that may change the duration its life time.

A particle is rarely alone, for example in firework each color point is a particle.

Particles are emit by a
[fr.jhelp.android.library.engine.animation.effect.ParticleNode](../../src/main/java/fr/jhelp/android/library/engine/animation/effect/ParticleNode.kt)
that is use as a source of some particles.

At construction of `ParticleNode` decides :

* `numberParticle` : The number of particles the node will emits
* `lifeTimeInFrame` : The number of frames each particle will live (Count 25 frames per seconds,
  even if rendering is faster, the engine will smoothly adapt)
* `startEmissionFrame` : The number of frames to wait fore launch the first frame. By default it
  starts immediately after the effect is launched.
* `stopEmissionFrame` : The frame number where stop particle emissions. By default it same as start
  emmision to emits all particles in same time.

If `startEmissionFrame` and `stopEmissionFrame` have sme value, all particles are emit on same time.
Else emission is linearly distributes between `startEmissionFrame` and `stopEmissionFrame`.

|        Field         | Description                                    |
|:--------------------:|:-----------------------------------------------|
|       texture        | Optional texture used on each emitted particle |
|  alphaInterpolation  | Interpolation mode for vary alpha              |
| diffuseInterpolation | Interpolation mode for vary the diffuse color  |

Particles are emits randomly inside a box.
If you reduce this box to a point all particle will be emits from same position.
For this use the `setPosition` method.

Particle can be rotated at start, the angle can be the same for all particles or choose randomly in
interval with
the method `setAngle`

Particles can be scaled at start, the scales can be the same for all particles or choose randomly in
interval with
the methods `setScale`

Once emitting a particle will may change location, angle, scale, diffuse color, transparency during
its life time.

To describe location a particle have a speed and acceleration.
A speed and acceleration have both a direction vector.
The direction vector length describes the velocity.

To describes rotation change, angles have an angle change speed and acceleration.

To describes scale change a particle have a speed and acceleration.
A speed and acceleration have both a direction vector.
The direction vector length describes the velocity.

Like driving a car,

* If acceleration is positive (press accelerate pedal) the speed will grows during time.
* If acceleration is negative (press break pedal) the speed will decrease during time.
* If acceleration is nul the speed not change.

The acceleration direction vector will influence the trajectory. if speed and accelerate vector
haven't sme direction,
the effect will turn direction the turn wheel while driving a car.

The speed and acceleration direction and scele vector are defined inside a box and choose randomly
inside it at particle emission.

|          Method          | Description                                                          |
|:------------------------:|:---------------------------------------------------------------------|
|    setSpeedDirection     | Defines the speed vector direction box at emission start             |
|     setSpeedRotation     | Defines the speed minimum and maximum angle at emission start        |
|      setSpeedScale       | Defines the speed vector scale box at emission start                 |
| setAccelerationDirection | Defines the acceleration vector direction box at emission start      |
| setAccelerationRotation  | Defines the acceleration minimum and maximum angle at emission start |
|   setAccelerationScale   | Defines the acceleration vector scale box at emission start          |

To change the diffuse color during particle life time, chose a start color randomly if a range,
chose a end color randomly in a given ange, then interpolates the color during life time to goes
from chosen start color to chosen end color.

* The method `setDiffuseColor` permit to defines the start range and end range.
* The field `diffuseInterpolation` permit to choose the interpolation to use.

The particle transparency may change during its life time.

* The method `setAlpha` choose the opacity value at particle start life and particle end life.
* The filed `alphaInterpolation` chose the interpolation to use.

In particle effect one particle node is rarely enough. to synchronize particle nodes, we regroup
them
in a
[fr.jhelp.android.library.engine.animation.effect.ParticleEffect](../../src/main/java/fr/jhelp/android/library/engine/animation/effect/ParticleEffect.kt)

Their only one visible method `addParticleNode` to add a `ParticleNdoe` to the effect.

Standard way

```kotlin
import fr.jhelp.android.library.animations.interpolation.DecelerationInterpolation
import fr.jhelp.android.library.engine.animation.effect.ParticleEffect
import fr.jhelp.android.library.engine.animation.effect.ParticleNode
import fr.jhelp.android.library.engine.resources.ResourcesAccess
import fr.jhelp.android.library.engine.scene.DARK_CYAN
import fr.jhelp.android.library.engine.scene.DARK_GREEN
import fr.jhelp.android.library.engine.scene.LIGHT_GREEN
import fr.jhelp.android.library.engine.scene.LIGHT_RED
import fr.jhelp.android.library.engine.scene.WHITE
import fr.jhelp.android.library.engine.view.View3D

// ...
val textureParticle = ResourcesAccess.obtainTexture(R.drawable.particle_clear_hard)

// Node for moment climb in sky
val particleNodeClimbInSky = ParticleNode(numberParticle = 1,
                                          lifeTimeInFrame = 20f,
                                          startEmissionFrame = 0f,
                                          stopEmissionFrame = 0f)
particleNodeClimbInSky.texture = textureParticle
particleNodeClimbInSky.setDiffuseColor(WHITE)
particleNodeClimbInSky.setPosition(0f, -1f, -2f)
particleNodeClimbInSky.setScale(0.2f)
particleNodeClimbInSky.setSpeedDirection(0f, 0.1f, 0f)

// Node for explode part
// We wait climb sky almost finished to start the emission
val particleNodeFireworkExplode = ParticleNode(numberParticle = 100,
                                               lifeTimeInFrame = 20f,
                                               startEmissionFrame = 19f,
                                               stopEmissionFrame = 19f)
particleNodeFireworkExplode.texture = textureParticle
particleNodeFireworkExplode.setDiffuseColor(diffuseColorStart1 = LIGHT_RED,
                                            diffuseColorEnd1 = DARK_CYAN,
                                            diffuseColorStart2 = LIGHT_GREEN,
                                            diffuseColorEnd2 = DARK_GREEN)
particleNodeFireworkExplode.setAlpha(alphaStart = 1f, alphaEnd = 0f)
particleNodeFireworkExplode.alphaInterpolation = DecelerationInterpolation(2f)
particleNodeFireworkExplode.setPosition(0f, 1f, 2f)
particleNodeFireworkExplode.setScale(0.1f)
particleNodeFireworkExplode.setSpeedDirection(-0.05f, -0.05f, 0f,
                                              0.05f, 0.05f, 0f)

// Create particle effect
val particleEffect = ParticleEffect()
particleEffect.addParticleNode(particleNodeClimbInSky)
particleEffect.addParticleNode(particleNodeFireworkExplode)

// ...
// Launch the effect
view3D.scene3D.play(particleEffect)
// ...
```

DSL way

```kotlin
import fr.jhelp.android.library.animations.interpolation.DecelerationInterpolation
import fr.jhelp.android.library.engine.dsl.animation.effect.ParticlePlayer
import fr.jhelp.android.library.engine.dsl.particleEffect
import fr.jhelp.android.library.engine.dsl.particleEffectReference
import fr.jhelp.android.library.engine.dsl.texture
import fr.jhelp.android.library.engine.dsl.textureReference
import fr.jhelp.android.library.engine.extensions.tree
import fr.jhelp.android.library.engine.scene.DARK_CYAN
import fr.jhelp.android.library.engine.scene.DARK_GREEN
import fr.jhelp.android.library.engine.scene.LIGHT_GREEN
import fr.jhelp.android.library.engine.scene.LIGHT_RED
import fr.jhelp.android.library.engine.scene.WHITE
import fr.jhelp.android.library.engine.view.View3D

private lateinit var fireworkPlayer: ParticlePlayer

fun sceneReady(view3D: View3D)
{
    val textureParticle = textureReference()
    texture(textureParticle, R.drawable.particle_clear_hard)

    val particleEffectFirework = particleEffectReference()
    particleEffect(particleEffectFirework) {
        // Node for moment climb in sky
        this.add {
            this.numberParticle = 1

            this.lifeTimeInFrame = 20f
            this.startEmissionFrame = 0f
            this.stopEmissionFrame = 0f

            this.positionFirstSecond(0f, -1f, -2f)

            this.texture = textureParticle
            this.constantDiffuse(WHITE)

            this.scaleFirstSecond(0.2f)
            this.speedFirstSecond(0f, 0.1f, 0f)
        }

        // Node for explode firework
        this.add {
            this.numberParticle = 100

            this.lifeTimeInFrame = 20f
            // We wait climb sky almost finished to start the emission
            this.startEmissionFrame = 19f
            this.stopEmissionFrame = 19f

            this.positionFirstSecond(0f, 1f, -2f)

            this.firstSpeedDirectionX = -0.05f
            this.firstSpeedDirectionY = -0.05f
            this.firstSpeedDirectionZ = 0f

            this.secondSpeedDirectionX = 0.05f
            this.secondSpeedDirectionY = 0.05f
            this.secondSpeedDirectionZ = 0f

            this.texture = textureParticle
            this.firstStartDiffuseColor = LIGHT_RED
            this.secondStartDiffuseColor = LIGHT_GREEN
            this.firstEndDiffuseColor = DARK_CYAN
            this.secondEndDiffuseColor = DARK_GREEN

            this.scaleFirstSecond(0.1f)

            this.alphaStart = 1f
            this.alphaEnd = 0f
            this.alphaInterpolation = DecelerationInterpolation(2f)
        }
    }

    view3D.tree {
        // ...
        fireworkPlayer = this.player(particleEffectFirework)
        // ...
    }

    // ...
    // Launch the particle effect
    fireworkPlayer.play()
}
```
