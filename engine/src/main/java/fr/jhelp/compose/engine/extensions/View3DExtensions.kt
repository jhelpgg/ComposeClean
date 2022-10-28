package fr.jhelp.compose.engine.extensions

import fr.jhelp.compose.engine.dsl.SceneCreator
import fr.jhelp.compose.engine.view.View3D

fun View3D.tree(scene: SceneCreator.() -> Unit)
{
    scene(SceneCreator(this))
}