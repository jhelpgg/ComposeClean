package fr.jhelp.composeclean.models.shared.preview

import fr.jhelp.compose.mutable.Mutable
import fr.jhelp.compose.ui.recycler.RecyclerModel
import fr.jhelp.composeclean.models.SortType
import fr.jhelp.composeclean.models.contact.Contact
import fr.jhelp.composeclean.models.contact.ContactFirstNameComparator
import fr.jhelp.composeclean.models.contact.ContactLastNameComparator
import fr.jhelp.composeclean.models.contact.ContactPerson
import fr.jhelp.composeclean.models.contact.ContactSeparator
import fr.jhelp.composeclean.models.shared.ContactListModel
import java.lang.RuntimeException

object ContactListModelPreview : ContactListModel
{
    override fun initialize(recyclerModel: RecyclerModel<Contact>, sortType: Mutable<SortType>)
    {
        for(character in 'A' .. 'Z') {
            recyclerModel += ContactSeparator(character.toString())
        }

        // Preview names
        recyclerModel += ContactPerson("Jhon", "Doe")
        recyclerModel += ContactPerson("Dandy", "Space")
        recyclerModel += ContactPerson("Arthur", "Dent")
        recyclerModel += ContactPerson("Diego", "Vega")
        recyclerModel += ContactPerson("Joe", "Baby")
        recyclerModel += ContactPerson("Jackie", "Chan")
        recyclerModel += ContactPerson("Ryo", "Saeba")
        recyclerModel += ContactPerson("Curtis", "Newton")
        recyclerModel += ContactPerson("Barry", "Allen")
        recyclerModel += ContactPerson("Reed", "Richards")
        recyclerModel += ContactPerson("Susan", "Storm")
        recyclerModel += ContactPerson("Johnny", "Storm")
        recyclerModel += ContactPerson("Benjamin", "Grimm")
        //

        when(sortType.get()) {
            SortType.SORT_BY_FIRST_NAME -> recyclerModel.sort(ContactFirstNameComparator)
            SortType.SORT_BY_LAST_NAME -> recyclerModel.sort(ContactLastNameComparator)
        }
    }

    override fun toggleSort() = Unit

    override fun filter(filter: String) = Unit
}