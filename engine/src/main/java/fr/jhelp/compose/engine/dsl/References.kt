package fr.jhelp.compose.engine.dsl

import androidx.annotation.DrawableRes
import fr.jhelp.compose.engine.dsl.animation.AnimationNodeCreator
import fr.jhelp.compose.engine.dsl.animation.effect.ParticleEffectCreator
import fr.jhelp.compose.engine.dsl.animation.effect.ParticleEffectReference
import fr.jhelp.compose.engine.dsl.animation.effect.ParticleEffectReferencesCreator
import fr.jhelp.compose.engine.dsl.animation.effect.ParticleNodeCreator
import fr.jhelp.compose.engine.dsl.animation.effect.ParticleNodeReference
import fr.jhelp.compose.engine.dsl.animation.effect.ParticleNodeReferenceCreator
import fr.jhelp.compose.engine.dsl.texture.TextureSourceAsset
import fr.jhelp.compose.engine.dsl.texture.TextureSourceDrawable

internal val junkReference = NodeReference()

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

fun particleEffectReference(): ParticleEffectReference = ParticleEffectReference()
fun particleEffectReferences(): ParticleEffectReferencesCreator = ParticleEffectReferencesCreator

fun particleNodeReference(): ParticleNodeReference = ParticleNodeReference()
fun particleNodeReferences(): ParticleNodeReferenceCreator = ParticleNodeReferenceCreator

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
    val materialCreator = MaterialCreator(materialReference.material)
    material(materialCreator)
    materialCreator.resolveTexture()
}

fun animationNode(animationNodeReference: AnimationNodeReference,
                  animation: AnimationNodeCreator.() -> Unit)
{
    animation(animationNodeReference.animationNodeCreator)
}

fun particleEffect(particleEffectReference: ParticleEffectReference,
                   particleEffect: ParticleEffectCreator.() -> Unit)
{
    particleEffect(particleEffectReference.particleEffect)
}

fun particleNode(particleNodeReference: ParticleNodeReference,
                 particleNode: ParticleNodeCreator.() -> Unit)
{
    particleNode(particleNodeReference.particleNodeCreator)
}

