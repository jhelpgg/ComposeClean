package fr.jhelp.android.library.animations

/**
 * List of animation played one after other
 */
class AnimationList : Animation(25)
{
    private val animations = ArrayList<Animation>()
    private var started = false
    private var index = 0

    /**
     * Add animation on the list.
     *
     * If first add it will be the first animation played, else it will follow the previous added
     */
    fun add(animation: Animation)
    {
        synchronized(this.animations)
        {
            this.animations.add(animation)
        }
    }

    /**
     * Initialize the animation, to make it ready to play at start position
     */
    override fun initialize()
    {
        this.started = false
        this.index = 0
    }

    /**
     * Put the animation at current step
     *
     * @return `true`if animation should continue. `false` if animation reach the end
     */
    override fun animate(frame: Float): Boolean
    {
        val animation =
            synchronized(this.animations)
            {
                if (this.index >= this.animations.size)
                {
                    return false
                }

                this.animations[this.index]
            }

        if (!this.started)
        {
            this.started = true
            animation.start()
        }

        if (!animation.animate())
        {
            animation.finished()
            this.index++

            synchronized(this.animations)
            {
                if (this.index >= this.animations.size)
                {
                    return false
                }

                this.animations[this.index].start()
            }
        }

        return true
    }
}