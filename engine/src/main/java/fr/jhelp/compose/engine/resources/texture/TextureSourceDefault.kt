package fr.jhelp.compose.engine.resources.texture

import fr.jhelp.compose.engine.resources.ResourcesAccess
import fr.jhelp.compose.engine.scene.Texture

/**
 * Default texture
 */
object TextureSourceDefault : TextureSource()
{
    override fun createTexture(): Texture =
        Texture(ResourcesAccess.defaultBitmap(), true)
}