/*
 *  <h1>License :</h1> <br/>
 * The following code is deliver as is. <br/>
 *  You can use, modify, the code as your need for any usage.<br/>
 *  But you can't do any action that avoid me or other person use, modify this code.<br/>
 *  The code is free for usage and modification, you can't change that fact.
 */

package fr.jhelp.compose.engine.scene.geometry

import fr.jhelp.compose.engine.scene.Object3D


/**
 * A plane
 */
class Plane(startU: Float = 0f, endU: Float = 1f, startV: Float = 0f, endV: Float = 1f) : Object3D()
{
    init
    {
        this.addSquare(-0.5f, 0.5f, 0f,
                       startU, startV,
                       -0.5f, -0.5f, 0f,
                       startU, endV,
                       0.5f, -0.5f, 0f,
                       endU, endV,
                       0.5f, 0.5f, 0f,
                       endU, startV)

        this.doubleFace = true
        this.seal()
    }
}