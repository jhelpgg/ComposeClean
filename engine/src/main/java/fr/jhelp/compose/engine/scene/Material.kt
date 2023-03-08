package fr.jhelp.compose.engine.scene

import javax.microedition.khronos.opengles.GL10

/**
 * Material composed of alpha, diffuse and [Texture]
 */
class Material
{
    var alpha = 1f
    var diffuse = GREY
    var texture: Texture? = null

    internal fun render(gl: GL10)
    {
        gl.glDisable(GL10.GL_TEXTURE_2D)
        gl.glColor4f(this.diffuse.red, this.diffuse.green, this.diffuse.blue, this.alpha)
        gl.glMaterialfv(GL10.GL_FRONT_AND_BACK, GL10.GL_DIFFUSE, this.diffuse.floatBuffer())

        this.texture?.let { text ->
            gl.glEnable(GL10.GL_TEXTURE_2D)
            text.bind(gl)
        }
    }
}