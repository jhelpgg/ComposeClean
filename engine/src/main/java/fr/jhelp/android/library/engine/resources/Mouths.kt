/*
 *  <h1>License :</h1> <br/>
 * The following code is deliver as is. <br/>
 *  You can use, modify, the code as your need for any usage.<br/>
 *  But you can't do any action that avoid me or other person use, modify this code.<br/>
 *  The code is free for usage and modification, you can't change that fact.
 */

package fr.jhelp.android.library.engine.resources

import android.graphics.Bitmap
import androidx.annotation.DrawableRes
import fr.jhelp.android.library.engine.R
import fr.jhelp.android.library.engine.scene.Texture

/**
 * Mouths texture. Used by [fr.jhelp.android.library.engine.scene.geometry.robot.Robot]'s [fr.jhelp.android.library.engine.scene.geometry.robot.Head]
 */
enum class Mouths(@DrawableRes private val drawableId: Int)
{
    /** Annoy 1 style */
    ANNOY_1(R.drawable.annoy1),

    /** Annoy 2 style */
    ANNOY_2(R.drawable.annoy2),

    /** Annoy 3 style */
    ANNOY_3(R.drawable.annoy3),

    /** Big mouth 1 style */
    ANNOY_BIG_1(R.drawable.annoy_big1),

    /** Big mouth 2 style */
    ANNOY_BIG_2(R.drawable.annoy_big2),

    /** Big mouth 3 style */
    ANNOY_BIG_3(R.drawable.annoy_big3),

    /** Semi mouth 1 style */
    ANNOY_SEMI_1(R.drawable.annoy_semi1),

    /** Semi mouth 2 style */
    ANNOY_SEMI_2(R.drawable.annoy_semi2),

    /** Semi mouth 3 style */
    ANNOY_SEMI_3(R.drawable.annoy_semi3),

    /** Sad 1 style */
    SAD_1(R.drawable.sad1),

    /** Sad 2 style */
    SAD_2(R.drawable.sad2),

    /** Sad 3 style */
    SAD_3(R.drawable.sad3),

    /** Serious 1 style */
    SERIOUS_1(R.drawable.serious1),

    /** Serious 2 style */
    SERIOUS_2(R.drawable.serious2),

    /** Serious 3 style */
    SERIOUS_3(R.drawable.serious3),

    /** Smile 1 style */
    SMILE_1(R.drawable.smile1),

    /** Smile 2 style */
    SMILE_2(R.drawable.smile2),

    /** Smile 3 style */
    SMILE_3(R.drawable.smile3),

    /** Smile big 1 style */
    SMILE_BIG_1(R.drawable.smile_big1),

    /** Smile big 2 style */
    SMILE_BIG_2(R.drawable.smile_big2),

    /** Smile big 3 style */
    SMILE_BIG_3(R.drawable.smile_big3),

    /** Other 1 style */
    SMILE_OTHER_1(R.drawable.smile_other1),

    /** Other 2 style */
    SMILE_OTHER_2(R.drawable.smile_other2),

    /** Other 3 style */
    SMILE_OTHER_3(R.drawable.smile_other3),

    /** Whisper 1 style */
    WHISPER_1(R.drawable.whisper1),

    /** Whisper 2 style */
    WHISPER_2(R.drawable.whisper2),

    /** Whisper 3 style */
    WHISPER_3(R.drawable.whisper3)
    ;

    /** Associated texture */
    val texture: Texture by lazy { ResourcesAccess.obtainTexture(this.drawableId) }

    /** Associated image */
    fun bitmap(): Bitmap = ResourcesAccess.obtainBitmap(this.drawableId)
}