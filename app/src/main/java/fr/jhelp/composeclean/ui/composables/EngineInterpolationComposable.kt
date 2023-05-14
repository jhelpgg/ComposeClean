package fr.jhelp.composeclean.ui.composables

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import fr.jhelp.compose.engine.view.touch.View3DTouchNothing
import fr.jhelp.compose.provider.provided
import fr.jhelp.compose.ui.engine.View3DComposable
import fr.jhelp.composeclean.R
import fr.jhelp.composeclean.models.shared.EngineInterpolationModel

/**
 * Show interpolation show case
 */
class EngineInterpolationComposable
{
    private val engineInterpolationModel: EngineInterpolationModel by provided<EngineInterpolationModel>()

    /**
     * Show interpolation show case
     */
    @Composable
    fun Show()
    {
        val view3D = View3DComposable()
        view3D.view3DTouchAction = View3DTouchNothing
        view3D.Draw(modifier = Modifier.fillMaxSize()) {
            this@EngineInterpolationComposable.engineInterpolationModel.scene(this.scene3D,
                                                                              R.drawable.floor)
        }
    }
}