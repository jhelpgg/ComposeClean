package fr.jhelp.compose.animations

/**
 * Animation based on a function that consumes animation current frame
 *
 * The function take the current animation frame and returns a `boolean`.
 * If the returned `boolean` is `true` the animation continue.
 * If it is `false` that indicates the animation is finished
 */
class AnimationFunction(fps: Int, private val function: (Float) -> Boolean) : Animation(fps)
{
    constructor(function: (Float) -> Boolean) : this(25, function)

    override fun animate(frame: Float) = this.function(frame)
}