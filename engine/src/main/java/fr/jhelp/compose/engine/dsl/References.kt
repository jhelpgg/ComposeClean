package fr.jhelp.compose.engine.dsl

import androidx.annotation.DrawableRes
import fr.jhelp.compose.engine.dsl.animation.AnimationNodeCreator
import fr.jhelp.compose.engine.dsl.texture.TextureSourceAsset
import fr.jhelp.compose.engine.dsl.texture.TextureSourceDrawable

fun nodeReference(): NodeReference = NodeReference()
fun nodeReferences(): NodeReferencesCreator = NodeReferencesCreator

fun materialReference(): MaterialReference = MaterialReference()
fun materialReferences(): MaterialReferencesCreator = MaterialReferencesCreator

fun textureReference(): TextureReference = TextureReference()
fun textureReferences(): TextureReferencesCreator = TextureReferencesCreator

fun animationNodeReference(nodeReference: NodeReference): AnimationNodeReference =
    AnimationNodeReference(nodeReference)

fun animationNodeReferences(vararg nodeReferences: NodeReference): AnimationNodeReferencesCreator =
    AnimationNodeReferencesCreator(*nodeReferences)

fun texture(textureReference: TextureReference, @DrawableRes resource: Int)
{
    textureReference.textureSource = TextureSourceDrawable(resource)
}

fun texture(textureReference: TextureReference, assetPath: String)
{
    textureReference.textureSource = TextureSourceAsset(assetPath)
}

fun material(materialReference: MaterialReference, material: MaterialCreator.() -> Unit)
{
    val materialCreator = MaterialCreator()
    material(materialCreator)
    materialReference.material = materialCreator()
}

fun animationNode(animationNodeReference: AnimationNodeReference,
                  animation: AnimationNodeCreator.() -> Unit)
{
    animation(animationNodeReference.animationNodeCreator)
}

