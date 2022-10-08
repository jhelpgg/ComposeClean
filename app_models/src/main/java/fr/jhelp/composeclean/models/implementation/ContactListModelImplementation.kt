package fr.jhelp.composeclean.models.implementation

import android.os.SystemClock
import fr.jhelp.compose.mutable.Mutable
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

internal class ContactListModelImplementation : ContactListModel
{
    private val scope = CoroutineScope(SupervisorJob() + Dispatchers.Default)
    private lateinit var recyclerModel: RecyclerModel<Contact>
    private lateinit var sortType: Mutable<SortType>

    override fun initialize(recyclerModel: RecyclerModel<Contact>, sortType: Mutable<SortType>)
    {
        this.recyclerModel = recyclerModel
        this.sortType = sortType

        this.fillAlphabet()
        this.scope.launch { this@ContactListModelImplementation.fillContact() }
            .invokeOnCompletion { this.update() }
    }

    override fun toggleSort()
    {
        val newSortType =
            when (this.sortType.get())
            {
                SortType.SORT_BY_FIRST_NAME -> SortType.SORT_BY_LAST_NAME
                SortType.SORT_BY_LAST_NAME  -> SortType.SORT_BY_FIRST_NAME
            }

        this.update(newSortType)
        this.sortType.set(newSortType)
    }

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

    private fun fillAlphabet()
    {
        for (character in 'A'..'Z')
        {
            this.recyclerModel += ContactSeparator(character.toString())
        }
    }

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

    private fun update(sortType: SortType = this.sortType.get())
    {
        when (sortType)
        {
            SortType.SORT_BY_FIRST_NAME -> this.recyclerModel.sort(ContactFirstNameComparator)
            SortType.SORT_BY_LAST_NAME  -> this.recyclerModel.sort(ContactLastNameComparator)
        }
    }
}