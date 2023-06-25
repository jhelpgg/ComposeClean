package fr.jhelp.android.library.models.contact

/**
 * Comparator of contact by their first name
 */
object ContactFirstNameComparator : Comparator<Contact>
{
    /**
     * Compare two contacts by their first name
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
                        firstContact.separator.compareTo(secondContact.firstName, ignoreCase = true)
                }
            is ContactPerson    ->
                when (secondContact)
                {
                    is ContactSeparator ->
                        firstContact.firstName.compareTo(secondContact.separator, ignoreCase = true)
                    is ContactPerson    ->
                        firstContact.firstName.compareTo(secondContact.firstName, ignoreCase = true)
                }
        }
}