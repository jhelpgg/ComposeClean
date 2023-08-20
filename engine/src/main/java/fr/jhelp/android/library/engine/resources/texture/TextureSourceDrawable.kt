package fr.jhelp.android.library.engine.resources.texture

import androidx.annotation.DrawableRes
import fr.jhelp.android.library.engine.resources.ResourcesAccess
import fr.jhelp.android.library.engine.scene.Texture

/**
 * Texture from drawable resources
 */
class TextureSourceDrawable(@DrawableRes private val resource: Int,
                            private val seal: Boolean = true) : TextureSource()
{
    override fun createTexture(): Texture =
        ResourcesAccess.obtainTexture(this.resource, this.seal)
}