package fr.jhelp.composeclean.game

import fr.jhelp.compose.animations.Animation
import fr.jhelp.compose.animations.AnimationFunction
import fr.jhelp.compose.engine.scene.Scene3D
import fr.jhelp.compose.engine.view.joystick.VirtualJoystickDirection
import fr.jhelp.compose.engine.view.joystick.VirtualJoystickType
import fr.jhelp.compose.engine.view.overlay.OverlayScreen
import fr.jhelp.compose.engine.view.overlay.OverlayScreenJoystick
import fr.jhelp.composeclean.game.models.Castle
import fr.jhelp.composeclean.game.models.CastleBridgeState
import fr.jhelp.tasks.extensions.observedBy
import fr.jhelp.tasks.future.FutureResult

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

    private val overlayJoystick = OverlayScreenJoystick(VirtualJoystickType.GREEN)
    private var joystickListener: FutureResult<Unit>? = null
    override val overlay: OverlayScreen = this.overlayJoystick
    private var currentAnimation: Animation = this.noAnimation
    private lateinit var bridgeState: CastleBridgeState

    override fun attached()
    {
        Castle.bridgeState.observedBy { state -> this.bridgeState = state }
        this.joystickListener?.cancel("attached")
        this.joystickListener = this.overlayJoystick.direction.observedBy(action = this::joystick)
        this.overlayJoystick.click = this::click
        this.scene.root.position.z = -3f
        val castle = Castle.node
        this.scene.root.add(castle)
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
            Castle.open(this.scene, 2048)
        }
        else if (this.bridgeState == CastleBridgeState.OPENED || this.bridgeState == CastleBridgeState.OPENING)
        {
            Castle.close(this.scene, 2048)
        }
    }
}