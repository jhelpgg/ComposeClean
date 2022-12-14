package fr.jhelp.composeclean.ui.composables

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
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
import fr.jhelp.compose.mutable
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

    @Composable
    fun Show()
    {
        // Create holders and initialize the model
        val recyclerView = RecyclerComposable<Contact> { contact -> DrawContact(contact) }
        val sortType = mutable(SortType.SORT_BY_FIRST_NAME)
        this.contactListModel.initialize(recyclerView.recyclerModel, sortType)
        // Trick for cumulate filter text
        var filtered: String by remember { mutableStateOf("") }

        ConstraintLayout(modifier = Modifier.fillMaxSize()) {
            val (filter, contactList, buttonFirstName, buttonLastName) = createRefs()

            BasicTextField(value = filtered, // We use the previous complete text so it cumulates
                           onValueChange = { filterTyped ->
                               filtered = filterTyped // We store and update the filter, so additional letters are not lost and delete action is preserve
                               // Since we have the recycler model we can be tempted to filter the recycler
                               // composable form here.
                               // But this not respect our good practice, we delegate the filter to the model,
                               // because it is model side the "intelligence" should be
                               this@ContactListActivityComposable.contactListModel
                                   .filter(filterTyped)
                           },
                           modifier = Modifier.constrainAs(filter) {
                               width = Dimension.fillToConstraints
                               height = Dimension.wrapContent
                               topParent
                               startParent
                               endParent
                           }) {
                // We manage the hint "manually"
                Text(text = filtered.ifEmpty { stringResource(R.string.hintFilter) },
                     color = if (filtered.isEmpty()) Color.Gray else Color.White)
            }

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
                   enabled = sortType.get() == SortType.SORT_BY_LAST_NAME,
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
                   enabled = sortType.get() == SortType.SORT_BY_FIRST_NAME,
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