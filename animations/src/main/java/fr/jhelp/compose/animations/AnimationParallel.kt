package fr.jhelp.compose.animations

/**
 * Launch several animations in "same time"
 */
class AnimationParallel : Animation(25)
{
    private val animations = ArrayList<Animation>()
    private var length = 0

    fun add(animation: Animation)
    {
        synchronized(this.animations)
        {
            this.animations.add(animation)
        }
    }

    override fun initialize()
    {
        synchronized(this.animations)
        {
            this.length = this.animations.size

            for (index in 0 until this.length)
            {
                this.animations[index].start()
            }
        }
    }

    override fun animate(frame: Float): Boolean
    {
        var animate = false

        for (index in 0 until this.length)
        {
            animate = this.animations[index].animate() || animate
        }

        return animate
    }
}