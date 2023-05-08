package fr.jhelp.composeclean.models.implementation

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import fr.jhelp.compose.provider.provided
import fr.jhelp.compose.ui.recycler.RecyclerModel
import fr.jhelp.composeclean.models.shared.ImageChooserModel
import fr.jhelp.composeclean.models.shared.NavigationModel

internal class ImageChooserModelImplementation : ImageChooserModel
{
    private val navigationModel: NavigationModel by provided<NavigationModel>()
    private var currentSelectedImageMutable = mutableStateOf<Int>(android.R.drawable.ic_delete)
    override val currentSelectedImage: State<Int> = this.currentSelectedImageMutable

    override fun initialize(recyclerModel: RecyclerModel<Int>, vararg images: Int)
    {
        recyclerModel.clear()

        for (image in images)
        {
            recyclerModel += image
        }

        this.currentSelectedImageMutable.value = android.R.drawable.ic_delete
    }

    override fun select(image: Int)
    {
        this.currentSelectedImageMutable.value = image
        this.navigationModel.back()
    }
}