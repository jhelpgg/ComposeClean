package fr.jhelp.compose.engine.dsl

import fr.jhelp.compose.engine.dsl.animation.player.AnimationNodePlayer
import fr.jhelp.compose.engine.dsl.animation.player.AnimationPlayer
import fr.jhelp.compose.engine.extensions.children
import fr.jhelp.compose.engine.extensions.position
import fr.jhelp.compose.engine.scene.Position3D
import fr.jhelp.compose.engine.scene.Scene3D
import fr.jhelp.compose.engine.view.View3D

class SceneCreator internal constructor(private val view3D: View3D)
{
    private val scene3D: Scene3D = this.view3D.scene3D

    fun position(position: Position3D.() -> Unit)
    {
        this.scene3D.root.position(position)
    }

    fun root(nodeTree: NodeTreeCreator.() -> Unit)
    {
        this.scene3D.root.children(nodeTree)
    }

    fun play(animationNodeReference: AnimationNodeReference)
    {
        this.scene3D.play(animationNodeReference.animationNodeCreator.animation)
    }

    fun stop(animationNodeReference: AnimationNodeReference)
    {
        this.scene3D.stop(animationNodeReference.animationNodeCreator.animation)
    }

    fun player(animationNodeReference: AnimationNodeReference): AnimationPlayer =
        AnimationNodePlayer(this.scene3D, animationNodeReference)
}