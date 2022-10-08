package fr.jhelp.composeclean.models

import fr.jhelp.compose.provider.provideSingle
import fr.jhelp.composeclean.models.implementation.ContactListModelImplementation
import fr.jhelp.composeclean.models.implementation.MainModelImplementation
import fr.jhelp.composeclean.models.shared.ContactListModel
import fr.jhelp.composeclean.models.shared.MainModel

fun injectModels()
{
    provideSingle<MainModel> { MainModelImplementation() }
    provideSingle<ContactListModel> { ContactListModelImplementation() }
}