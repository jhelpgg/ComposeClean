# Animation

Animate a node/object is change the node/object position (
See [Position in 3D](../position/PositionIn3D.md))
during the time.

So it can be down be your self in a separate thread.

Here we presents a tool for simplify animation creation :
[fr.jhelp.android.library.engine.animation.keyFrame.AnimationNode3D](../../src/main/java/fr/jhelp/android/library/engine/animation/keyFrame/AnimationNode3D.kt)

Imagine you want he node/object have a certain position at a moment and an other one later.
Here you just have to precise the start position, the end position and let `AnimationNode3D` do the
interpolation for goes from start point to end point smoothly in the specified time or number frame.

To more complex animation, just have to repeat the process to have several steps.
Be at first position on taking specified time, then second position and take some time,
then third position with some time, ...

Each transition use its
own [fr.jhelp.android.library.animations.interpolation.Interpolation](../../../animations/src/main/java/fr/jhelp/android/library/animations/interpolation/Interpolation.kt)
to choose the manner to go to the destination (In continues speed, on accelerate, decelerate, ....)

To animate a node, we need one.

Standard way

```kotlin
import fr.jhelp.android.library.animations.interpolation.BounceInterpolation
import fr.jhelp.android.library.animations.interpolation.DecelerationInterpolation
import fr.jhelp.android.library.animations.interpolation.LinearInterpolation
import fr.jhelp.android.library.engine.animation.keyFrame.AnimationNode3D
import fr.jhelp.android.library.engine.scene.Position3D
import fr.jhelp.android.library.engine.scene.geometry.Box
import fr.jhelp.android.library.engine.view.View3D
import fr.jhelp.android.library.math.extensions.seconds

// ...
val scene3D = view3D.scene3D
val box = Box()
scene3D.root.add(box)

val animation = AnimationNode3D(box)
// We don't start at zero to not have the "jump" effect and prefer take one second to goe in start position
animation.time(milliseconds = 1.seconds,
               Position3D(x = 2.3f, y = 1.0f, z = -9f,
                          angleX = 0.0f, angleY = 10f, angleZ = 25f,
                          scaleX = 0.5f, scaleY = 0.75f, scaleZ = 2f),
               LinearInterpolation)
// At 10 seconds after the start (So 9 seconds take from previous position to this one)
animation.time(milliseconds = 10.seconds,
               Position3D(x = 0f, y = 0f, z = -2f,
                          angleX = 0.0f, angleY = 0f, angleZ = 0f,
                          scaleX = 1f, scaleY = 1f, scaleZ = 1f),
               BounceInterpolation)
// At 15 seconds after the start (So 5 seconds take from previous position to this one)
animation.time(milliseconds = 15.seconds,
               Position3D(x = -2f, y = 0.18f, z = -7f,
                          angleX = 45.0f, angleY = 10f, angleZ = 0f,
                          scaleX = 1f, scaleY = 1f, scaleZ = 1f),
               DecelerationInterpolation(factor = 2.0f))
// ...

// ...
// To launch the animation :
scene3D.play(animation)

// ...
// To stop animation before the end (If animation already finished it does nothing)
scene3D.stop(animation)
// ...
```

DSL way

```kotlin
import fr.jhelp.android.library.animations.interpolation.BounceInterpolation
import fr.jhelp.android.library.animations.interpolation.DecelerationInterpolation
import fr.jhelp.android.library.animations.interpolation.LinearInterpolation
import fr.jhelp.android.library.engine.dsl.animation.player.AnimationPlayer
import fr.jhelp.android.library.engine.dsl.animationNode
import fr.jhelp.android.library.engine.dsl.animationNodeReference
import fr.jhelp.android.library.engine.dsl.nodeReference
import fr.jhelp.android.library.engine.extensions.tree
import fr.jhelp.android.library.engine.view.View3D
import fr.jhelp.android.library.math.extensions.seconds

private lateinit var player: AnimationPlayer

fun sceneReady(view3D: View3D)
{
    val boxReference = nodeReference()
    val animationReference = animationNodeReference(boxReference)
    animationNode(animationReference) {
        // We don't start at zero to not have the "jump" effect and prefer take one second to goe in start position
        this.atTime(atTimeMilliseconds = 1.seconds,
                    withInterpolation = LinearInterpolation) {
            this.x = 2.3f
            this.y = 1.0f
            this.z = -9f

            this.angleX = 0.0f
            this.angleY = 10f
            this.angleZ = 25f

            this.scaleX = 0.5f
            this.scaleY = 0.75f
            this.scaleZ = 2f
        }

        // At 10 seconds after the start (So 9 seconds take from previous position to this one)
        this.atTime(atTimeMilliseconds = 10.seconds,
                    withInterpolation = BounceInterpolation) {
            this.x = 0f
            this.y = 0f
            this.z = -2f

            this.angleX = 0.0f
            this.angleY = 0f
            this.angleZ = 0f

            this.scaleX = 1f
            this.scaleY = 1f
            this.scaleZ = 1f
        }

        // At 15 seconds after the start (So 5 seconds take from previous position to this one)
        this.atTime(atTimeMilliseconds = 15.seconds,
                    withInterpolation = DecelerationInterpolation(factor = 2.0f)) {
            this.x = -2f
            this.y = 0.18f
            this.z = -7f

            this.angleX = 45.0f
            this.angleY = 10f
            this.angleZ = 0f

            this.scaleX = 1f
            this.scaleY = 1f
            this.scaleZ = 1f
        }
    }

    view3D.tree {
        this.root {
            this.box(boxReference) {
                // ...
            }
        }

        player = this.player(animationReference)
    }

    // ...
    // To launch the animation :
    player.play()

    // ...
    // To stop animation before the end (If animation already finished it does nothing)
    player.stop()
}
```

To have more example an a show of all interpolations in action, see the show case :

* [fr.jhelp.android.library.showcase.ui.composables.EngineInterpolationComposable](../../../app/src/main/java/fr/jhelp/android/library/showcase/ui/composables/EngineInterpolationComposable.kt)
* [fr.jhelp.android.library.models.implementation.EngineInterpolationModelImplementation](../../../app_models/src/main/java/fr/jhelp/android/library/models/implementation/EngineInterpolationModelImplementation.kt)

To combine animations or have other types of animation,
see [Animations](../../../animations/doc/Animations.md)
