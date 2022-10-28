package fr.jhelp.compose.engine.dsl.texture

import fr.jhelp.compose.engine.resources.ResourcesAccess
import fr.jhelp.compose.engine.scene.Texture

class TextureSourceAsset(private val assetPath: String) : TextureSource()
{
    override fun createTexture(): Texture =
        ResourcesAccess.obtainTexture(this.assetPath)
}