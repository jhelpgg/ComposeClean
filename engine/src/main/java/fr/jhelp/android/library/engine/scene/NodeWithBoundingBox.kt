/*
 *  <h1>License :</h1> <br/>
 * The following code is deliver as is. <br/>
 *  You can use, modify, the code as your need for any usage.<br/>
 *  But you can't do any action that avoid me or other person use, modify this code.<br/>
 *  The code is free for usage and modification, you can't change that fact.
 */

package fr.jhelp.android.library.engine.scene

import fr.jhelp.android.library.engine.math.BoundingBox

/**
 * Generic class for node with a bounding box
 */
abstract class NodeWithBoundingBox : Node3D()
{
    /**
     * Node's bounding box
     */
    abstract fun boundingBox(): BoundingBox
}