package fr.jhelp.composeclean.models.shared

import fr.jhelp.compose.engine.resources.Eyes
import fr.jhelp.compose.ui.recycler.RecyclerModel

/**
 * Model for choose an eye
 */
interface EyeChooserModel
{
    /** Called when an eye is choose */
    var selectEyeListener: (Eyes) -> Unit

    /**
     * Initialize the model an fill the list of choice
     */
    fun initialize(recyclerModel: RecyclerModel<Eyes>)

    /**
     * Called when selection is done
     */
    fun select(eye: Eyes)
}