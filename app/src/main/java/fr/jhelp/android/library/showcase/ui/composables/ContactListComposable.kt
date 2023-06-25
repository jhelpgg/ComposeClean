package fr.jhelp.android.library.showcase.ui.composables

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.Dimension
import fr.jhelp.android.library.constraint.extensions.bottomParent
import fr.jhelp.android.library.constraint.extensions.endParent
import fr.jhelp.android.library.constraint.extensions.near
import fr.jhelp.android.library.constraint.extensions.next
import fr.jhelp.android.library.constraint.extensions.startParent
import fr.jhelp.android.library.constraint.extensions.topParent
import fr.jhelp.android.library.provider.provided
import fr.jhelp.android.library.showcase.ui.recycler.RecyclerComposable
import fr.jhelp.android.library.showcase.R
import fr.jhelp.android.library.models.SortType
import fr.jhelp.android.library.models.contact.Contact
import fr.jhelp.android.library.models.shared.ContactListModel
import fr.jhelp.android.library.showcase.ui.composables.contact.DrawContact

/**
 * Contact list activity composable
 */
class ContactListComposable
{
    private val contactListModel: ContactListModel by provided<ContactListModel>()

    /**
     * Show the contact list
     */
    @Composable
    fun Show()
    {
        // Create holders and initialize the model
        val recyclerView = RecyclerComposable<Contact> { contact -> DrawContact(contact) }
        val sortType = this.contactListModel.sortTypeState
        this.contactListModel.initialize(recyclerView.recyclerModel)
        // Trick for cumulate filter text
        var filtered: String by remember { mutableStateOf("") }

        ConstraintLayout(modifier = Modifier.fillMaxSize()) {
            val (filter, contactList, buttonFirstName, buttonLastName) = this.createRefs()

            TextField(
                value = filtered,
                onValueChange = { filterTyped ->
                    // We store and update the filter, so additional letters are not lost and delete action is preserve
                    filtered = filterTyped
                    // Since we have the recycler model we can be tempted to filter the recycler
                    // composable form here.
                    // But this not respect our good practice, we delegate the filter to the model,
                    // because it is model side the "intelligence" should be
                    this@ContactListComposable.contactListModel
                        .filter(filterTyped)
                },
                label = { Text(stringResource(R.string.hintFilter)) },
                modifier = Modifier.constrainAs(filter) {
                    this.width = Dimension.fillToConstraints
                    this.height = Dimension.wrapContent
                    this.topParent
                    this.startParent
                    this.endParent
                })

            recyclerView.Draw(Modifier.constrainAs(contactList) {
                this.width = Dimension.fillToConstraints
                this.height = Dimension.fillToConstraints
                this.top near filter.bottom
                this.bottom next buttonFirstName.top
                this.startParent
                this.endParent
            })

            val buttonGuideline = this.createGuidelineFromStart(0.5f)

            // Same remarks as above about recycler compose model
            Button(onClick = { this@ContactListComposable.contactListModel.toggleSort() },
                   enabled = sortType.value == SortType.SORT_BY_LAST_NAME,
                   modifier = Modifier.constrainAs(buttonFirstName) {
                       this.width = Dimension.wrapContent
                       this.height = Dimension.wrapContent
                       this.bottomParent
                       this.startParent
                       this.end near buttonGuideline
                   }) {
                Text(text = stringResource(R.string.buttonFirstName))
            }

            // Same remarks as above about recycler compose model
            Button(onClick = { this@ContactListComposable.contactListModel.toggleSort() },
                   enabled = sortType.value == SortType.SORT_BY_FIRST_NAME,
                   modifier = Modifier.constrainAs(buttonLastName) {
                       this.width = Dimension.wrapContent
                       this.height = Dimension.wrapContent
                       this.bottomParent
                       this.start near buttonGuideline
                       this.endParent
                   }) {
                Text(text = stringResource(R.string.buttonLastName))
            }
        }
    }
}