package fr.jhelp.compose.engine.view.joystick

import android.graphics.Canvas
import android.graphics.Paint
import fr.jhelp.compose.engine.view.touch.View3DTouchOverListener
import fr.jhelp.compose.images.center
import fr.jhelp.compose.math.radianToDegree
import fr.jhelp.compose.math.sqrt
import fr.jhelp.compose.math.square
import fr.jhelp.android.library.tasks.TaskType
import fr.jhelp.android.library.tasks.future.FutureResult
import kotlin.math.atan2
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

/**
 * Virtual joystick
 * @property type Virtual joystick type
 */
class VirtualJoystick(val type: VirtualJoystickType) : View3DTouchOverListener
{
    companion object
    {
        /** Size of joystick base */
        const val BASE_SIZE: Int = 128

        /** Size of joystick button */
        const val BUTTON_SIZE: Int = VirtualJoystick.BASE_SIZE / 2

        /** Time left joystick visible after finger up the overlay */
        const val VISIBILITY_TIME: Long = 512L
    }

    private var x = 0
    private var y = 0
    private var deltaX = 0
    private var deltaY = 0
    private var visible = false
    private var futureVisibility: FutureResult<Unit>? = null
    private var dirtyListener: () -> Unit = {}
    private val directionMutableState =
        MutableStateFlow<VirtualJoystickDirection>(VirtualJoystickDirection.NONE)

    /** Observable of direction change */
    val direction: StateFlow<VirtualJoystickDirection> = this.directionMutableState.asStateFlow()

    /** Action do on click */
    var click: () -> Unit = {}

    override fun dirtyListener(dirty: () -> Unit)
    {
        this.dirtyListener = dirty
    }

    override fun onRefresh(canvas: Canvas, paint: Paint)
    {
        if (this.visible)
        {
            canvas.center(this.type.baseImage, this.x.toFloat(), this.y.toFloat())
            canvas.center(this.type.buttonImage,
                          this.x.toFloat() + this.deltaX.toFloat(),
                          this.y.toFloat() + this.deltaY.toFloat())
        }
    }

    override fun onDown(x: Int, y: Int)
    {
        this.directionMutableState.value = VirtualJoystickDirection.NONE
        this.futureVisibility?.cancel("Start again")
        this.x = x
        this.y = y
        this.deltaX = 0
        this.deltaY = 0
        this.visible = true
    }

    override fun onUp(x: Int, y: Int)
    {
        this.directionMutableState.value = VirtualJoystickDirection.NONE
        this.deltaX = 0
        this.deltaY = 0
        this.futureVisibility =
            TaskType.SHORT_TASK.delay(VirtualJoystick.VISIBILITY_TIME) {
                this.visible = false
                this.dirtyListener()
            }
    }

    override fun onClick(x: Int, y: Int)
    {
        this.click()
    }

    override fun onMove(previousX: Int, previousY: Int, x: Int, y: Int)
    {
        val distance = sqrt(square(this.x - x) + square(this.y - y))

        if (distance < VirtualJoystick.BUTTON_SIZE)
        {
            this.deltaX = x - this.x
            this.deltaY = y - this.y
            this.directionMutableState.value = VirtualJoystickDirection.NONE
        }
        else
        {
            this.deltaX = ((x - this.x) * VirtualJoystick.BUTTON_SIZE) / distance
            this.deltaY = ((y - this.y) * VirtualJoystick.BUTTON_SIZE) / distance
            // + 180 to have angle in [0, 360[
            // + 22.5 for round when divide by 45
            // 8 and 0 are same direction, that's why we do % 8
            val direction =
                ((radianToDegree(atan2(this.y.toFloat() - y.toFloat(),
                                       this.x.toFloat() - x.toFloat()))
                        + 180f + 22.5f) / 45f).toInt() % 8

            when (direction)
            {
                0 -> this.directionMutableState.value = VirtualJoystickDirection.RIGHT
                1 -> this.directionMutableState.value = VirtualJoystickDirection.DOWN_RIGHT
                2 -> this.directionMutableState.value = VirtualJoystickDirection.DOWN
                3 -> this.directionMutableState.value = VirtualJoystickDirection.DOWN_LEFT
                4 -> this.directionMutableState.value = VirtualJoystickDirection.LEFT
                5 -> this.directionMutableState.value = VirtualJoystickDirection.UP_LEFT
                6 -> this.directionMutableState.value = VirtualJoystickDirection.UP
                7 -> this.directionMutableState.value = VirtualJoystickDirection.UP_RIGHT
            }
        }
    }
}