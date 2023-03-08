package fr.jhelp.compose.engine.dsl.texture

import fr.jhelp.compose.engine.scene.Texture

sealed class TextureSource
{
    val texture: Texture by lazy { this.createTexture() }

    protected abstract fun createTexture(): Texture
}
