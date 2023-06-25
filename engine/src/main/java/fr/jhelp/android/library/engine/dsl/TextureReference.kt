package fr.jhelp.android.library.engine.dsl

import fr.jhelp.android.library.engine.resources.texture.TextureSource
import fr.jhelp.android.library.engine.resources.texture.TextureSourceDefault

/**
 * Reference of texture
 */
class TextureReference internal constructor(internal var textureSource: TextureSource = TextureSourceDefault)
