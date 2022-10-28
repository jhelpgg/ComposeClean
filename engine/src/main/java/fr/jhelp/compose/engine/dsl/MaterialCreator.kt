package fr.jhelp.compose.engine.dsl

import fr.jhelp.compose.engine.scene.GREY
import fr.jhelp.compose.engine.scene.Material
import fr.jhelp.compose.math.extensions.bounds

class MaterialCreator
{
    var alpha: Float = 1f
        set(value)
        {
            field = value.bounds(0f, 1f)
        }

    var diffuse = GREY
    var textureReference: TextureReference? = null

    operator fun invoke(): Material
    {
        val material = Material()
        material.alpha = this.alpha
        material.diffuse = diffuse
        material.texture = this.textureReference?.textureSource?.texture
        return material
    }
}