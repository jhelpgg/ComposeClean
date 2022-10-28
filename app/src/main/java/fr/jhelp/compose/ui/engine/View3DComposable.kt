package fr.jhelp.compose.ui.engine

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.viewinterop.AndroidView
import fr.jhelp.compose.engine.dsl.SceneCreator
import fr.jhelp.compose.engine.extensions.tree
import fr.jhelp.compose.engine.view.View3D
import java.util.concurrent.atomic.AtomicBoolean

class View3DComposable
{
    private val viewCreated = AtomicBoolean(false)
    private lateinit var view: View3D

    @Composable
    fun Draw(modifier: Modifier = Modifier,
             scene: SceneCreator.() -> Unit = {
                 scenePosition { z = -2f }
                 root { box {} }
             })
    {
        AndroidView<View3D>(
            modifier = modifier,
            factory = { context ->
                if (this.viewCreated.compareAndSet(false, true))
                {
                    this.view = View3D(context)
                }

                this.view.tree(scene)
                this.view
            })
    }
}