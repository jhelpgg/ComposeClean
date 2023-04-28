package fr.jhelp.composeclean.models.contact

/**
 * Contact element that represents a person
 * @property firstName Person's first name
 * @property lastName Person's last name
 * @property secretIdentity Person's secret identity
 */
data class ContactPerson(val firstName: String,
                         val lastName: String,
                         val secretIdentity: String = "") : Contact
