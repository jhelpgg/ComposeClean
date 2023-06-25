package fr.jhelp.android.library.engine.resources.texture

import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.Paint
import fr.jhelp.android.library.engine.resources.draw
import fr.jhelp.android.library.engine.resources.texture
import fr.jhelp.android.library.engine.scene.Texture

/**
 * Texture draw by developer
 */
class TextureSourceCreated(private val width: Int,
                           private val height: Int,
                           private val draw: (Bitmap, Canvas, Paint) -> Unit) : TextureSource()
{
    override fun createTexture(): Texture =
        texture(this.width, this.height).draw(this.draw)!!
}