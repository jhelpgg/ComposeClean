package fr.jhelp.composeclean.ui.composables

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import fr.jhelp.compose.provider.provided
import fr.jhelp.compose.ui.recycler.RecyclerComposable
import fr.jhelp.composeclean.models.presentation.Presentation
import fr.jhelp.composeclean.models.shared.MainPresentationModel
import fr.jhelp.composeclean.ui.composables.presentation.DrawPresentation

/**
 * Main activity composable
 */
class MainPresentationComposable
{
    private val mainPresentationModel: MainPresentationModel by provided<MainPresentationModel>()

    /**
     * Show the main activity
     */
    @Composable
    fun Show()
    {
        val select = this.mainPresentationModel::select
        val recyclerView =
            RecyclerComposable<Presentation> { presentation ->
                DrawPresentation(presentation, select)
            }
        this.mainPresentationModel.initialize(recyclerView.recyclerModel)
        // TODO : Add button(s) for filter

        recyclerView.Draw(Modifier.fillMaxSize())
    }
}