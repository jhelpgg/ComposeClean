package fr.jhelp.compose.animations

import java.util.concurrent.atomic.AtomicBoolean
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

/**
 * Launch function as animation
 */
fun animationTask(coroutineScope: CoroutineScope, task: () -> Unit) =
    AnimationTask(coroutineScope, Unit) { task() }

/**
 * Create animation that launch a task
 */
class AnimationTask<P>(private val coroutineScope: CoroutineScope, private val parameter: P,
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
            this.coroutineScope.launch { this@AnimationTask.task(this@AnimationTask.parameter) }
        }

        return false
    }
}