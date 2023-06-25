package fr.jhelp.android.library.models.shared

import fr.jhelp.android.library.common.ui.recycler.RecyclerModel

/**
 * Model for choose a mask
 */
interface MaskChooserModel
{
    /** Called when image choose  */
    var selectedMaskListener: (ImageMasks) -> Unit

    /**
     * Initialize the model
     *
     * @param recyclerModel Recycler model of mask possibles to choose
     */
    fun initialize(recyclerModel: RecyclerModel<ImageMasks>)


    /**
     * Called when selection is done
     */
    fun select(mask: ImageMasks)
}