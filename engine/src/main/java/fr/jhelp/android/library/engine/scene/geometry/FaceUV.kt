/*
 *  <h1>License :</h1> <br/>
 * The following code is deliver as is. <br/>
 *  You can use, modify, the code as your need for any usage.<br/>
 *  But you can't do any action that avoid me or other person use, modify this code.<br/>
 *  The code is free for usage and modification, you can't change that fact.
 */

package fr.jhelp.android.library.engine.scene.geometry

/**
 * Describes how UV are put in a box face.
 *
 * The face is covered by the texture.
 * @param minU Left U on face
 * @param maxU Right U on face
 * @param minV Up V on face
 * @param maxV Down V on face
 */
class FaceUV(val minU: Float = 0f, val maxU: Float = 1f, val minV: Float = 0f, val maxV: Float = 1f)