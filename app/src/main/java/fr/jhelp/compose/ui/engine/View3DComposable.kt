package fr.jhelp.compose.ui.engine

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.viewinterop.AndroidView
import fr.jhelp.compose.engine.dsl.SceneCreator
import fr.jhelp.compose.engine.extensions.tree
import fr.jhelp.compose.engine.view.View3D
import fr.jhelp.compose.engine.view.touch.View3DTouchAction
import fr.jhelp.compose.engine.view.touch.View3DTouchManipulation
import java.util.concurrent.atomic.AtomicBoolean

class View3DComposable
{
    private val viewCreated = AtomicBoolean(false)
    private var action: View3DTouchAction = View3DTouchManipulation
    private lateinit var view: View3D

    var view3DTouchAction: View3DTouchAction
        get() =
            if (this.viewCreated.get())
            {
                this.view.view3DTouchAction
            }
            else
            {
                this.action
            }
        set(value)
        {
            if (this.viewCreated.get())
            {
                this.view.view3DTouchAction = value
            }
            else
            {
                this.action = value
            }
        }

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
                    this.view.view3DTouchAction = this.action
                }

                this.view.tree(scene)
                this.view
            })
    }
}