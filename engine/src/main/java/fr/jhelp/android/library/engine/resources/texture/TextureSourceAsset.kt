package fr.jhelp.android.library.engine.resources.texture

import fr.jhelp.android.library.engine.resources.ResourcesAccess
import fr.jhelp.android.library.engine.scene.Texture

/**
 * Texture in assets file
 */
class TextureSourceAsset(private val assetPath: String) : TextureSource()
{
    override fun createTexture(): Texture =
        ResourcesAccess.obtainTexture(this.assetPath)
}