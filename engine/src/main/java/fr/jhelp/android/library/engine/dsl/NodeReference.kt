package fr.jhelp.android.library.engine.dsl

import fr.jhelp.android.library.engine.scene.Node3D

/**
 * Reference to a node
 */
class NodeReference internal constructor(internal var node: Node3D = Node3D()) {
    override fun toString(): String =
        "NodeReference : ${this.node}"
}
