package fr.jhelp.composeclean.models.contact

data class ContactPerson(val firstName: String,
                         val lastName: String,
                         val secretIdentity: String = "") : Contact
