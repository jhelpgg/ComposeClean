package fr.jhelp.composeclean.models.implementation

import fr.jhelp.compose.engine.resources.Eyes
import fr.jhelp.android.library.provider.provided
import fr.jhelp.compose.ui.recycler.RecyclerModel
import fr.jhelp.composeclean.models.shared.EyeChooserModel
import fr.jhelp.composeclean.models.shared.NavigationModel

internal class EyeChooserModelImplementation : EyeChooserModel
{
    private val navigationModel: NavigationModel by provided<NavigationModel>()

    override var selectEyeListener: (Eyes) -> Unit = {}

    override fun initialize(recyclerModel: RecyclerModel<Eyes>)
    {
        recyclerModel.clear()

        for (eye in Eyes.values())
        {
            recyclerModel += eye
        }
    }

    override fun select(eye: Eyes)
    {
        this.selectEyeListener(eye)
        this.navigationModel.back()
    }
}
