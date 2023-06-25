/*
 *  <h1>License :</h1> <br/>
 * The following code is deliver as is. <br/>
 *  You can use, modify, the code as your need for any usage.<br/>
 *  But you can't do any action that avoid me or other person use, modify this code.<br/>
 *  The code is free for usage and modification, you can't change that fact.
 */

package fr.jhelp.android.library.engine.scene.geometry.dice

import fr.jhelp.android.library.engine.R
import fr.jhelp.android.library.engine.animation.keyFrame.AnimationNode3D
import fr.jhelp.android.library.engine.resources.ResourcesAccess
import fr.jhelp.android.library.engine.scene.Color3D
import fr.jhelp.android.library.engine.scene.GREY
import fr.jhelp.android.library.engine.scene.Node3D
import fr.jhelp.android.library.engine.scene.Position3D
import fr.jhelp.android.library.engine.scene.geometry.Box
import fr.jhelp.android.library.engine.scene.geometry.CrossUV
import fr.jhelp.android.library.math.extensions.bounds
import fr.jhelp.android.library.math.random
import fr.jhelp.android.library.tasks.TaskType
import fr.jhelp.android.library.animations.Animation
import fr.jhelp.android.library.animations.AnimationList
import fr.jhelp.android.library.animations.AnimationTask
import kotlin.math.max
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

private val DICE_POSITIONS = arrayOf(Position3D(), // 1: Face
                                     Position3D(angleY = -90f), // 2: Right
                                     Position3D(angleX = -90f), // 3: Bottom
                                     Position3D(angleX = 90f), // 4: Top
                                     Position3D(angleY = 90f), // 5: Left
                                     Position3D(angleY = 180f)) // 6: Back

/**
 * A dice
 */
class Dice(@DiceValue value: Int = random(1, 6)) : Node3D()
{
    private val dice = Box(CrossUV())

    /** Current dice value */
    var value: Int = value.bounds(1, 6)
        private set
    private val diceInfoMutableState: MutableStateFlow<DiceInfo> =
        MutableStateFlow<DiceInfo>(DiceInfo(this.id, this.name, this.value))

    /** Observable on dice info change */
    val diceInfoState: StateFlow<DiceInfo> = this.diceInfoMutableState.asStateFlow()
    private val changeValue = { value: Int ->
        this.value = value
        this.diceInfoMutableState.value = DiceInfo(this.id, this.name, this.value)
    }

    init
    {
        this.dice.material.texture = ResourcesAccess.obtainTexture(R.drawable.dice)
        this.dice.position = DICE_POSITIONS[this.value - 1]
        this.add(this.dice)
    }

    /**
     * Create animation tha change the dice value
     * @param value New dice value: 1, 2, 3, 4, 5 or 6
     * @param numberFrame Animation duration in frames
     * @return Created animation
     */
    @Throws(IllegalArgumentException::class)
    fun value(@DiceValue value: Int,
              numberFrame: Int = 1): Animation
    {
        val diceValue = value.bounds(1, 6)
        val animation = AnimationList()
        val animationNode = AnimationNode3D(this.dice)
        animationNode.frame(max(1, numberFrame), DICE_POSITIONS[diceValue - 1])
        animation.add(animationNode)
        animation.add(AnimationTask(TaskType.SHORT_TASK, diceValue, this.changeValue))
        return animation
    }

    /**
     * Create animation that roll the dice
     * @return Created animation
     */
    fun roll(): Animation
    {
        val animationNode = AnimationNode3D(this.dice)
        var frame = 1
        val time = random(12, 25)
        var lastValue = this.value
        var value = this.value

        for (index in 0..time)
        {
            do
            {
                value = random(1, 6)
            }
            while (value == lastValue)

            lastValue = value
            animationNode.frame(frame, DICE_POSITIONS[value - 1])
            frame += index + 1
        }

        val animation = AnimationList()
        animation.add(animationNode)
        animation.add(AnimationTask(TaskType.SHORT_TASK, value, this.changeValue))
        return animation
    }

    /**
     * Change dice color
     */
    fun color(color: Color3D = GREY)
    {
        this.dice.material.diffuse = color
    }
}