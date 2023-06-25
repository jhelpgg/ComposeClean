package fr.jhelp.composeclean.models.shared

import fr.jhelp.android.library.engine.resources.Mouths
import fr.jhelp.android.library.common.ui.recycler.RecyclerModel

/**
 * Model for choose a mouth
 */
interface MouthChooserModel
{
    /** Called when an eye is choose */
    var selectMouthListener: (Mouths) -> Unit

    /**
     * Initialize the model an fill the list of choice
     */
    fun initialize(recyclerModel: RecyclerModel<Mouths>)

    /**
     * Called when selection is done
     */
    fun select(mouth: Mouths)
}
