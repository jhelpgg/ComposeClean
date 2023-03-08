/*
 *  <h1>License :</h1> <br/>
 * The following code is deliver as is. <br/>
 *  You can use, modify, the code as your need for any usage.<br/>
 *  But you can't do any action that avoid me or other person use, modify this code.<br/>
 *  The code is free for usage and modification, you can't change that fact.
 */

package fr.jhelp.compose.engine.scene.geometry.robot.animation

import fr.jhelp.compose.animations.Animation
import fr.jhelp.compose.engine.scene.geometry.robot.Robot
import fr.jhelp.compose.engine.scene.geometry.robot.RobotAnimation
import fr.jhelp.compose.engine.scene.geometry.robot.RobotPosition
import kotlin.math.max

/**
 * Create animation that make robot to walk
 * @param numberFramePerStep Number frame to make a step
 * @param numberStep Number step to make
 * @return Created animation
 */
fun Robot.walk(numberFramePerStep: Int = 2, numberStep: Int = 1): Animation
{
    val frame = max(2, numberFramePerStep)
    val semiFrame = frame shr 1
    val stepMax = max(1, numberStep)
    val animation = RobotAnimation(this)
    val robotPosition1 = RobotPosition(rightShoulderAngleX = 144f, leftShoulderAngleX = 216f,
                                       rightAssAngleX = 216f, leftAssAngleX = 144f)
    val robotPosition2 = RobotPosition(rightShoulderAngleX = 216f, leftShoulderAngleX = 144f,
                                       rightAssAngleX = 144f, leftAssAngleX = 216f)
    var key = semiFrame
    animation.frame(key, robotPosition1)
    var step = 1

    while (step < stepMax)
    {
        key += frame
        animation.frame(key, if ((step and 1) == 0) robotPosition1 else robotPosition2)
        step++
    }

    key += semiFrame
    animation.frame(key, RobotPosition())
    return animation
}
