package fr.jhelp.android.library.engine.extensions

import fr.jhelp.android.library.engine.dsl.SceneCreator
import fr.jhelp.android.library.engine.view.View3D
import fr.jhelp.android.library.tasks.TaskType

/**
 * Create scene tree hierarchy for this view 3D
 */
fun View3D.tree(scene: SceneCreator.() -> Unit)
{
    TaskType.SHORT_TASK.launch { scene(SceneCreator(this@tree)) }
}