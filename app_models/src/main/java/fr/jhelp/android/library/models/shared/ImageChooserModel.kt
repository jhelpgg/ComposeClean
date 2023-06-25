package fr.jhelp.android.library.models.shared

import androidx.annotation.DrawableRes
import fr.jhelp.android.library.common.ui.recycler.RecyclerModel

/**
 * Model for choose an image
 */
interface ImageChooserModel
{
    /** Called when image choose */
    var selectImageListener: (Int) -> Unit

    /**
     * Initialize the model
     *
     * @param recyclerModel Recycler model of image possibles to choose
     * @param images List of possible images
     */
    fun initialize(recyclerModel: RecyclerModel<Int>, @DrawableRes vararg images: Int)


    /**
     * Called when selection is done
     */
    fun select(@DrawableRes image: Int)
}
