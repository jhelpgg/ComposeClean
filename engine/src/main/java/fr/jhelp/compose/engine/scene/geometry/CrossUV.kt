/*
 *  <h1>License :</h1> <br/>
 * The following code is deliver as is. <br/>
 *  You can use, modify, the code as your need for any usage.<br/>
 *  But you can't do any action that avoid me or other person use, modify this code.<br/>
 *  The code is free for usage and modification, you can't change that fact.
 */

package fr.jhelp.compose.engine.scene.geometry

/**
 * UV are place on cross like this:
 *
 *       0   u1  u2  1
 *     0 +---+---+---+
 *       | . | T | . |
 *     v1+---+---+---+
 *       | L | F | R |
 *     v2+---+---+---+
 *       | . | B | . |
 *     v3+---+---+---+
 *       | . | b | . |
 *     1 +---+---+---+
 *
 * Where:
 * * **T** : Applied on top face
 * * **L** : Applied on left face
 * * **F** : Applied on face face
 * * **R** : Applied on right face
 * * **B** : Applied on bottom face
 * * **b** : Applied on back face
 * * Other part of texture are not used
 * @param u1 U border between left and face
 * @param u2 U border between face and right
 * @param v1 V border between top and face
 * @param v2 V border between face and bottom
 * @param v3 V border between bottom and back
 */
class CrossUV(u1: Float = 1f / 3f, u2: Float = 2f / 3f,
              v1: Float = 0.25f, v2: Float = 0.5f, v3: Float = 0.75f) :
    BoxUV(face = FaceUV(u1, u2, v1, v2), back = FaceUV(u1, u2, v3, 1f),
          top = FaceUV(u1, u2, 0f, v1), bottom = FaceUV(u1, u2, v2, v3),
          left = FaceUV(0f, u1, v1, v2), right = FaceUV(u2, 1f, v1, v2))