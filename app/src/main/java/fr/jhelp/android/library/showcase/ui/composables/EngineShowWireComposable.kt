package fr.jhelp.android.library.showcase.ui.composables

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import fr.jhelp.android.library.math.AngleFloat
import fr.jhelp.android.library.math.AngleUnit
import fr.jhelp.android.library.showcase.ui.engine.View3DComposable

/**
 * Show the engine 3d with show wire frame
 */
class EngineShowWireComposable
{
    /**
     * Show the engine 3d with material texture change
     */
    @Composable
    fun Show()
    {
        val view3D = View3DComposable()
        view3D.Draw(modifier = Modifier.fillMaxSize()) {
            this.scenePosition { this.z = -2.2f }
            this.root {
                this.revolution(path = {
                    this.moveTo(0.2f, 1f)
                    this.lineTo(0.22f, 0f)
                    this.ellipticArcTo(0.5f,
                                       0.5f,
                                       AngleFloat(90f, AngleUnit.DEGREE),
                                       true,
                                       false,
                                       0f,
                                       -1f)
                }) {
                    this.doubleFace = true
                    this.showWire()
                }
            }
        }
    }
}