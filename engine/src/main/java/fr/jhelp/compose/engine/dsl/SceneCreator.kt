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

class SceneCreator internal constructor(private val view3D: View3D)
{
    private val scene3D: Scene3D = this.view3D.scene3D
    var backgroundColor: Color3D
        get() = this.scene3D.backgroundColor
        set(value)
        {
            this.scene3D.backgroundColor = value
        }

    fun scenePosition(position: Position3D.() -> Unit)
    {
        this.scene3D.root.position(position)
    }

    fun root(nodeTree: NodeTreeCreator.() -> Unit)
    {
        this.scene3D.root.children(nodeTree)
    }

    fun player(animationNodeReference: AnimationNodeReference): AnimationPlayer =
        AnimationNodePlayer(this.scene3D, animationNodeReference)

    fun player(particleEffectReference: ParticleEffectReference): ParticlePlayer =
        ParticlePlayer(this.scene3D, particleEffectReference)
}