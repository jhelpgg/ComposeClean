package fr.jhelp.compose.animations

import java.util.concurrent.atomic.AtomicReference

/**
 * Animation that start with an animation, then loop on an other one until [endLoop] is called, then call an end animation
 */
class AnimationLoop(private val startAnimation: Animation,
                    private val loopAnimation: Animation,
                    private val endAnimation: Animation) : Animation(25)
{
    private val status = AtomicReference(
        AnimationLoopStatus.START)

    /**
     * Create animation with only a loop
     */
    constructor(loopAnimation: Animation) :
            this(AnimationPause(1), loopAnimation, AnimationPause(1))

    override fun initialize()
    {
        when (this.status.get())
        {
            AnimationLoopStatus.START ->
                this.startAnimation.finished()
            AnimationLoopStatus.LOOP  ->
                this.loopAnimation.finished()
            else                      ->
                this.endAnimation.finished()
        }

        this.status.set(AnimationLoopStatus.START)
        this.startAnimation.start()
    }

    override fun animate(frame: Float): Boolean
    {
        when (this.status.get())
        {
            AnimationLoopStatus.START ->
                if (!this.startAnimation.animate())
                {
                    this.startAnimation.finished()
                    this.loopAnimation.start()
                    this.status.set(AnimationLoopStatus.LOOP)
                }
            AnimationLoopStatus.LOOP  ->
                if (!this.loopAnimation.animate())
                {
                    this.loopAnimation.finished()
                    this.loopAnimation.start()
                }
            AnimationLoopStatus.END   ->
                if (!this.endAnimation.animate())
                {
                    this.endAnimation.finished()
                    this.status.set(
                        AnimationLoopStatus.START)
                    return false
                }
            else                      -> Unit
        }

        return true
    }

    /**
     * End animation loop
     */
    fun endLoop()
    {
        when (this.status.get())
        {
            AnimationLoopStatus.START ->
            {
                this.startAnimation.finished()
                this.endAnimation.start()
            }
            AnimationLoopStatus.LOOP  ->
            {
                this.loopAnimation.finished()
                this.endAnimation.start()
            }
            else                      -> Unit
        }

        this.status.set(AnimationLoopStatus.END)
    }
}