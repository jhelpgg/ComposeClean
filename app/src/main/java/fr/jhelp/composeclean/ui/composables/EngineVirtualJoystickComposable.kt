package fr.jhelp.composeclean.ui.composables

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import fr.jhelp.compose.animations.AnimationFunction
import fr.jhelp.compose.engine.resources.ResourcesAccess
import fr.jhelp.compose.engine.scene.Scene3D
import fr.jhelp.compose.engine.scene.WHITE
import fr.jhelp.compose.engine.scene.geometry.Box
import fr.jhelp.compose.engine.view.joystick.VirtualJoystick
import fr.jhelp.compose.engine.view.joystick.VirtualJoystickDirection
import fr.jhelp.compose.engine.view.joystick.VirtualJoystickType
import fr.jhelp.compose.engine.view.touch.View3DTouchOverSensitive
import fr.jhelp.compose.math.random
import fr.jhelp.compose.ui.engine.View3DComposable
import fr.jhelp.composeclean.R
import fr.jhelp.tasks.extensions.observedBy
import java.util.concurrent.atomic.AtomicBoolean

/**
 * Show the engine 3d with virtual joystick
 */
class EngineVirtualJoystickComposable
{
    private val sceneReady = AtomicBoolean(false)
    private lateinit var scene: Scene3D

    /**
     * Show the engine 3d with virtual joystick
     */
    @Composable
    fun Show()
    {
        val step = 0.04f
        val angle = 5f
        val box = Box()
        box.material.diffuse = WHITE
        box.material.texture = ResourcesAccess.obtainTexture(R.drawable.floor)

        val noAnimation = AnimationFunction(25) { false }
        val upAnimation = AnimationFunction(25) {
            box.position.y += step
            box.position.angleX -= angle
            true
        }
        val upLeftAnimation = AnimationFunction(25) {
            box.position.y += step / 2f
            box.position.angleX -= angle / 2f
            box.position.x -= step / 2f
            box.position.angleY -= angle / 2f
            true
        }
        val upRightAnimation = AnimationFunction(25) {
            box.position.y += step / 2f
            box.position.angleX -= angle / 2f
            box.position.x += step / 2f
            box.position.angleY += angle / 2f
            true
        }
        val downAnimation = AnimationFunction(25) {
            box.position.y -= step
            box.position.angleX += angle
            true
        }
        val downLeftAnimation = AnimationFunction(25) {
            box.position.y -= step / 2f
            box.position.angleX += angle / 2f
            box.position.x -= step / 2f
            box.position.angleY -= angle / 2f
            true
        }
        val downRightAnimation = AnimationFunction(25) {
            box.position.y -= step / 2f
            box.position.angleX += angle / 2f
            box.position.x += step / 2f
            box.position.angleY += angle / 2f
            true
        }
        val leftAnimation = AnimationFunction(25) {
            box.position.x -= step
            box.position.angleY -= angle
            true
        }
        val rightAnimation = AnimationFunction(25) {
            box.position.x += step
            box.position.angleY += angle
            true
        }
        var animation = noAnimation
        val virtualJoystick = VirtualJoystick(random(VirtualJoystickType::class.java))
        virtualJoystick.direction.observedBy { direction ->
            if (this.sceneReady.get())
            {
                this.scene.stop(animation)

                animation =
                    when (direction)
                    {
                        VirtualJoystickDirection.NONE       -> noAnimation
                        VirtualJoystickDirection.UP         -> upAnimation
                        VirtualJoystickDirection.UP_LEFT    -> upLeftAnimation
                        VirtualJoystickDirection.UP_RIGHT   -> upRightAnimation
                        VirtualJoystickDirection.DOWN       -> downAnimation
                        VirtualJoystickDirection.DOWN_LEFT  -> downLeftAnimation
                        VirtualJoystickDirection.DOWN_RIGHT -> downRightAnimation
                        VirtualJoystickDirection.LEFT       -> leftAnimation
                        VirtualJoystickDirection.RIGHT      -> rightAnimation
                        else                                -> noAnimation
                    }

                this.scene.play(animation)
            }
        }

        val view3D = View3DComposable()
        view3D.view3DTouchAction = View3DTouchOverSensitive(virtualJoystick)
        view3D.Draw(modifier = Modifier.fillMaxSize()) {
            this@EngineVirtualJoystickComposable.scene = this.scene3D
            this@EngineVirtualJoystickComposable.sceneReady.set(true)
            this.scenePosition { this.z = -4f }
            this.root {
                this.node { this.add(box) }
            }
        }
    }
}