package fr.jhelp.composeclean.models.implementation

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import fr.jhelp.compose.provider.provided
import fr.jhelp.compose.ui.recycler.RecyclerModel
import fr.jhelp.composeclean.models.shared.ImageMasks
import fr.jhelp.composeclean.models.shared.MaskChooserModel
import fr.jhelp.composeclean.models.shared.NavigationModel

internal class MaskChooserModelImplementation : MaskChooserModel
{
    private val navigationModel: NavigationModel by provided<NavigationModel>()
    private val currentSelectedMaskMutable = mutableStateOf<ImageMasks>(ImageMasks.SMALL_CIRCLE)
    override val currentSelectedMask: State<ImageMasks> = this.currentSelectedMaskMutable

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
        this.currentSelectedMaskMutable.value = mask
        this.navigationModel.back()
    }
}