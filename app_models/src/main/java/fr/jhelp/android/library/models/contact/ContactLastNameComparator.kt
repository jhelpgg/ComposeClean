package fr.jhelp.android.library.models.contact

/**
 * Comparator of contact by their last name
 */
object ContactLastNameComparator : Comparator<Contact>
{
    /**
     * Compare two contacts by their last name
     */
    override fun compare(firstContact: Contact, secondContact: Contact): Int =
        when (firstContact)
        {
            is ContactSeparator ->
                when (secondContact)
                {
                    is ContactSeparator ->
                        firstContact.separator.compareTo(secondContact.separator, ignoreCase = true)
                    is ContactPerson    ->
                        firstContact.separator.compareTo(secondContact.lastName, ignoreCase = true)
                }
            is ContactPerson    ->
                when (secondContact)
                {
                    is ContactSeparator ->
                        firstContact.lastName.compareTo(secondContact.separator, ignoreCase = true)
                    is ContactPerson    ->
                        firstContact.lastName.compareTo(secondContact.lastName, ignoreCase = true)
                }
        }
}