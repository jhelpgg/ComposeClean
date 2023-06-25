package fr.jhelp.composeclean.models.implementation

import fr.jhelp.android.library.engine.resources.Mouths
import fr.jhelp.android.library.provider.provided
import fr.jhelp.android.library.common.ui.recycler.RecyclerModel
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
