package fr.jhelp.composeclean.ui.composables.contact

import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import fr.jhelp.composeclean.models.contact.Contact
import fr.jhelp.composeclean.models.contact.ContactPerson
import fr.jhelp.composeclean.models.contact.ContactSeparator
import fr.jhelp.composeclean.ui.theme.ComposeCleanTheme

@Composable
fun DrawContact(contact: Contact)
{
    when (contact)
    {
        is ContactSeparator -> DrawContactSeparator(contact)
        is ContactPerson    -> DrawContactPerson(contact)
    }
}

@Preview(showBackground = true)
@Composable
fun DrawContactPreview()
{
    ComposeCleanTheme {
        DrawContact(ContactPerson("Test", "TryAgain"))
    }
}