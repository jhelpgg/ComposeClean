package fr.jhelp.composeclean.ui.composables.imageChooser

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import fr.jhelp.android.library.provider.provided
import fr.jhelp.compose.ui.recycler.RecyclerComposable
import fr.jhelp.composeclean.R
import fr.jhelp.android.library.models.shared.ImageChooserModel

/**
 * Choose an image composable
 */
class ImageChooserComposable
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
                                          fr.jhelp.android.library.engine.R.drawable.body_costume,
                                          R.drawable.floor,
                                          R.drawable.default_screen,
                                          fr.jhelp.android.library.engine.R.drawable.dice)
        recyclerView.Draw(Modifier.fillMaxSize())
    }
}