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


