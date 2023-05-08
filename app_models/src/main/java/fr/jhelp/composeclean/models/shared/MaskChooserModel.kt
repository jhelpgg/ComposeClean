package fr.jhelp.composeclean.models.shared

import androidx.compose.runtime.State
import fr.jhelp.compose.ui.recycler.RecyclerModel

/**
 * Model for choose a mask
 */
interface MaskChooserModel
{
    /** Current selected mask */
    val currentSelectedMask: State<ImageMasks>

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