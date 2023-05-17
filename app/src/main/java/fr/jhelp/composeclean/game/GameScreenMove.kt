package fr.jhelp.composeclean.game

import android.util.Log
import fr.jhelp.compose.engine.scene.Scene3D
import fr.jhelp.compose.engine.scene.geometry.Box
import fr.jhelp.compose.engine.view.joystick.VirtualJoystickDirection
import fr.jhelp.compose.engine.view.joystick.VirtualJoystickType
import fr.jhelp.compose.engine.view.overlay.OverlayScreen
import fr.jhelp.compose.engine.view.overlay.OverlayScreenJoystick
import fr.jhelp.tasks.extensions.observedBy
import fr.jhelp.tasks.future.FutureResult

/**
 * Screen for move hero
 */
class GameScreenMove(scene3D: Scene3D) : GameScreen(scene3D)
{
    private val box = Box()
    private val overlayJoystick = OverlayScreenJoystick(VirtualJoystickType.GREEN)
    private var joystickListener: FutureResult<Unit>? = null
    override val overlay: OverlayScreen = this.overlayJoystick

    override fun attached()
    {
        this.joystickListener?.cancel("attached")
        this.joystickListener = this.overlayJoystick.direction.observedBy(action = this::joystick)
        this.overlayJoystick.click = this::click
        this.scene.root.position.z = -2f
        this.scene.root.add(this.box)
    }

    override fun detached()
    {
        this.joystickListener?.cancel("detached")
    }

    private fun joystick(joystickDirection: VirtualJoystickDirection)
    {
        Log.d("REMOVE_ME", "Direction = $joystickDirection")
    }

    private fun click()
    {
        Log.d("REMOVE_ME", "CLICK !")
    }
}