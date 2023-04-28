package fr.jhelp.composeclean.models.shared.preview

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import fr.jhelp.compose.ui.recycler.RecyclerModel
import fr.jhelp.composeclean.models.SortType
import fr.jhelp.composeclean.models.contact.Contact
import fr.jhelp.composeclean.models.contact.ContactFirstNameComparator
import fr.jhelp.composeclean.models.contact.ContactLastNameComparator
import fr.jhelp.composeclean.models.contact.ContactPerson
import fr.jhelp.composeclean.models.contact.ContactSeparator
import fr.jhelp.composeclean.models.shared.ContactListModel

/**
 * Contact list activity model used for preview
 */
object ContactListModelPreview : ContactListModel
{
    private lateinit var recyclerModel: RecyclerModel<Contact>
    private val sortTypeMutableState: MutableState<SortType> =
        mutableStateOf(SortType.SORT_BY_FIRST_NAME)

    /**
     * Contact sort type observable to have current sort and/or to observe the sort change
     */
    override val sortTypeState: State<SortType> get() = this.sortTypeMutableState

    /**
     * Called on initialization to fill some contact in preview
     */
    override fun initialize(recyclerModel: RecyclerModel<Contact>)
    {
        this.recyclerModel = recyclerModel

        for (character in 'A'..'Z')
        {
            recyclerModel += ContactSeparator(character.toString())
        }

        // Preview names
        recyclerModel += ContactPerson("John", "Doe")
        recyclerModel += ContactPerson("Dandy", "Space")
        recyclerModel += ContactPerson("Arthur", "Dent")
        recyclerModel += ContactPerson("Diego", "Vega", "Zorro")
        recyclerModel += ContactPerson("Joe", "Baby")
        recyclerModel += ContactPerson("Jackie", "Chan")
        recyclerModel += ContactPerson("Ryo", "Saeba", "City Hunter")
        recyclerModel += ContactPerson("Curtis", "Newton", "Captain Future")
        recyclerModel += ContactPerson("Barry", "Allen", "Flash")
        recyclerModel += ContactPerson("Reed", "Richards", "Mister fantastic")
        recyclerModel += ContactPerson("Susan", "Storm", "Invisible woman")
        recyclerModel += ContactPerson("Johnny", "Storm", "Fire man")
        recyclerModel += ContactPerson("Benjamin", "Grimm", "The thing")
        //

        when (this.sortTypeMutableState.value)
        {
            SortType.SORT_BY_FIRST_NAME -> recyclerModel.sort(ContactFirstNameComparator)
            SortType.SORT_BY_LAST_NAME  -> recyclerModel.sort(ContactLastNameComparator)
        }
    }

    /**
     * Change the sort type
     */
    override fun toggleSort()
    {
        // Here if we set the holder and just after get the value we have a great chance to have the previous value.
        // Because the holder is link to compose system. And it don't immediately change the value,
        // so if we request the value to soon it may have not the type to be updated, and will received value before set
        //
        // To solve this here we just not refer to get to have the value but use the computed one for update the sort
        val newSortType =
            when (this.sortTypeMutableState.value)
            {
                SortType.SORT_BY_FIRST_NAME -> SortType.SORT_BY_LAST_NAME
                SortType.SORT_BY_LAST_NAME  -> SortType.SORT_BY_FIRST_NAME
            }

        this.update(newSortType)
        this.sortTypeMutableState.value = newSortType
    }

    /**
     * Change the filter
     */
    override fun filter(filter: String)
    {
        val safeFilter = filter.trim()

        if (safeFilter.isEmpty())
        {
            this.recyclerModel.removeFilter()
        }
        else
        {
            this.recyclerModel.filter { contact ->
                (contact is ContactPerson) &&
                        (contact.firstName.startsWith(safeFilter, ignoreCase = true) ||
                                contact.lastName.startsWith(safeFilter, ignoreCase = true) ||
                                contact.secretIdentity.equals(safeFilter, ignoreCase = true))
            }
        }
    }

    /**
     * Update the sort order
     */
    private fun update(sortType: SortType = this.sortTypeMutableState.value)
    {
        when (sortType)
        {
            SortType.SORT_BY_FIRST_NAME -> this.recyclerModel.sort(ContactFirstNameComparator)
            SortType.SORT_BY_LAST_NAME  -> this.recyclerModel.sort(ContactLastNameComparator)
        }
    }
}