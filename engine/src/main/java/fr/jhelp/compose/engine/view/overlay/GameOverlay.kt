package fr.jhelp.compose.engine.view.overlay

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.Bitmap
import android.graphics.Canvas
import android.os.SystemClock
import android.util.AttributeSet
import android.view.MotionEvent
import android.view.View
import fr.jhelp.compose.engine.view.CLICK_TIME
import fr.jhelp.android.library.images.bitmap
import fr.jhelp.android.library.images.clear
import fr.jhelp.android.library.images.draw
import fr.jhelp.android.library.images.fitRectangle
import fr.jhelp.android.library.math.square
import fr.jhelp.android.library.tasks.TaskType
import fr.jhelp.android.library.tasks.extensions.parallel
import java.util.concurrent.atomic.AtomicBoolean
import kotlin.math.sqrt


/**
 * Overlay for draw things over the 3D
 */
class GameOverlay(context: Context, attributes: AttributeSet? = null) :
        View(context, attributes)
{
    private var overlayScreen: OverlayScreen = DummyOverlayScreen
    private var overlayWidth = 1
    private var overlayHeight = 1
    private var bitmap: Bitmap = Bitmap.createBitmap(1, 1, Bitmap.Config.ARGB_8888)
    private val refreshing = AtomicBoolean(false)
    private val shouldRefreshAgain = AtomicBoolean(false)
    private val refreshListener: () -> Unit = { this.refreshRequest(false) }
    private var touchX = 0f
    private var touchY = 0f
    private var lastTouchTime = 0L
    private val shouldResize = AtomicBoolean(false)

    /**
     * Called when view is draw
     */
    override fun onDraw(canvas: Canvas)
    {
        canvas.bitmap(this.bitmap, 0f, 0f)
    }

    /**
     * Called when view size changed
     */
    override fun onSizeChanged(w: Int, h: Int, oldw: Int, oldh: Int)
    {
        if (w != this.overlayWidth || h != this.overlayHeight)
        {
            this.overlayWidth = w
            this.overlayHeight = h
            this.updateOverlaySize()
        }
    }

    /**
     * Called when user touch the overlay
     */
    @SuppressLint("ClickableViewAccessibility")
    override fun onTouchEvent(event: MotionEvent): Boolean
    {
        var clicked = false
        val x = event.x
        val y = event.y

        when (event.action)
        {
            MotionEvent.ACTION_DOWN ->
            {
                this.lastTouchTime = SystemClock.elapsedRealtime()
                this.overlayScreen.touchDown(x, y)
            }

            MotionEvent.ACTION_MOVE ->
                this.overlayScreen.touchMove(this.touchX, this.touchY, x, y)

            MotionEvent.ACTION_UP   ->
            {
                clicked = (SystemClock.elapsedRealtime() - this.lastTouchTime) <= CLICK_TIME
                        && sqrt(square(x - this.touchX) + square(y - this.touchY)) <= event.size
                this.overlayScreen.touchUp(x, y)
            }
        }

        this.touchX = x
        this.touchY = y

        if (clicked)
        {
            this.overlayScreen.touchClick(x, y)
        }

        return true
    }

    internal fun screen(overlayScreen: OverlayScreen)
    {
        this.overlayScreen = overlayScreen
        overlayScreen.registerRefresh(this.refreshListener)
        overlayScreen.size(this.overlayWidth, this.overlayHeight)
        this.refreshRequest(true)
    }

    private fun updateOverlaySize()
    {
        val bitmap =
            Bitmap.createBitmap(this.overlayWidth, this.overlayHeight, Bitmap.Config.ARGB_8888)
        bitmap.draw { canvas, _ ->
            canvas.fitRectangle(this.bitmap, 0, 0, this.overlayWidth, this.overlayHeight)
        }
        this.bitmap.recycle()
        this.bitmap = bitmap
        this.refreshRequest(true)
    }

    private fun refreshRequest(resized: Boolean)
    {
        if (this.refreshing.compareAndSet(false, true))
        {
            this::refresh.parallel(resized)
        }
        else
        {
            if (resized)
            {
                this.shouldResize.set(true)
            }

            this.shouldRefreshAgain.set(true)
        }
    }

    private fun refresh(resized: Boolean)
    {
        if (resized)
        {
            this.overlayScreen.size(this.overlayWidth, this.overlayHeight)
            this.shouldResize.set(false)
        }

        this.bitmap.clear(0)
        this.bitmap.draw { canvas, paint ->
            this.overlayScreen.draw(canvas, paint)
        }

        this.postInvalidate()

        if (this.shouldRefreshAgain.compareAndSet(true, false))
        {
            TaskType.SHORT_TASK.delay(32L) { this.refresh(this.shouldResize.get()) }
            return
        }

        this.refreshing.set(false)
    }
}
