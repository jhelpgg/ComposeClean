package fr.jhelp.android.library.engine.resources.texture

import fr.jhelp.android.library.engine.resources.ResourcesAccess
import fr.jhelp.android.library.engine.scene.Texture

/**
 * Default texture
 */
object TextureSourceDefault : TextureSource()
{
    override fun createTexture(): Texture =
        Texture(ResourcesAccess.defaultBitmap(), true)
}