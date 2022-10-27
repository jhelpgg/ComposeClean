package fr.jhelp.composeclean.models.implementation

import android.os.SystemClock
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
import kotlin.random.Random
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.launch

/**
 * Contact list activity model implementation
 */
internal class ContactListModelImplementation : ContactListModel
{
    /** Coroutine scope to launch task in parallel */
    private val scope = CoroutineScope(SupervisorJob() + Dispatchers.Default)

    /** Recycler model of contact list to manipulate it */
    private lateinit var recyclerModel: RecyclerModel<Contact>

    /** mutable state of sort type*/
    private val sortTypeMutableState: MutableState<SortType> =
        mutableStateOf(SortType.SORT_BY_FIRST_NAME)
    /** Shared sort type state */
    override val sortTypeState: State<SortType> get() = this.sortTypeMutableState

    /**
     * Initialize the model
     *
     * @param recyclerModel Recycler model of contact list to manipulate it
     * @param sortType Holder of sort type to able get/change it
     */
    override fun initialize(recyclerModel: RecyclerModel<Contact>)
    {
        this.recyclerModel = recyclerModel

        // Fill the model

        this.fillAlphabet()
        this.scope.launch { this@ContactListModelImplementation.fillContact() }
            .invokeOnCompletion { this.update() }
    }


    /**
     * Change the sort type action
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
     * Filter the content
     *
     * Empty filter will remove filter
     *
     * @param filter Filter to apply
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
     * Fill letters
     */
    private fun fillAlphabet()
    {
        for (character in 'A'..'Z')
        {
            this.recyclerModel += ContactSeparator(character.toString())
        }
    }

    /**
     * Fill contacts
     */
    private fun fillContact()
    {
        // NOTE : In real application we will get te real contact here, for the demo, we just provide some contacts
        this.recyclerModel += ContactPerson("Jhon", "Doe")
        this.recyclerModel += ContactPerson("Dandy", "Space")
        this.recyclerModel += ContactPerson("Arthur", "Dent")
        this.recyclerModel += ContactPerson("Diego", "Vega", "Zorro")
        this.recyclerModel += ContactPerson("Joe", "Baby")
        this.recyclerModel += ContactPerson("Jackie", "Chan")
        this.recyclerModel += ContactPerson("Ryo", "Saeba", "City Hunter")
        this.recyclerModel += ContactPerson("Curtis", "Newton", "Captain Future")
        this.recyclerModel += ContactPerson("Barry", "Allen", "Flash")
        this.recyclerModel += ContactPerson("Reed", "Richards", "Mister fantastic")
        this.recyclerModel += ContactPerson("Susan", "Storm", "Invisible woman")
        this.recyclerModel += ContactPerson("Johnny", "Storm", "Fire man")
        this.recyclerModel += ContactPerson("Benjamin", "Grimm", "The thing")
        this.recyclerModel += ContactPerson("Bruce", "Wayne", "Batman")
        this.recyclerModel += ContactPerson("Clark", "Kent", "Superman")

        for (firstCharacter in 'A'..'Z')
        {
            for (lastCharacter in 'A'..'Z')
            {
                this.recyclerModel += ContactPerson(name(firstCharacter), name(lastCharacter))
                Thread.sleep(1)
            }
        }
    }

    /**
     * Compute a random name start by given letter
     */
    private fun name(firstLetter: Char): String
    {
        val random = Random(SystemClock.elapsedRealtime())
        val name = StringBuilder()
        name.append(firstLetter)
        val number = random.nextInt(4, 9)

        for (index in 0 until number)
        {
            name.append(random.nextInt('a'.code, 'z'.code + 1).toChar())
        }

        return name.toString()
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