package fr.jhelp.compose.engine.dsl

import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.Paint
import androidx.annotation.DrawableRes
import fr.jhelp.compose.engine.dsl.animation.AnimationNodeCreator
import fr.jhelp.compose.engine.dsl.animation.effect.ParticleEffectCreator
import fr.jhelp.compose.engine.dsl.animation.effect.ParticleEffectReference
import fr.jhelp.compose.engine.dsl.animation.effect.ParticleEffectReferencesCreator
import fr.jhelp.compose.engine.dsl.animation.effect.ParticleNodeCreator
import fr.jhelp.compose.engine.dsl.animation.effect.ParticleNodeReference
import fr.jhelp.compose.engine.dsl.animation.effect.ParticleNodeReferenceCreator
import fr.jhelp.compose.engine.dsl.texture.TextureSourceAsset
import fr.jhelp.compose.engine.dsl.texture.TextureSourceCreated
import fr.jhelp.compose.engine.dsl.texture.TextureSourceDrawable
import fr.jhelp.compose.engine.scene.geometry.dice.DiceInfo
import kotlinx.coroutines.flow.StateFlow

internal val junkReference = NodeReference()

/** Create a node reference */
fun nodeReference(): NodeReference = NodeReference()

/**
 * Create several node references. Example for 3 references :
 *
 * ```kotlin
 * val (nodeReference1, nodeReference2, nodeReference3) =  nodeReferences()
 * ```
 */
fun nodeReferences(): NodeReferencesCreator = NodeReferencesCreator

/** Create a material reference */
fun materialReference(): MaterialReference = MaterialReference()

/**
 * Create several material references. Example for 3 references :
 *
 * ```kotlin
 * val (materialReference1, materialReference2, materialReference3) =  materialReferences()
 * ```
 */
fun materialReferences(): MaterialReferencesCreator = MaterialReferencesCreator

/** Create a texture reference */
fun textureReference(): TextureReference = TextureReference()

/**
 * Create several texture references. Example for 3 references :
 *
 * ```kotlin
 * val (textureReference1, textureReference2, textureReference3) =  textureReferences()
 * ```
 */
fun textureReferences(): TextureReferencesCreator = TextureReferencesCreator

/**
 * Create animation of node reference
 *
 * @param nodeReference Node reference to node to animate
 */
fun animationNodeReference(nodeReference: NodeReference): AnimationNodeReference =
    AnimationNodeReference(nodeReference)

/**
 * Create several animation node references. Example for 3 references (beware must have same number of reference to create as number of node reference in parameters) :
 *
 * ```kotlin
 * val (animationNodeReference1, animationNodeReference2, animationNodeReference3) =  animationNodeReferences(nodeReference1, nodeReference2, nodeReference3)
 * ```
 *
 * In the example :
 * * `animationNodeReference1`will animate the node refer by `nodeReference1`
 * * `animationNodeReference2`will animate the node refer by `nodeReference2`
 * * `animationNodeReference3`will animate the node refer by `nodeReference3`
 *
 * @param nodeReferences Nodes reference to use on animation node
 */
fun animationNodeReferences(vararg nodeReferences: NodeReference): AnimationNodeReferencesCreator =
    AnimationNodeReferencesCreator(*nodeReferences)

/** Create a particle effect reference */
fun particleEffectReference(): ParticleEffectReference = ParticleEffectReference()

/**
 * Create several particle effect references. Example for 3 references :
 *
 * ```kotlin
 * val (particleEffectReference1, particleEffectReference2, particleEffectReference3) =  particleEffectReferences()
 * ```
 */
fun particleEffectReferences(): ParticleEffectReferencesCreator = ParticleEffectReferencesCreator

/** Create a particle node source reference */
fun particleNodeReference(): ParticleNodeReference = ParticleNodeReference()

/**
 * Create several particle node source references. Example for 3 references :
 *
 * ```kotlin
 * val (particleNodeReference1, particleNodeReference2, particleNodeReference2) =  particleNodeReferences()
 * ```
 */
fun particleNodeReferences(): ParticleNodeReferenceCreator = ParticleNodeReferenceCreator

/**
 * Associate a texture reference to a drawable resource.
 *
 * The texture will be the image referred by the drawable resource
 */
fun texture(textureReference: TextureReference, @DrawableRes resource: Int)
{
    textureReference.textureSource = TextureSourceDrawable(resource)
}

/**
 * Associate a texture reference to an asset path
 *
 * The texture will be the image referred by the file in assets indicates by the path
 */
fun texture(textureReference: TextureReference, assetPath: String)
{
    textureReference.textureSource = TextureSourceAsset(assetPath)
}

/**
 * Associate a texture draw by developer to a texture reference
 *
 * @param textureReference Texture reference where associate the texture
 * @param width Texture width (It will be round to nearest power of 2)
 * @param height Texture height (It will be round to nearest power of 2)
 * @param draw Describes what to draw on texture
 */
fun texture(textureReference: TextureReference,
            width: Int, height: Int, draw: (Bitmap, Canvas, Paint) -> Unit)
{
    textureReference.textureSource = TextureSourceCreated(width, height, draw)
}

/**
 * Create material associate to material reference
 */
fun material(materialReference: MaterialReference, material: MaterialCreator.() -> Unit)
{
    val materialCreator = MaterialCreator(materialReference.material)
    material(materialCreator)
    materialCreator.resolveTexture()
}

/**
 * Create animation base on node and refer it to an animation node reference
 */
fun animationNode(animationNodeReference: AnimationNodeReference,
                  animation: AnimationNodeCreator.() -> Unit)
{
    animation(animationNodeReference.animationNodeCreator)
}

/**
 * Create a particle effect for a particle effect reference
 */
fun particleEffect(particleEffectReference: ParticleEffectReference,
                   particleEffect: ParticleEffectCreator.() -> Unit)
{
    particleEffect(particleEffectReference.particleEffect)
}

/**
 * Create a particle node source for a particle node source reference
 */
fun particleNode(particleNodeReference: ParticleNodeReference,
                 particleNode: ParticleNodeCreator.() -> Unit)
{
    particleNode(particleNodeReference.particleNodeCreator)
}

/**
 * Roll a dice
 */
fun roll(dice: DiceReference)
{
    dice.scene.play(dice.dice.roll())
}
