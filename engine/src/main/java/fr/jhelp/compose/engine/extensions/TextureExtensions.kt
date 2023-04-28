package fr.jhelp.compose.engine.extensions

import android.graphics.Canvas
import android.graphics.Paint
import fr.jhelp.compose.engine.scene.Texture

inline fun Texture.draw(draw: (Canvas, Paint) -> Unit)
{
    val canvas = this.canvas()
    val paint = this.paint()

    if (canvas != null && paint != null)
    {
        draw(canvas, paint)
    }
}