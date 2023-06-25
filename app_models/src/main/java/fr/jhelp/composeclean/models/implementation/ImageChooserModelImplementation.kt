package fr.jhelp.composeclean.models.implementation

import fr.jhelp.android.library.provider.provided
import fr.jhelp.android.library.common.ui.recycler.RecyclerModel
import fr.jhelp.composeclean.models.shared.ImageChooserModel
import fr.jhelp.composeclean.models.shared.NavigationModel

internal class ImageChooserModelImplementation : ImageChooserModel
{
    private val navigationModel: NavigationModel by provided<NavigationModel>()
    override var selectImageListener: (Int) -> Unit = {}

    override fun initialize(recyclerModel: RecyclerModel<Int>, vararg images: Int)
    {
        recyclerModel.clear()

        for (image in images)
        {
            recyclerModel += image
        }
    }

    override fun select(image: Int)
    {
        this.selectImageListener(image)
        this.navigationModel.back()
    }
}