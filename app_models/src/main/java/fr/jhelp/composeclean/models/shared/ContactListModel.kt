package fr.jhelp.composeclean.models.shared

import androidx.compose.runtime.State
import fr.jhelp.android.library.common.ui.recycler.RecyclerModel
import fr.jhelp.composeclean.models.SortType
import fr.jhelp.composeclean.models.contact.Contact

/**
 * Contact list activity model
 */
interface ContactListModel
{
    /**
     * Observable on current sort order
     */
    val sortTypeState: State<SortType>

    /**
     * Initialize the model
     *
     * @param recyclerModel Recycler model of contact list to manipulate it
     */
    fun initialize(recyclerModel: RecyclerModel<Contact>)

    /**
     * Change the sort type action
     */
    fun toggleSort()

    /**
     * Filter the content
     *
     * Empty filter will remove filter
     *
     * @param filter Filter to apply
     */
    fun filter(filter: String)
}