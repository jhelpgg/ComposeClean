package fr.jhelp.android.library.engine.extensions

import fr.jhelp.android.library.engine.dsl.NodeTreeCreator
import fr.jhelp.android.library.engine.scene.Node3D
import fr.jhelp.android.library.engine.scene.Position3D

/**
 * Change node position
 */
fun Node3D.position(position: Position3D.() -> Unit)
{
    position(this.position)
}

/***
 * Create nde hierarchy
 */
fun Node3D.children(nodeTree: NodeTreeCreator.() -> Unit)
{
    nodeTree(NodeTreeCreator(this))
}
