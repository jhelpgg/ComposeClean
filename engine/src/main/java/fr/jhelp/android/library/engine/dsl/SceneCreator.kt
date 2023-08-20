package fr.jhelp.android.library.engine.dsl

import fr.jhelp.android.library.engine.dsl.animation.effect.ParticleEffectReference
import fr.jhelp.android.library.engine.dsl.animation.effect.ParticlePlayer
import fr.jhelp.android.library.engine.dsl.animation.player.AnimationNodePlayer
import fr.jhelp.android.library.engine.dsl.animation.player.AnimationPlayer
import fr.jhelp.android.library.engine.dsl.animation.player.MorphingPlayer
import fr.jhelp.android.library.engine.extensions.children
import fr.jhelp.android.library.engine.extensions.position
import fr.jhelp.android.library.engine.scene.Color3D
import fr.jhelp.android.library.engine.scene.Position3D
import fr.jhelp.android.library.engine.scene.Scene3D
import fr.jhelp.android.library.engine.view.View3D

/**
 * Create a 3D scene
 *
 * @property view3D View that show the 3D
 */
class SceneCreator internal constructor(val view3D: View3D)
{
    /** The 3D scene */
    val scene3D: Scene3D = this.view3D.scene3D

    /** Background color */
    var backgroundColor: Color3D
        get() = this.scene3D.backgroundColor
        set(value)
        {
            this.scene3D.backgroundColor = value
        }

    /**
     * Change/define scene position
     */
    fun scenePosition(position: Position3D.() -> Unit)
    {
        this.scene3D.root.position(position)
    }

    /**
     * Create scene node hierarchy
     */
    fun root(nodeTree: NodeTreeCreator.() -> Unit)
    {
        this.scene3D.root.children(nodeTree)
    }

    /**
     * Create a dice reference
     */
    fun dice(): DiceReference =
        DiceReference(this.scene3D)

    /**
     * Create an animation player
     */
    fun player(animationNodeReference: AnimationNodeReference): AnimationPlayer =
        AnimationNodePlayer(this.scene3D, animationNodeReference)

    /**
     * Create a particle effect player
     */
    fun player(particleEffectReference: ParticleEffectReference): ParticlePlayer =
        ParticlePlayer(this.scene3D, particleEffectReference)

    /**
     * Create a morphing animation player
     */
    fun player(animationMorphingReference: AnimationMorphingReference): MorphingPlayer =
        MorphingPlayer(this.scene3D, animationMorphingReference)

    /**
     * Define a background texture
     */
    fun backgroundTexture(textureReference: TextureReference)
    {
        this.scene3D.textureBackground = textureReference.textureSource.texture
    }

    /**
     * Remove any background texture
     */
    fun noBackgroundTexture()
    {
        this.scene3D.textureBackground = null
    }

    /**
     * Add texture over the 3D
     */
    fun over3DTexture(textureReference: TextureReference)
    {
        this.scene3D.textureOver3D = textureReference.textureSource.texture
    }

    /**
     * Remove any texture over the 3D
     */
    fun noOver3DTexture()
    {
        this.scene3D.textureOver3D = null
    }
}