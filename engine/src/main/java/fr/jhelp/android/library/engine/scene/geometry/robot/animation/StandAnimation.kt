/*
 *  <h1>License :</h1> <br/>
 * The following code is deliver as is. <br/>
 *  You can use, modify, the code as your need for any usage.<br/>
 *  But you can't do any action that avoid me or other person use, modify this code.<br/>
 *  The code is free for usage and modification, you can't change that fact.
 */

package fr.jhelp.android.library.engine.scene.geometry.robot.animation

import fr.jhelp.android.library.animations.Animation
import fr.jhelp.android.library.animations.interpolation.Interpolation
import fr.jhelp.android.library.animations.interpolation.LinearInterpolation
import fr.jhelp.android.library.engine.scene.geometry.robot.Robot
import fr.jhelp.android.library.engine.scene.geometry.robot.RobotAnimation
import fr.jhelp.android.library.engine.scene.geometry.robot.RobotPosition
import kotlin.math.max

/**
 * Create animation that make the robot to return at it start position
 * @param numberFrame Number frame to return to start position
 * @param interpolation Interpolation to use
 * @return Created animation
 */
fun Robot.stand(numberFrame: Int = 1,
                interpolation: Interpolation = LinearInterpolation): Animation
{
    val animation = RobotAnimation(this)
    animation.frame(max(1, numberFrame), RobotPosition(), interpolation)
    return animation
}
