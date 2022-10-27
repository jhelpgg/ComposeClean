package fr.jhelp.compose.animations

/**
 * List of animation played one after other
 */
class AnimationList() : Animation(25)
{
    private val animations = ArrayList<Animation>()
    private var started = false
    private var index = 0

    fun add(animation: Animation)
    {
        synchronized(this.animations)
        {
            this.animations.add(animation)
        }
    }

    override fun initialize()
    {
        this.started = false
        this.index = 0
    }

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