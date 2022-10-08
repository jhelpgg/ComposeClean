package fr.jhelp.composeclean.models.contact

object ContactFirstNameComparator : Comparator<Contact>
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