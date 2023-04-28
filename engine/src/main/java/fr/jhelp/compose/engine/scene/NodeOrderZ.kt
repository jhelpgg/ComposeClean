package fr.jhelp.compose.engine.scene

import fr.jhelp.compose.math.extensions.sign


/**
 * Order nodes by their 'Z'
 */
object NodeOrderZ : Comparator<Node3D>
{
    override fun compare(node1: Node3D, node2: Node3D): Int =
        (node2.zOrder - node1.zOrder).sign()
}