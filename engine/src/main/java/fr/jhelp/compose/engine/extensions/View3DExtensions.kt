package fr.jhelp.compose.engine.extensions

import fr.jhelp.compose.engine.dsl.SceneCreator
import fr.jhelp.compose.engine.view.View3D
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

fun View3D.tree(scene: SceneCreator.() -> Unit)
{
    CoroutineScope(Dispatchers.Default).launch { scene(SceneCreator(this@tree)) }
}