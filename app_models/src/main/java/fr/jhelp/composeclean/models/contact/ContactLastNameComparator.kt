package fr.jhelp.composeclean.models.contact

object ContactLastNameComparator : Comparator<Contact>
{
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