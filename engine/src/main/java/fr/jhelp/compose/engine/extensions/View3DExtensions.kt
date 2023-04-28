package fr.jhelp.compose.engine.extensions

import fr.jhelp.compose.engine.dsl.SceneCreator
import fr.jhelp.compose.engine.view.View3D
import fr.jhelp.tasks.TaskType

fun View3D.tree(scene: SceneCreator.() -> Unit)
{
    TaskType.SHORT_TASK.launch { scene(SceneCreator(this@tree)) }
}