package fr.jhelp.android.library.showcase.ui.composables.bumpChooser

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import fr.jhelp.android.library.provider.provided
import fr.jhelp.android.library.showcase.ui.recycler.RecyclerComposable
import fr.jhelp.android.library.showcase.R
import fr.jhelp.android.library.models.shared.ImageChooserModel
import fr.jhelp.android.library.showcase.ui.composables.imageChooser.DrawImageChooserElement

/**
 * Choose an image composable
 */
class BumpImageChooserComposable
{
    private val imageChooserModel: ImageChooserModel by provided<ImageChooserModel>()

    /**
     * Show the image chooser image list
     */
    @Composable
    fun Show()
    {
        val select = this.imageChooserModel::select
        val recyclerView =
            RecyclerComposable<Int> { image ->
                DrawImageChooserElement(image = image, click = select)
            }
        this.imageChooserModel.initialize(recyclerView.recyclerModel,
                                          R.drawable.bump1,
                                          R.drawable.floor,
                                          R.drawable.bump4,
                                          R.drawable.bump3)
        recyclerView.Draw(Modifier.fillMaxSize())
    }
}