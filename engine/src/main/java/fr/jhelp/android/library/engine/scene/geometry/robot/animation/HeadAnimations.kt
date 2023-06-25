/*
 *  <h1>License :</h1> <br/>
 * The following code is deliver as is. <br/>
 *  You can use, modify, the code as your need for any usage.<br/>
 *  But you can't do any action that avoid me or other person use, modify this code.<br/>
 *  The code is free for usage and modification, you can't change that fact.
 */

package fr.jhelp.android.library.engine.scene.geometry.robot.animation

import fr.jhelp.compose.animations.Animation
import fr.jhelp.android.library.engine.scene.geometry.robot.Robot
import fr.jhelp.android.library.engine.scene.geometry.robot.RobotAnimation
import fr.jhelp.android.library.engine.scene.geometry.robot.RobotPosition

/**
 * Animation for robot to say yes
 */
val Robot.headYesAnimation: Animation
    get()
    {
        val animation = RobotAnimation(this)
        animation.frame(8, RobotPosition(neckAngleX = 35f))
        animation.frame(16, RobotPosition(neckAngleX = -25f))
        animation.frame(32, RobotPosition(neckAngleX = 35f))
        animation.frame(48, RobotPosition(neckAngleX = -25f))
        animation.frame(56, RobotPosition(neckAngleX = 0f))
        return animation
    }

/**
 * Animation for robot to say no
 */
val Robot.headNoAnimation: Animation
    get()
    {
        val angle = 45f
        val animation = RobotAnimation(this)
        animation.frame(8, RobotPosition(neckAngleY = angle))
        animation.frame(16, RobotPosition(neckAngleY = -angle))
        animation.frame(32, RobotPosition(neckAngleY = angle))
        animation.frame(48, RobotPosition(neckAngleY = -angle))
        animation.frame(56, RobotPosition(neckAngleY = 0f))
        return animation
    }