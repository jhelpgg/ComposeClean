package fr.jhelp.composeclean.models.shared

import fr.jhelp.compose.mutable.Mutable
import fr.jhelp.compose.ui.recycler.RecyclerModel
import fr.jhelp.composeclean.models.SortType
import fr.jhelp.composeclean.models.contact.Contact

interface ContactListModel
{
    fun initialize(recyclerModel: RecyclerModel<Contact>, sortType: Mutable<SortType>)

    fun toggleSort()

    fun filter(filter: String)
}