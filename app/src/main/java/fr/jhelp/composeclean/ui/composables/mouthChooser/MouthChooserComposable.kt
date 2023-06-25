package fr.jhelp.composeclean.ui.composables.mouthChooser

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import fr.jhelp.android.library.engine.resources.Mouths
import fr.jhelp.android.library.provider.provided
import fr.jhelp.compose.ui.recycler.RecyclerComposable
import fr.jhelp.android.library.models.shared.MouthChooserModel

/**
 * Choose a mouth
 */
class MouthChooserComposable
{
    private val mouthChooserModel: MouthChooserModel by provided<MouthChooserModel>()

    /**
     * Show the mouth chooser
     */
    @Composable
    fun Show()
    {
        val select = this.mouthChooserModel::select
        val recyclerView =
            RecyclerComposable<Mouths> { mouth ->
                DrawMouthChooserElementComposable(mouth = mouth, click = select)
            }
        recyclerView.gridSpan = 2
        this.mouthChooserModel.initialize(recyclerView.recyclerModel)
        recyclerView.Draw(Modifier.fillMaxSize())
    }
}
