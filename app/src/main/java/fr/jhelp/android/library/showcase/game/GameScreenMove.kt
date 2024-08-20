package fr.jhelp.android.library.showcase.game

import android.graphics.Paint
import fr.jhelp.android.library.animations.Animation
import fr.jhelp.android.library.animations.AnimationFunction
import fr.jhelp.android.library.engine.extensions.position
import fr.jhelp.android.library.engine.resources.draw
import fr.jhelp.android.library.engine.resources.texture
import fr.jhelp.android.library.engine.scene.Node3D
import fr.jhelp.android.library.engine.scene.Scene3D
import fr.jhelp.android.library.engine.scene.geometry.Plane
import fr.jhelp.android.library.engine.view.joystick.VirtualJoystickDirection
import fr.jhelp.android.library.engine.view.joystick.VirtualJoystickType
import fr.jhelp.android.library.engine.view.overlay.OverlayScreen
import fr.jhelp.android.library.engine.view.overlay.OverlayScreenJoystick
import fr.jhelp.android.library.math.COLOR_GREEN_0300
import fr.jhelp.android.library.math.COLOR_GREY_0300
import fr.jhelp.android.library.showcase.game.models.Castle
import fr.jhelp.android.library.showcase.game.models.CastleBridgeState
import fr.jhelp.android.library.showcase.game.models.Tree
import fr.jhelp.android.library.tasks.extensions.observedBy
import fr.jhelp.android.library.tasks.future.FutureResult

/**
 * Screen for move hero
 */
class GameScreenMove(scene3D: Scene3D) : GameScreen(scene3D)
{
    private val noAnimation = AnimationFunction { false }
    private val animationUp = AnimationFunction {
        this.scene.root.position.angleX -= 1f
        true
    }
    private val animationUpLeft = AnimationFunction {
        this.scene.root.position.angleX -= 0.5f
        this.scene.root.position.angleY -= 0.5f
        true
    }
    private val animationLeft = AnimationFunction {
        this.scene.root.position.angleY -= 1f
        true
    }
    private val animationDownLeft = AnimationFunction {
        this.scene.root.position.angleX += 0.5f
        this.scene.root.position.angleY -= 0.5f
        true
    }
    private val animationDown = AnimationFunction {
        this.scene.root.position.angleX += 1f
        true
    }
    private val animationDownRight = AnimationFunction {
        this.scene.root.position.angleX += 0.5f
        this.scene.root.position.angleY += 0.5f
        true
    }
    private val animationRight = AnimationFunction {
        this.scene.root.position.angleY += 1f
        true
    }
    private val animationUpRight = AnimationFunction {
        this.scene.root.position.angleX -= 0.5f
        this.scene.root.position.angleY += 0.5f
        true
    }

    private val castle = Castle()
    private val overlayJoystick = OverlayScreenJoystick(VirtualJoystickType.GREEN)
    private var joystickListener: FutureResult<Unit>? = null
    override val overlay: OverlayScreen = this.overlayJoystick
    private var currentAnimation: Animation = this.noAnimation
    private lateinit var bridgeState: CastleBridgeState

    override fun attached()
    {
        this.castle.bridgeState.observedBy { state -> this.bridgeState = state }
        this.joystickListener?.cancel("attached")
        this.joystickListener = this.overlayJoystick.direction.observedBy(action = this::joystick)
        this.overlayJoystick.click = this::click
        this.scene.root.position.z = -3f
        val castle = this.castle.node
        this.scene.root.add(castle)
        //
        val tree = Tree()
        tree.node.position.x = 2.5f
        this.scene.root.add(tree.node)
        var z = -5f
        val road = this.road()

        for(roadPart in 0 until 20) {
            val clone =road.copy()
            clone.position {
                this.scaleX = 15f
                this.angleX = 90f
                this.y = -1.01f
                this.z = z
            }

            this.scene.root.add(clone)
            z += 1f
        }
    }

    private fun road(): Node3D {
        val plane = Plane()
        plane.material.texture = texture(512, 512).draw { bitmap, canvas, paint ->
            paint.color = COLOR_GREEN_0300
            paint.style = Paint.Style.FILL
            canvas.drawRect(0f, 0f, 512f, 512f, paint)
            paint.color = COLOR_GREY_0300
            canvas.drawRect(240f, 0f, 270f, 512f, paint)
        }

        return plane
    }
    override fun detached()
    {
        this.joystickListener?.cancel("detached")
    }

    private fun joystick(joystickDirection: VirtualJoystickDirection)
    {
        this.scene.stop(this.currentAnimation)
        this.currentAnimation =
            when (joystickDirection)
            {
                VirtualJoystickDirection.NONE       -> this.noAnimation
                VirtualJoystickDirection.UP         -> this.animationUp
                VirtualJoystickDirection.UP_LEFT    -> this.animationUpLeft
                VirtualJoystickDirection.LEFT       -> this.animationLeft
                VirtualJoystickDirection.DOWN_LEFT  -> this.animationDownLeft
                VirtualJoystickDirection.DOWN       -> this.animationDown
                VirtualJoystickDirection.DOWN_RIGHT -> this.animationDownRight
                VirtualJoystickDirection.RIGHT      -> this.animationUpRight
                VirtualJoystickDirection.UP_RIGHT   -> this.animationUpRight
            }
        this.scene.play(this.currentAnimation)
    }

    private fun click()
    {
        if (this.bridgeState == CastleBridgeState.CLOSED || this.bridgeState == CastleBridgeState.CLOSING)
        {
            this.castle.open(this.scene, 2048)
        }
        else if (this.bridgeState == CastleBridgeState.OPENED || this.bridgeState == CastleBridgeState.OPENING)
        {
            this.castle.close(this.scene, 2048)
        }
    }
}