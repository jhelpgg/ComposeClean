/*
 *  <h1>License :</h1> <br/>
 * The following code is deliver as is. <br/>
 *  You can use, modify, the code as your need for any usage.<br/>
 *  But you can't do any action that avoid me or other person use, modify this code.<br/>
 *  The code is free for usage and modification, you can't change that fact.
 */

package fr.jhelp.compose.engine.scene.geometry.robot

import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.Paint
import android.graphics.Path
import fr.jhelp.compose.engine.R
import fr.jhelp.compose.engine.resources.Eyes
import fr.jhelp.compose.engine.resources.Mouths
import fr.jhelp.compose.engine.resources.ResourcesAccess
import fr.jhelp.compose.engine.resources.draw
import fr.jhelp.compose.engine.resources.texture
import fr.jhelp.compose.engine.scene.Texture
import fr.jhelp.compose.images.bitmap
import fr.jhelp.compose.images.clear
import fr.jhelp.compose.images.mask
import fr.jhelp.compose.images.tint
import fr.jhelp.android.library.math.COLOR_WHITE

private val hairPath by lazy {
    val path = Path()
    path.moveTo(0f, 0f)
    path.lineTo(512f, 0f)
    path.lineTo(512f, 512f)
    path.lineTo(342f, 512f)
    path.lineTo(333f, 256f)
    path.lineTo(322f, 300f)
    path.lineTo(342f, 170f)
    path.lineTo(333f, 150f)
    path.lineTo(321f, 160f)
    path.lineTo(300f, 170f)
    path.lineTo(250f, 155f)
    path.lineTo(170f, 170f)
    path.lineTo(150f, 250f)
    path.lineTo(160f, 300f)
    path.lineTo(170f, 512f)
    path.lineTo(0f, 512f)
    path.close()
    path
}

/**
 * Robot's head
 * @param leftEye Left eye image
 * @param rightEye Right eye image
 * @param mouth Mouth image
 * @param hair Hair color
 */
class Head(var leftEye: Eyes = Eyes.GREEN_2, var rightEye: Eyes = Eyes.GREEN_2,
           var mouth: Mouths = Mouths.SMILE_2,
           var hair: Int = 0xFFA0661C.toInt())
{
    /** Head texture */
    val texture: Texture = texture(512, 512)

    init
    {
        this.refresh()
    }

    /**
     * Refresh head to see last changes
     */
    fun refresh()
    {
        this.texture.draw { textureBitmap, canvas, paint ->
            textureBitmap.clear(COLOR_WHITE)
            paint.style = Paint.Style.FILL
            var bitmap = this.leftEye.bitmap()
            canvas.bitmap(bitmap, 271f, 192f)
            bitmap.recycle()
            bitmap = this.rightEye.bitmap()
            canvas.bitmap(bitmap, 177f, 192f)
            bitmap.recycle()
            bitmap = this.mouth.bitmap()
            canvas.bitmap(bitmap, 192f, 300f)
            bitmap.recycle()
            bitmap = ResourcesAccess.obtainBitmap(R.drawable.hair1, 512, 512)
            bitmap.tint(this.hair)
            val mask = Bitmap.createBitmap(512, 512, Bitmap.Config.ARGB_8888)
            mask.clear(0)
            val canvasMask = Canvas(mask)
            paint.color = 0xFF000000.toInt()
            canvasMask.drawPath(hairPath, paint)
            bitmap.mask(mask, 0, 0, 512, 512)
            canvas.bitmap(bitmap, 0f, 0f)
            bitmap.recycle()
            mask.recycle()
        }
    }
}