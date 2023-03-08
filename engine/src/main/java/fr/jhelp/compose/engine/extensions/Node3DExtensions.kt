package fr.jhelp.compose.engine.extensions

import fr.jhelp.compose.engine.dsl.NodeTreeCreator
import fr.jhelp.compose.engine.scene.Node3D
import fr.jhelp.compose.engine.scene.Position3D

fun Node3D.position(position: Position3D.() -> Unit)
{
    position(this.position)
}

fun Node3D.children(nodeTree: NodeTreeCreator.() -> Unit)
{
    nodeTree(NodeTreeCreator(this))
}
