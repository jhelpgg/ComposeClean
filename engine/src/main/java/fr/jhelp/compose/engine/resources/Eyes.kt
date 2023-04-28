/*
 *  <h1>License :</h1> <br/>
 * The following code is deliver as is. <br/>
 *  You can use, modify, the code as your need for any usage.<br/>
 *  But you can't do any action that avoid me or other person use, modify this code.<br/>
 *  The code is free for usage and modification, you can't change that fact.
 */

package fr.jhelp.compose.engine.resources

import android.graphics.Bitmap
import androidx.annotation.DrawableRes
import fr.jhelp.compose.engine.R
import fr.jhelp.compose.engine.scene.Texture

/**
 * Eyes texture. Used by [fr.jhelp.compose.engine.scene.geometry.robot.Robot]'s [fr.jhelp.compose.engine.scene.geometry.robot.Head]
 */
enum class Eyes(@DrawableRes private val drawableId: Int)
{
    /** Blue eye */
    BLUE(R.drawable.eye_blue),

    /** Blue 2 eye */
    BLUE_2(R.drawable.eye_blue2),

    /** Blue 3 eye */
    BLUE_3(R.drawable.eye_blue3),

    /** Brown dark eye */
    BROWN(R.drawable.eye_brown),

    /** Brown light eye */
    BROWN_2(R.drawable.eye_brown2),

    /** Green eye */
    GREEN(R.drawable.eye_green),

    /** Green 2 eye */
    GREEN_2(R.drawable.eye_green2),

    /** Green 3 eye */
    GREEN_3(R.drawable.eye_green3),

    /** Green shine eye */
    GREEN_SHINE(R.drawable.eye_green_shine),

    /** Red light eye */
    LIGHT_RED(R.drawable.eye_light_red),

    /** Pink eye */
    PINK(R.drawable.eye_pink),

    /** Purple light eye */
    PURPLE(R.drawable.eye_purple),

    /** Red eye */
    RED(R.drawable.eye_red),

    /** Red 2 eye */
    RED_2(R.drawable.eye_red2),

    /** Red 3 eye */
    RED_3(R.drawable.eye_red3),

    /** Tone blue eye */
    TONE_BLUE(R.drawable.eye_tone_blue),

    /** Tone red eye */
    TONE_RED(R.drawable.eye_tone_red)
    ;

    /** Eye as texture */
    val texture: Texture by lazy { ResourcesAccess.obtainTexture(this.drawableId) }

    /** Eye as bitmap */
    fun bitmap(): Bitmap = ResourcesAccess.obtainBitmap(this.drawableId, 64, 64)

}