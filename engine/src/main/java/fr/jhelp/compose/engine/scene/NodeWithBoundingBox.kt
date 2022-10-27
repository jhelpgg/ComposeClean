/*
 *  <h1>License :</h1> <br/>
 * The following code is deliver as is. <br/>
 *  You can use, modify, the code as your need for any usage.<br/>
 *  But you can't do any action that avoid me or other person use, modify this code.<br/>
 *  The code is free for usage and modification, you can't change that fact.
 */

package fr.jhelp.compose.engine.scene

import fr.jhelp.compose.engine.math.BoundingBox

abstract class NodeWithBoundingBox : Node3D()
{
    abstract fun boundingBox(): BoundingBox
}