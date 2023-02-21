package fr.jhelp.compose.engine.dsl

import fr.jhelp.compose.engine.scene.Color3D
import fr.jhelp.compose.engine.scene.Material
import fr.jhelp.compose.math.extensions.bounds

class MaterialCreator internal constructor(private val material: Material)
{
    var alpha: Float
        get() = this.material.alpha
        set(value)
        {
            this.material.alpha = value.bounds(0f, 1f)
        }

    var diffuse: Color3D
        get() = this.material.diffuse
        set(value)
        {
            this.material.diffuse = value
        }

    var textureReference: TextureReference? = null

    internal fun resolveTexture()
    {
        this.material.texture = this.textureReference?.textureSource?.texture
    }
}