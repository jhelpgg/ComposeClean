import fr.jhelp.android.library.engine.view.View3D
import fr.jhelp.android.library.engine.view.joystick.VirtualJoystick
import fr.jhelp.android.library.engine.view.joystick.VirtualJoystickDirection
import fr.jhelp.android.library.engine.view.joystick.VirtualJoystickType
import fr.jhelp.android.library.engine.view.touch.View3DTouchOverSensitive
import fr.jhelp.android.library.tasks.TaskType
import fr.jhelp.android.library.tasks.extensions.observedBy

fun sceneReady(view3D: View3D)
{
    val virtualJoystick = VirtualJoystick(VirtualJoystickType.GREEN)
    view3D.view3DTouchAction = View3DTouchOverSensitive(virtualJoystick)

    virtualJoystick.click = ::clickAction
    virtualJoystick.direction.observedBy(TaskType.SHORT_TASK, ::directionChanged)
}

private fun clickAction()
{
    // ...
}

private fun directionChanged(direction: VirtualJoystickDirection)
{
    when(direction)
    {
        // ...
    }
}

