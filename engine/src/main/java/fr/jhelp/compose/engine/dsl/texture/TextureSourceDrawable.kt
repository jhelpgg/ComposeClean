package fr.jhelp.compose.engine.dsl.texture

import androidx.annotation.DrawableRes
import fr.jhelp.compose.engine.resources.ResourcesAccess
import fr.jhelp.compose.engine.scene.Texture

class TextureSourceDrawable(@DrawableRes private val resource: Int) : TextureSource()
{
    override fun createTexture(): Texture =
        ResourcesAccess.obtainTexture(this.resource)
}