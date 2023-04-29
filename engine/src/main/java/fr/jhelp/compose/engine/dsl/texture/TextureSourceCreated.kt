package fr.jhelp.compose.engine.dsl.texture

import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.Paint
import fr.jhelp.compose.engine.resources.draw
import fr.jhelp.compose.engine.resources.texture
import fr.jhelp.compose.engine.scene.Texture

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