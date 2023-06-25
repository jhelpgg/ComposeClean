package fr.jhelp.composeclean.game.models

import fr.jhelp.android.library.engine.scene.Node3D

/**
 * Model of 3D object used in game
 */
abstract class Model3D
{
    /** Node to use */
    val node: Node3D by lazy { this.create() }

    /**
     * Create the node reference with material applied on objects/clone
     */
    protected abstract fun create(): Node3D
}
