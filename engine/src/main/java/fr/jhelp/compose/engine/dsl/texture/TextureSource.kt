package fr.jhelp.compose.engine.dsl.texture

import fr.jhelp.compose.engine.scene.Texture

/**
 * Source where get a texture
 */
sealed class TextureSource
{
    /** Texture generated by the source */
    val texture: Texture by lazy { this.createTexture() }

    /** Create a texture from the source */
    protected abstract fun createTexture(): Texture
}
