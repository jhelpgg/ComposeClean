package fr.jhelp.composeclean.models.shared

import fr.jhelp.compose.mutable.Mutable
import fr.jhelp.compose.ui.recycler.RecyclerModel
import fr.jhelp.composeclean.models.SortType
import fr.jhelp.composeclean.models.contact.Contact

/**
 * Contact list activity model
 */
interface ContactListModel
{
    /**
     * Initialize the model
     *
     * @param recyclerModel Recycler model of contact list to manipulate it
     * @param sortType Holder of sort type to able get/change it
     */
    fun initialize(recyclerModel: RecyclerModel<Contact>, sortType: Mutable<SortType>)

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