/*
 *  <h1>License :</h1> <br/>
 * The following code is deliver as is. <br/>
 *  You can use, modify, the code as your need for any usage.<br/>
 *  But you can't do any action that avoid me or other person use, modify this code.<br/>
 *  The code is free for usage and modification, you can't change that fact.
 */

package fr.jhelp.compose.engine.scene.geometry

/**
 * Describes how UV are put on each box face.
 *
 * By default texture is repeat on each face.
 * @param face Describes how UV are put on face
 * @param back Describes how UV are put on back
 * @param top Describes how UV are put on top
 * @param bottom Describes how UV are put on bottom
 * @param left Describes how UV are put on left
 * @param right Describes how UV are put on right
 */
open class BoxUV(val face: FaceUV = FaceUV(), val back: FaceUV = FaceUV(),
                 val top: FaceUV = FaceUV(), val bottom: FaceUV = FaceUV(),
                 val left: FaceUV = FaceUV(), val right: FaceUV = FaceUV())