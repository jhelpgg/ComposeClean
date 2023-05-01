package fr.jhelp.composeclean.ui.composables.contact

import androidx.compose.runtime.Composable
import fr.jhelp.composeclean.models.contact.Contact
import fr.jhelp.composeclean.models.contact.ContactPerson
import fr.jhelp.composeclean.models.contact.ContactSeparator

/**
 * Composable for draw a contact element
 */
@Composable
fun DrawContact(contact: Contact)
{
    when (contact)
    {
        is ContactSeparator -> DrawContactSeparator(contact)
        is ContactPerson    -> DrawContactPerson(contact)
    }
}
