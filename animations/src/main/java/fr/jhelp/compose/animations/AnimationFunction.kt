package fr.jhelp.compose.animations

/**
 * Animation based on a function that consumes animation current frame
 */
class AnimationFunction(fps: Int, private val function: (Float) -> Boolean) : Animation(fps)
{
    constructor(function: (Float) -> Boolean) : this(25, function)

    override fun animate(frame: Float) = this.function(frame)
}