package fr.jhelp.compose.animations

import fr.jhelp.tasks.TaskType
import java.util.concurrent.atomic.AtomicBoolean

/**
 * Launch function as animation
 */
fun animationTask(taskType: TaskType, task: () -> Unit): Animation =
    AnimationTask(taskType, Unit) { task() }

/**
 * Create animation that launch a task
 */
class AnimationTask<P>(private val taskType: TaskType, private val parameter: P,
                       private val task: (P) -> Unit) : Animation(25)
{
    private val played = AtomicBoolean(false)

    override fun initialize()
    {
        this.played.set(false)
    }

    override fun animate(frame: Float): Boolean
    {
        if (!this.played.getAndSet(true))
        {
            this.taskType.launch { this@AnimationTask.task(this@AnimationTask.parameter) }
        }

        return false
    }
}