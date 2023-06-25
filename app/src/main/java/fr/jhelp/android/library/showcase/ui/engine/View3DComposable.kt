package fr.jhelp.android.library.showcase.ui.engine

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.viewinterop.AndroidView
import fr.jhelp.android.library.engine.dsl.SceneCreator
import fr.jhelp.android.library.engine.extensions.tree
import fr.jhelp.android.library.engine.view.View3D
import fr.jhelp.android.library.engine.view.touch.View3DTouchAction
import fr.jhelp.android.library.engine.view.touch.View3DTouchManipulation
import java.util.concurrent.atomic.AtomicBoolean

/**
 * Composable with [View3D] inside.
 *
 * Convenient to have 3D inside compose UI
 */
class View3DComposable
{
    private val viewCreated = AtomicBoolean(false)
    private var action: View3DTouchAction = View3DTouchManipulation
    private lateinit var view: View3D

    /**
     * Describes how to react to touch the 3D view
     */
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

    /**
     * Call in composable context to draw the 3D
     *
     * @param modifier : Modifier used to layout the 3D view
     * @param scene : Describe how to create the 3D view
     */
    @Composable
    fun Draw(modifier: Modifier = Modifier,
             scene: SceneCreator.() -> Unit = {
                 this.scenePosition { this.z = -2f }
                 this.root { this.box {} }
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