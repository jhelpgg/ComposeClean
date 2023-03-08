/*
 *  <h1>License :</h1> <br/>
 * The following code is deliver as is. <br/>
 *  You can use, modify, the code as your need for any usage.<br/>
 *  But you can't do any action that avoid me or other person use, modify this code.<br/>
 *  The code is free for usage and modification, you can't change that fact.
 */

package fr.jhelp.compose.engine.resources

import androidx.annotation.DrawableRes
import fr.jhelp.compose.engine.R

/**
 * Eyes texture. Used by [fr.jhelp.compose.engine.scene.geometry.robot.Robot]'s [fr.jhelp.compose.engine.scene.geometry.robot.Head]
 */
enum class Eyes(@DrawableRes private val drawableId: Int)
{
    BLUE(R.drawable.eye_blue),
    BLUE_2(R.drawable.eye_blue2),
    BLUE_3(R.drawable.eye_blue3),
    BROWN(R.drawable.eye_brown),
    BROWN_2(R.drawable.eye_brown2),
    GREEN(R.drawable.eye_green),
    GREEN_2(R.drawable.eye_green2),
    GREEN_3(R.drawable.eye_green3),
    GREEN_SHINE(R.drawable.eye_green_shine),
    LIGHT_RED(R.drawable.eye_light_red),
    PINK(R.drawable.eye_pink),
    PURPLE(R.drawable.eye_purple),
    RED(R.drawable.eye_red),
    RED_2(R.drawable.eye_red2),
    RED_3(R.drawable.eye_red3),
    TONE_BLUE(R.drawable.eye_tone_blue),
    TONE_RED(R.drawable.eye_tone_red)
    ;

    val texture by lazy { ResourcesAccess.obtainTexture(this.drawableId) }

    fun bitmap() = ResourcesAccess.obtainBitmap(this.drawableId, 64, 64)

}