package fr.jhelp.composeclean.ui.composables.maskChooser

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import fr.jhelp.android.library.provider.provided
import fr.jhelp.compose.ui.recycler.RecyclerComposable
import fr.jhelp.composeclean.models.shared.ImageMasks
import fr.jhelp.composeclean.models.shared.MaskChooserModel

/**
 * Choose a mask composable
 */
class MaskChooserComposable
{
    private val maskChooserModel: MaskChooserModel by provided<MaskChooserModel>()

    /**
     * Show the image chooser image list
     */
    @Composable
    fun Show()
    {
        val select = this.maskChooserModel::select
        val recyclerView =
            RecyclerComposable<ImageMasks> { image ->
                DrawMaskChooserElement(image = image, click = select)
            }
        this.maskChooserModel.initialize(recyclerView.recyclerModel)
        recyclerView.Draw(Modifier.fillMaxSize())
    }
}