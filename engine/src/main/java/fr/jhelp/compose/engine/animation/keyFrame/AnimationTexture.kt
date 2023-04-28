package fr.jhelp.compose.engine.animation.keyFrame

import androidx.annotation.DrawableRes
import fr.jhelp.compose.animations.Animation
import fr.jhelp.compose.animations.bitmap.AnimationBitmap
import fr.jhelp.compose.animations.bitmap.AnimationBitmapTransition
import fr.jhelp.compose.animations.interpolation.Interpolation
import fr.jhelp.compose.animations.interpolation.LinearInterpolation
import fr.jhelp.compose.engine.resources.ResourcesAccess
import fr.jhelp.compose.engine.resources.texture
import fr.jhelp.compose.engine.scene.Texture

/**
 * Animated texture that do transitions between images.
 * Can decide how many frames it takes for transition and interpolation type to use
 * @property size Animation texture size
 */
class AnimationTexture(@DrawableRes firstImage: Int,
                       val size: AnimationTextureSize = AnimationTextureSize.MEDIUM) :
        Animation(25)
{
    private val animationBitmap: AnimationBitmap =
        AnimationBitmap(ResourcesAccess.obtainBitmap(firstImage, this.size.size, this.size.size),
                        this.size.size, this.size.size, 25)

    /**Animated texture to apply to object*/
    val texture: Texture = texture(this.animationBitmap.resultBitmap, false)

    /**
     * Add transition image
     * @param numberFrame Number frames to go to the given image
     * @param image Target image
     * @param transition Transition type to use
     * @param interpolation Interpolation to use
     */
    fun appendTransitionIn(numberFrame: Int, @DrawableRes image: Int,
                           transition: AnimationBitmapTransition = AnimationBitmapTransition.MELT,
                           interpolation: Interpolation = LinearInterpolation)
    {
        this.animationBitmap.appendTransitionIn(numberFrame,
                                                ResourcesAccess.obtainBitmap(image,
                                                                             this.size.size,
                                                                             this.size.size),
                                                transition, interpolation)
    }

    override fun animate(frame: Float): Boolean
    {
        val stillAlive = this.animationBitmap.setAnimateFrame(frame)
        this.texture.refresh()
        return stillAlive
    }
}