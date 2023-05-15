package fr.jhelp.composeclean.models.implementation

import fr.jhelp.compose.engine.resources.Mouths
import fr.jhelp.compose.provider.provided
import fr.jhelp.compose.ui.recycler.RecyclerModel
import fr.jhelp.composeclean.models.shared.MouthChooserModel
import fr.jhelp.composeclean.models.shared.NavigationModel

internal class MouthChooserModelImplementation : MouthChooserModel
{
    private val navigationModel: NavigationModel by provided<NavigationModel>()
    override var selectMouthListener: (Mouths) -> Unit = {}

    override fun initialize(recyclerModel: RecyclerModel<Mouths>)
    {
        recyclerModel.clear()

        for (mouth in Mouths.values())
        {
            recyclerModel += mouth
        }
    }

    override fun select(mouth: Mouths)
    {
        this.selectMouthListener(mouth)
        this.navigationModel.back()
    }
}