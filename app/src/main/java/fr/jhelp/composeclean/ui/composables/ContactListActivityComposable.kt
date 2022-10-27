package fr.jhelp.composeclean.ui.composables

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.Dimension
import fr.jhelp.compose.constraint.extensions.bottomParent
import fr.jhelp.compose.constraint.extensions.endParent
import fr.jhelp.compose.constraint.extensions.near
import fr.jhelp.compose.constraint.extensions.next
import fr.jhelp.compose.constraint.extensions.startParent
import fr.jhelp.compose.constraint.extensions.topParent
import fr.jhelp.compose.provider.provideSingle
import fr.jhelp.compose.provider.provided
import fr.jhelp.compose.ui.recycler.RecyclerComposable
import fr.jhelp.composeclean.R
import fr.jhelp.composeclean.models.SortType
import fr.jhelp.composeclean.models.contact.Contact
import fr.jhelp.composeclean.models.shared.ContactListModel
import fr.jhelp.composeclean.models.shared.preview.ContactListModelPreview
import fr.jhelp.composeclean.ui.composables.contact.DrawContact
import fr.jhelp.composeclean.ui.theme.ComposeCleanTheme

/**
 * Contact list activity composable
 */
class ContactListActivityComposable
{
    private val contactListModel: ContactListModel by provided<ContactListModel>()

    @OptIn(ExperimentalMaterial3Api::class)
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
            val (filter, contactList, buttonFirstName, buttonLastName) = createRefs()

            TextField(
                value = filtered,
                onValueChange = { filterTyped ->
                    // We store and update the filter, so additional letters are not lost and delete action is preserve
                    filtered = filterTyped
                    // Since we have the recycler model we can be tempted to filter the recycler
                    // composable form here.
                    // But this not respect our good practice, we delegate the filter to the model,
                    // because it is model side the "intelligence" should be
                    this@ContactListActivityComposable.contactListModel
                        .filter(filterTyped)
                },
                label = { Text(stringResource(R.string.hintFilter)) },
                modifier = Modifier.constrainAs(filter) {
                    width = Dimension.fillToConstraints
                    height = Dimension.wrapContent
                    topParent
                    startParent
                    endParent
                })

            recyclerView.Draw(Modifier.constrainAs(contactList) {
                width = Dimension.fillToConstraints
                height = Dimension.fillToConstraints
                top near filter.bottom
                bottom next buttonFirstName.top
                startParent
                endParent
            })

            val buttonGuideline = createGuidelineFromStart(0.5f)

            // Same remarks as above about recycler compose model
            Button(onClick = { this@ContactListActivityComposable.contactListModel.toggleSort() },
                   enabled = sortType.value == SortType.SORT_BY_LAST_NAME,
                   modifier = Modifier.constrainAs(buttonFirstName) {
                       width = Dimension.wrapContent
                       height = Dimension.wrapContent
                       bottomParent
                       startParent
                       end near buttonGuideline
                   }) {
                Text(text = stringResource(R.string.buttonFirstName))
            }

            // Same remarks as above about recycler compose model
            Button(onClick = { this@ContactListActivityComposable.contactListModel.toggleSort() },
                   enabled = sortType.value == SortType.SORT_BY_FIRST_NAME,
                   modifier = Modifier.constrainAs(buttonLastName) {
                       width = Dimension.wrapContent
                       height = Dimension.wrapContent
                       bottomParent
                       start near buttonGuideline
                       endParent
                   }) {
                Text(text = stringResource(R.string.buttonLastName))
            }
        }
    }

    @Preview(showBackground = true)
    @Composable
    fun Preview()
    {
        provideSingle<ContactListModel> { ContactListModelPreview }
        ComposeCleanTheme {
            this.Show()
        }
    }
}