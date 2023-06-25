package fr.jhelp.android.library.engine.view.overlay

import android.graphics.Canvas
import android.graphics.Paint
import android.graphics.RectF
import fr.jhelp.android.library.engine.view.joystick.VirtualJoystick
import fr.jhelp.android.library.engine.view.joystick.VirtualJoystickDirection
import fr.jhelp.android.library.engine.view.joystick.VirtualJoystickType
import fr.jhelp.android.library.math.radianToDegree
import fr.jhelp.android.library.math.square
import fr.jhelp.android.library.tasks.TaskType
import fr.jhelp.android.library.tasks.future.FutureResult
import kotlin.math.atan2
import kotlin.math.min
import kotlin.math.sqrt
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

/**
 * Overlay screen with joystick
 */
class OverlayScreenJoystick(private val joystickType: VirtualJoystickType) : OverlayScreen
{
    private var baseSize = 1f
    private var buttonSize = 1f
    private var width = 1f
    private var height = 1f
    private var x = 0f
    private var y = 0f
    private var deltaX = 0f
    private var deltaY = 0f
    private var visible = false
    private var futureVisibility: FutureResult<Unit>? = null
    private val directionMutableState =
        MutableStateFlow<VirtualJoystickDirection>(VirtualJoystickDirection.NONE)

    /** Observable of direction change */
    val direction: StateFlow<VirtualJoystickDirection> = this.directionMutableState.asStateFlow()

    /** Action do on click */
    var click: () -> Unit = {}

    private var refresh: () -> Unit = {}

    override fun registerRefresh(refresh: () -> Unit)
    {
        this.refresh = refresh
    }

    override fun size(width: Int, height: Int)
    {
        this.width = width.toFloat()
        this.height = height.toFloat()
        this.baseSize = min(this.width, this.height) / 4f
        this.buttonSize = this.baseSize / 2f
    }

    override fun draw(canvas: Canvas, paint: Paint)
    {
        if (this.visible)
        {
            canvas.drawBitmap(this.joystickType.baseImage, null,
                              RectF(this.x - this.baseSize / 2f, this.y - this.baseSize / 2f,
                                    this.x + this.baseSize / 2f, this.y + this.baseSize / 2f),
                              paint)
            canvas.drawBitmap(this.joystickType.buttonImage, null,
                              RectF(this.x + this.deltaX - this.buttonSize / 2f,
                                    this.y + this.deltaY - this.buttonSize / 2f,
                                    this.x + this.deltaX + this.buttonSize / 2f,
                                    this.y + this.deltaY + this.buttonSize / 2f),
                              paint)
        }
    }

    override fun touchDown(x: Float, y: Float)
    {
        this.directionMutableState.value = VirtualJoystickDirection.NONE
        this.futureVisibility?.cancel("Start again")
        this.x = x
        this.y = y
        this.deltaX = 0f
        this.deltaY = 0f
        this.visible = true
        this.refresh()
    }

    override fun touchUp(x: Float, y: Float)
    {
        this.directionMutableState.value = VirtualJoystickDirection.NONE
        this.deltaX = 0f
        this.deltaY = 0f
        this.refresh()
        this.futureVisibility =
            TaskType.SHORT_TASK.delay(VirtualJoystick.VISIBILITY_TIME) {
                this.visible = false
                this.refresh()
            }
    }

    override fun touchMove(xPrevious: Float, yPrevious: Float, x: Float, y: Float)
    {
        val distance = sqrt(square(this.x - x) + square(this.y - y))

        if (distance < this.buttonSize)
        {
            this.deltaX = x - this.x
            this.deltaY = y - this.y
            this.directionMutableState.value = VirtualJoystickDirection.NONE
        }
        else
        {
            this.deltaX = ((x - this.x) * this.buttonSize) / distance
            this.deltaY = ((y - this.y) * this.buttonSize) / distance
            // + 180 to have angle in [0, 360[
            // + 22.5 for round when divide by 45
            // 8 and 0 are same direction, that's why we do % 8
            val direction =
                ((radianToDegree(atan2(this.y - y,
                                       this.x - x))
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

        this.refresh()
    }

    override fun touchClick(x: Float, y: Float)
    {
        this.click()
    }
}
