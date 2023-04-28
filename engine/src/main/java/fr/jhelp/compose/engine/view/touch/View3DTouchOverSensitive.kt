package fr.jhelp.compose.engine.view.touch

import android.graphics.Point
import android.os.SystemClock
import fr.jhelp.compose.engine.resources.texture
import fr.jhelp.compose.engine.scene.Texture
import fr.jhelp.compose.engine.view.CLICK_TIME
import fr.jhelp.compose.engine.view.DELTA_DISTANCE_BEFORE_CONSIDER_DIFFERENT
import fr.jhelp.compose.engine.view.View3D
import fr.jhelp.compose.engine.view.ViewBounds
import fr.jhelp.compose.images.clear
import fr.jhelp.compose.math.square
import fr.jhelp.tasks.extensions.observedBy
import fr.jhelp.tasks.extensions.parallel
import fr.jhelp.tasks.future.FutureResult
import kotlin.math.sqrt

class View3DTouchOverSensitive(private val view3DTouchOverListener: View3DTouchOverListener) :
        View3DTouchAction()
{
    companion object
    {
        const val WIDTH = 512
        const val HEIGHT = 512
    }

    private var future: FutureResult<Unit>? = null
    private lateinit var viewBounds: ViewBounds
    private var lastDownTime = 0L
    private var oldTexture: Texture? = null
    private val texture = texture(512, 512)
    private var touchX = -128f
    private var touchY = -128f

    init
    {
        this.view3DTouchOverListener.dirtyListener(this::refresh)
    }

    override fun attachTo(view3D: View3D)
    {
        this.future =
            view3D.viewBoundsState.observedBy { viewBounds -> this.viewBounds = viewBounds }
        this.oldTexture = view3D.scene3D.textureOver3D
        view3D.scene3D.textureOver3D = this.texture
        this.refresh()
    }

    override fun detachFrom(view3D: View3D)
    {
        this.future?.cancel("Detached")
        this.future = null
        view3D.scene3D.textureOver3D = this.oldTexture
        this.oldTexture = null
    }

    override fun oneFingerDown(x: Float, y: Float)
    {
        this.touchX = x
        this.touchY = y
        this.lastDownTime = SystemClock.elapsedRealtime()
        val point = this.convertTouchPositionToTexturePosition(x, y)
        this.view3DTouchOverListener.onDown(point.x, point.y)
        this.refresh()
    }

    override fun oneFingerUp(x: Float, y: Float): Boolean
    {
        val click = (SystemClock.elapsedRealtime() - this.lastDownTime) <= CLICK_TIME

        val point = this.convertTouchPositionToTexturePosition(x, y)
        this.view3DTouchOverListener.onUp(point.x, point.y)

        if (click && sqrt(square(x - this.touchX) + square(y - this.touchY)) < DELTA_DISTANCE_BEFORE_CONSIDER_DIFFERENT)
        {
            this.view3DTouchOverListener.onClick(point.x, point.y)
        }

        this.refresh()
        return click
    }

    override fun oneFingerMove(previousX: Float, previousY: Float, x: Float, y: Float)
    {
        val previousPoint = this.convertTouchPositionToTexturePosition(previousX, previousY)
        val point = this.convertTouchPositionToTexturePosition(x, y)
        this.view3DTouchOverListener.onMove(previousPoint.x, previousPoint.y, point.x, point.y)
        this.refresh()
    }

    override fun twoFingersDown(x1: Float, y1: Float,
                                x2: Float, y2: Float) = Unit

    override fun twoFingersUp(x1: Float, y1: Float,
                              x2: Float, y2: Float) = Unit

    override fun twoFingersMove(previousX1: Float, previousY1: Float, x1: Float, y1: Float,
                                previousX2: Float, previousY2: Float, x2: Float, y2: Float) = Unit

    private fun convertTouchPositionToTexturePosition(x: Float, y: Float): Point
    {
        val size = this.viewBounds.bottomRight

        if (size.x > 0f && size.y > 0f)
        {

            return Point(((x * View3DTouchOverSensitive.WIDTH.toFloat()) / size.x).toInt(),
                         ((y * View3DTouchOverSensitive.HEIGHT.toFloat() / size.y)).toInt())
        }

        return Point(x.toInt(), y.toInt())
    }

    /**
     * Force refresh/repaint the texture
     */
    private fun refresh()
    {
        {
            this.texture.bitmap()?.clear(0x00_00_00_00)
            val canvas = this.texture.canvas()!!
            val paint = this.texture.paint()!!
            this.view3DTouchOverListener.onRefresh(canvas, paint)
            this.texture.refresh()
        }.parallel()
    }
}