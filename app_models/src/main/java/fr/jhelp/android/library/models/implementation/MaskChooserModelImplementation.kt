package fr.jhelp.android.library.models.implementation

import fr.jhelp.android.library.provider.provided
import fr.jhelp.android.library.common.ui.recycler.RecyclerModel
import fr.jhelp.android.library.models.shared.ImageMasks
import fr.jhelp.android.library.models.shared.MaskChooserModel
import fr.jhelp.android.library.models.shared.NavigationModel

internal class MaskChooserModelImplementation : MaskChooserModel
{
    private val navigationModel: NavigationModel by provided<NavigationModel>()
    override var selectedMaskListener: (ImageMasks) -> Unit = {}

    override fun initialize(recyclerModel: RecyclerModel<ImageMasks>)
    {
        recyclerModel.clear()

        for (imageMak in ImageMasks.values())
        {
            recyclerModel += imageMak
        }
    }

    override fun select(mask: ImageMasks)
    {
        this.selectedMaskListener(mask)
        this.navigationModel.back()
    }
}