package fr.jhelp.compose.engine.view

import android.annotation.SuppressLint
import android.content.Context
import android.opengl.GLSurfaceView
import android.os.SystemClock
import android.util.AttributeSet
import android.view.MotionEvent
import fr.jhelp.compose.engine.scene.Node3D
import fr.jhelp.compose.engine.view.touch.View3DTouchAction
import fr.jhelp.compose.engine.view.touch.View3DTouchManipulation
import fr.jhelp.compose.math.Point3D
import fr.jhelp.tasks.TaskType
import java.util.concurrent.atomic.AtomicBoolean
import kotlin.math.max
import kotlinx.coroutines.flow.StateFlow

/**
 * View with OpenGL inside.
 *
 * It shows the 3D
 */
class View3D(context: Context, attributes: AttributeSet? = null) :
        GLSurfaceView(context, attributes)
{
    private val alive = AtomicBoolean(true)
    private val renderer = View3DRenderer(this::refreshDone)
    val viewBoundsState: StateFlow<ViewBounds> = this.renderer.viewBoundsState
    private var startRefreshTime = 0L
    var minimumAngleY = Float.NEGATIVE_INFINITY
    var maximumAngleY = Float.POSITIVE_INFINITY
    var minimumAngleX = Float.NEGATIVE_INFINITY
    var maximumAngleX = Float.POSITIVE_INFINITY
    var minimumZ = -9f
    var maximumZ = -1f
    var view3DTouchAction: View3DTouchAction = View3DTouchManipulation
        set(value)
        {
            field.detachFrom(this)
            value.attachTo(this)
            field = value
        }

    /**
     * Last touch X position
     */
    private var touchX = 0f

    /**
     * Last touch Y position
     */
    private var touchY = 0f

    private var numberFinger = 0
    private var touchX1 = 0f
    private var touchY1 = 0f
    private var touchX2 = 0f
    private var touchY2 = 0f

    /**
     * Scene draw on the view
     */
    val scene3D = this.renderer.scene3D

    /**
     * Current manipulated node
     */
    var manipulateNode: Node3D = this.scene3D.root

    init
    {
        this.setRenderer(this.renderer)
        // Render the view only when there is a change
        this.renderMode = RENDERMODE_WHEN_DIRTY
        TaskType.SHORT_TASK.delay(1024L) {
            this@View3D.refreshScene()
        }

        this.view3DTouchAction.attachTo(this)
    }

    override fun onDetachedFromWindow()
    {
        this.alive.set(false)
        super.onDetachedFromWindow()
    }

    /**
     * Manipulate the scene root
     */
    fun manipulateRoot()
    {
        this.manipulateNode = this.scene3D.root
    }

    /**
     * Convert view coordinates to 3D coordinates.
     *
     * The `Z` value is fixed by the caller
     */
    fun screenCoordinateTo3D(xScreen: Float, yScreen: Float, zFix: Float): Point3D
    {
        val bounds = this.viewBoundsState.value
        val topLeftNear = bounds.topLeftNear
        val bottomRightFar = bounds.bottomRightFar
        val topLeft = bounds.topLet
        val bottomRight = bounds.bottomRight
        val width3D: Float = bottomRightFar.x - topLeftNear.x
        val height3D: Float = bottomRightFar.y - topLeftNear.y
        val widthView: Float = bottomRight.x - topLeft.x
        val heightView: Float = bottomRight.y - topLeft.y
        return Point3D(topLeftNear.x + (width3D * xScreen) / widthView,
                       topLeftNear.y + (height3D * yScreen) / heightView,
                       zFix)
    }

    @SuppressLint("ClickableViewAccessibility")
    override fun onTouchEvent(event: MotionEvent): Boolean
    {
        when (event.pointerCount)
        {
            1 -> this.oneFingerTouchEvent(event)
            2 -> this.twoFingersTouchEvent(event)
        }

        return true
    }

    private fun oneFingerTouchEvent(event: MotionEvent)
    {
        this.numberFinger = 1
        val x = event.x
        val y = event.y

        when (event.action)
        {
            MotionEvent.ACTION_DOWN ->
                this.view3DTouchAction.oneFingerDown(x, y)

            MotionEvent.ACTION_MOVE ->
                this.view3DTouchAction.oneFingerMove(this.touchX, this.touchY, x, y)

            MotionEvent.ACTION_UP   ->
            {
                this.numberFinger = 0

                if (this.view3DTouchAction.oneFingerUp(x, y) && this.hasOnClickListeners())
                {
                    this.callOnClick()
                }
            }
        }

        this.touchX = x
        this.touchY = y
    }

    private fun twoFingersTouchEvent(event: MotionEvent)
    {
        if (this.numberFinger == 1)
        {
            this.touchX1 = this.touchX
            this.touchY1 = this.touchY
        }

        this.numberFinger = 2

        val x1 = event.getX(0)
        val y1 = event.getY(0)
        val x2 = event.getX(1)
        val y2 = event.getY(1)

        when (event.action)
        {
            MotionEvent.ACTION_DOWN ->
                this.view3DTouchAction.twoFingersDown(x1, y1,
                                                      x2, y2)

            MotionEvent.ACTION_MOVE ->
                this.view3DTouchAction.twoFingersMove(this.touchX1, this.touchY1, x1, y1,
                                                      this.touchX2, this.touchY2, x2, y2)

            MotionEvent.ACTION_UP   ->
            {
                this.numberFinger = 0

                this.view3DTouchAction.twoFingersUp(x1, y1,
                                                    x2, y2)
            }
        }

        this.touchX1 = x1
        this.touchY1 = y1
        this.touchX2 = x2
        this.touchY2 = y2
    }

    private fun refreshScene()
    {
        if (!this.alive.get())
        {
            return
        }

        this.startRefreshTime = SystemClock.elapsedRealtime()
        this.requestRender()
    }

    private fun refreshDone()
    {
        if (!this.alive.get())
        {
            return
        }

        val timeLeft = max(1L, 32L - SystemClock.elapsedRealtime() + this.startRefreshTime)
        TaskType.SHORT_TASK.delay(timeLeft) {
            this@View3D.refreshScene()
        }
    }
}