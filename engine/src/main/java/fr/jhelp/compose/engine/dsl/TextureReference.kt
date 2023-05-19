package fr.jhelp.compose.engine.dsl

import fr.jhelp.compose.engine.resources.texture.TextureSource
import fr.jhelp.compose.engine.resources.texture.TextureSourceDefault

/**
 * Reference of texture
 */
class TextureReference internal constructor(internal var textureSource: TextureSource = TextureSourceDefault)
