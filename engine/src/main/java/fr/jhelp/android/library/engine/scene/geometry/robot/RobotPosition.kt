/*
 *  <h1>License :</h1> <br/>
 * The following code is deliver as is. <br/>
 *  You can use, modify, the code as your need for any usage.<br/>
 *  But you can't do any action that avoid me or other person use, modify this code.<br/>
 *  The code is free for usage and modification, you can't change that fact.
 */

package fr.jhelp.android.library.engine.scene.geometry.robot

import fr.jhelp.android.library.math.extensions.bounds
import fr.jhelp.android.library.math.moduloInterval

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
    /** Neck angle around X axis in `[-45, 45]` */
    val neckAngleX: Float = neckAngleX.bounds(-45f, 45f)

    /** Neck angle around Y axis in `[-90, 90]` */
    val neckAngleY: Float = neckAngleY.bounds(-90f, 90f)

    /** Neck angle around Z axis in `[-22, 22]` */
    val neckAngleZ: Float = neckAngleZ.bounds(-22f, 22f)

    /** Right shoulder angle around X axis */
    val rightShoulderAngleX: Float = moduloInterval(rightShoulderAngleX, 0f, 360f)

    /** Right shoulder angle around Z axis in `[0, 180]` */
    val rightShoulderAngleZ: Float = rightShoulderAngleZ.bounds(0f, 180f)

    /** Right elbow angle around X axis in `[-150, 0]` */
    val rightElbowAngleX: Float = rightElbowAngleX.bounds(-150f, 0f)

    /** Left shoulder angle around X axis */
    val leftShoulderAngleX: Float = moduloInterval(leftShoulderAngleX, 0f, 360f)

    /** Left shoulder angle around Z axis in `[-180, 0]` */
    val leftShoulderAngleZ: Float = leftShoulderAngleZ.bounds(-180f, 0f)

    /** Left elbow angle around X axis in `[-150, 0]` */
    val leftElbowAngleX: Float = leftElbowAngleX.bounds(-150f, 0f)

    /** Right ass angle around X axis in `[90, 270]` */
    val rightAssAngleX: Float = rightAssAngleX.bounds(90f, 270f)

    /** Right ass angle around Z axis in `[-30, 90]` */
    val rightAssAngleZ: Float = rightAssAngleZ.bounds(-30f, 90f)

    /** Right knee angle around X axis in `[0, 150]` */
    val rightKneeAngleX: Float = rightKneeAngleX.bounds(0f, 150f)

    /** Left ass angle around X axis in `[90, 270]` */
    val leftAssAngleX: Float = leftAssAngleX.bounds(90f, 270f)

    /** Left ass angle around Z axis in `[-90, 30]` */
    val leftAssAngleZ: Float = leftAssAngleZ.bounds(-90f, 30f)

    /** Left knee angle around X axis in `[0, 150]` */
    val leftKneeAngleX: Float = leftKneeAngleX.bounds(0f, 150f)
}
