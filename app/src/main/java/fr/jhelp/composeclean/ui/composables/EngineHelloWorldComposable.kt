package fr.jhelp.composeclean.ui.composables

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import fr.jhelp.compose.ui.engine.View3DComposable

/**
 * Show the engine 3d "Hello world" : A cube with default material
 */
class EngineHelloWorldComposable
{
    private val view3D = View3DComposable()

    /**
     * Show the engine 3d "Hello world" : A cube with default material
     */
    @Composable
    fun Show() {
        this.view3D.Draw(modifier = Modifier.fillMaxSize()) {
            this.scenePosition { this.z = -2f }
            this.root {
                this.box {  }
            }
        }
    }
}