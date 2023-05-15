package fr.jhelp.composeclean.ui.composables.eyeChooser

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import fr.jhelp.compose.engine.resources.Eyes
import fr.jhelp.compose.provider.provided
import fr.jhelp.compose.ui.recycler.RecyclerComposable
import fr.jhelp.composeclean.models.shared.EyeChooserModel

/**
 * Choose a eye
 */
class EyeChooserComposable
{
    private val eyeChooserModel: EyeChooserModel by provided<EyeChooserModel>()

    /**
     * Show the eye chooser
     */
    @Composable
    fun Show()
    {
        val select = this.eyeChooserModel::select
        val recyclerView =
            RecyclerComposable<Eyes> { eye ->
                DrawEyeChooserElementComposable(eye = eye, click = select)
            }
        recyclerView.gridSpan = 2
        this.eyeChooserModel.initialize(recyclerView.recyclerModel)
        recyclerView.Draw(Modifier.fillMaxSize())
    }
}
