package fr.jhelp.compose.engine.dsl.texture

import android.graphics.Canvas
import android.graphics.Paint
import fr.jhelp.compose.engine.resources.texture
import fr.jhelp.compose.engine.scene.Texture

class TextureSourceCreated(private val width: Int,
                           private val height: Int,
                           private val draw: (Canvas, Paint) -> Unit) : TextureSource()
{
    override fun createTexture(): Texture
    {
        val texture = texture(this.width, this.height)
        val canvas = texture.canvas()
        val paint = texture.paint()

        if (canvas != null && paint != null)
        {
            this.draw(canvas, paint)
        }

        return texture
    }
}