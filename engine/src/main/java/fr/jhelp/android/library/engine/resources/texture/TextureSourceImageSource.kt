package fr.jhelp.android.library.engine.resources.texture

import fr.jhelp.android.library.engine.resources.image.ImageSource
import fr.jhelp.android.library.engine.resources.texture
import fr.jhelp.android.library.engine.scene.Texture

/**
 * Texture source based on image source
 */
class TextureSourceImageSource(private val imageSource: ImageSource<*>) : TextureSource()
{
    override fun createTexture(): Texture = texture(this.imageSource)
}
