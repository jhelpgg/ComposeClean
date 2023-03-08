package fr.jhelp.compose.animations

import android.os.SystemClock
import fr.jhelp.compose.math.extensions.bounds

/**
 * A generic animation
 */
abstract class Animation(fps: Int)
{
    val fps = fps.bounds(1, 100)
    private var statTime = 0L

    /**
     * Called when animation about to start.
     *
     * Does nothing by default
     */
    protected open fun initialize() = Unit

    /**
     * Play animation for given frame.
     *
     * @return `true` if the animation continue. `false` if animation is finished
     */
    protected abstract fun animate(frame: Float): Boolean

    /**
     * Called when animation finished
     *
     * Does nothing by default
     */
    open fun finished() = Unit

    fun start()
    {
        this.statTime = SystemClock.uptimeMillis()
        this.initialize()
        this.animate(0f)
    }

    fun animate(): Boolean =
        this.animate(((SystemClock.uptimeMillis() - this.statTime) * this.fps) / 1000f)

    /**
     * Transform time to number of frame, depends on animation FPS
     */
    fun millisecondsToFrame(milliseconds: Int): Int = (milliseconds * this.fps) / 1000
}