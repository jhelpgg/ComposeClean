/*
 *  <h1>License :</h1> <br/>
 * The following code is deliver as is. <br/>
 *  You can use, modify, the code as your need for any usage.<br/>
 *  But you can't do any action that avoid me or other person use, modify this code.<br/>
 *  The code is free for usage and modification, you can't change that fact.
 */

package fr.jhelp.compose.engine.scene.geometry.robot

import fr.jhelp.compose.math.extensions.bounds
import fr.jhelp.compose.math.moduloInterval

/**
 * Describe [Robot] joints position
 * @param neckAngleX Neck angle around X axis in `[-45, 45]`
 * @param neckAngleY Neck angle around Y axis in `[-90, 90]`
 * @param neckAngleZ Neck angle around Z axis in `[-22, 22]`
 * @param rightShoulderAngleX Right shoulder angle around X axis
 * @param rightShoulderAngleZ Right shoulder angle around Z axis in `[0, 180]`
 * @param rightElbowAngleX Right elbow angle around X axis in `[-150, 0]`
 * @param leftShoulderAngleX Left shoulder angle around X axis
 * @param leftShoulderAngleZ Left shoulder angle around Z axis in `[-180, 0]`
 * @param leftElbowAngleX Left elbow angle around X axis in `[-150, 0]`
 * @param rightAssAngleX Right ass angle around X axis in `[90, 270]`
 * @param rightAssAngleZ Right ass angle around Z axis in `[-30, 90]`
 * @param rightKneeAngleX Right knee angle around X axis in `[0, 150]`
 * @param leftAssAngleX Left ass angle around X axis in `[90, 270]`
 * @param leftAssAngleZ Left ass angle around Z axis in `[-90, 30]`
 * @param leftKneeAngleX Left knee angle around X axis in `[0, 150]`
 */
class RobotPosition(neckAngleX: Float = 0f, neckAngleY: Float = 0f, neckAngleZ: Float = 0f,
                    rightShoulderAngleX: Float = 180f, rightShoulderAngleZ: Float = 0f,
                    rightElbowAngleX: Float = 0f,
                    leftShoulderAngleX: Float = 180f, leftShoulderAngleZ: Float = 0f,
                    leftElbowAngleX: Float = 0f,
                    rightAssAngleX: Float = 180f, rightAssAngleZ: Float = 0f,
                    rightKneeAngleX: Float = 0f,
                    leftAssAngleX: Float = 180f, leftAssAngleZ: Float = 0f,
                    leftKneeAngleX: Float = 0f)
{
    val neckAngleX = neckAngleX.bounds(-45f, 45f)
    val neckAngleY = neckAngleY.bounds(-90f, 90f)
    val neckAngleZ = neckAngleZ.bounds(-22f, 22f)

    val rightShoulderAngleX = moduloInterval(rightShoulderAngleX, 0f, 360f)
    val rightShoulderAngleZ = rightShoulderAngleZ.bounds(0f, 180f)

    val rightElbowAngleX = rightElbowAngleX.bounds(-150f, 0f)

    val leftShoulderAngleX = moduloInterval(leftShoulderAngleX, 0f, 360f)
    val leftShoulderAngleZ = leftShoulderAngleZ.bounds(-180f, 0f)

    val leftElbowAngleX = leftElbowAngleX.bounds(-150f, 0f)

    val rightAssAngleX = rightAssAngleX.bounds(90f, 270f)
    val rightAssAngleZ = rightAssAngleZ.bounds(-30f, 90f)

    val rightKneeAngleX = rightKneeAngleX.bounds(0f, 150f)

    val leftAssAngleX = leftAssAngleX.bounds(90f, 270f)
    val leftAssAngleZ = leftAssAngleZ.bounds(-90f, 30f)

    val leftKneeAngleX = leftKneeAngleX.bounds(0f, 150f)
}
