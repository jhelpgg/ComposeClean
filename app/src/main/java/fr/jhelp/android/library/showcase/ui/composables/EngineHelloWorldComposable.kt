package fr.jhelp.android.library.showcase.ui.composables

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import fr.jhelp.android.library.showcase.ui.engine.View3DComposable

/**
 * Show the engine 3d "Hello world" : A cube with default material
 */
class EngineHelloWorldComposable
{
    /**
     * Show the engine 3d "Hello world" : A cube with default material
     */
    @Composable
    fun Show()
    {
        val view3D = View3DComposable()
        view3D.Draw(modifier = Modifier.fillMaxSize()) {
            this.scenePosition { this.z = -2f }
            this.root {
                this.box { }
            }
        }
    }
}