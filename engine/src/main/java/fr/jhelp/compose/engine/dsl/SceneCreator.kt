package fr.jhelp.compose.engine.dsl

import fr.jhelp.compose.engine.dsl.animation.effect.ParticleEffectReference
import fr.jhelp.compose.engine.dsl.animation.effect.ParticlePlayer
import fr.jhelp.compose.engine.dsl.animation.player.AnimationNodePlayer
import fr.jhelp.compose.engine.dsl.animation.player.AnimationPlayer
import fr.jhelp.compose.engine.extensions.children
import fr.jhelp.compose.engine.extensions.position
import fr.jhelp.compose.engine.scene.Color3D
import fr.jhelp.compose.engine.scene.Position3D
import fr.jhelp.compose.engine.scene.Scene3D
import fr.jhelp.compose.engine.view.View3D

/**
 * Create a 3D scene
 */
class SceneCreator internal constructor(private val view3D: View3D)
{
    private val scene3D: Scene3D = this.view3D.scene3D

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