package fr.jhelp.compose.engine.dsl

import fr.jhelp.compose.engine.scene.Node3D

class NodeReference internal constructor(internal var node: Node3D = Node3D()) {
    override fun toString(): String =
        "NodeReference : ${this.node}"
}
