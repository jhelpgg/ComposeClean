package fr.jhelp.android.library.animations

/**
 * Animation that make a pause
 */
class AnimationPause(milliseconds: Int) : Animation(25)
{
    private val numberFrame = milliseconds / 40f

    override fun animate(frame: Float): Boolean =
        frame <= this.numberFrame
}