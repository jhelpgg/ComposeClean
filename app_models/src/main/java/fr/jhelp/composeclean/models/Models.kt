package fr.jhelp.composeclean.models

import fr.jhelp.compose.provider.provideSingle
import fr.jhelp.composeclean.models.implementation.ContactListModelImplementation
import fr.jhelp.composeclean.models.implementation.MainPresentationModelImplementation
import fr.jhelp.composeclean.models.implementation.NavigationModelImplementation
import fr.jhelp.composeclean.models.shared.ContactListModel
import fr.jhelp.composeclean.models.shared.MainPresentationModel
import fr.jhelp.composeclean.models.shared.NavigationModel

/**
 * Inject models implementations to share them
 */
fun injectModels()
{
    provideSingle<NavigationModel> { NavigationModelImplementation() }
    provideSingle<MainPresentationModel> { MainPresentationModelImplementation() }
    provideSingle<ContactListModel> { ContactListModelImplementation() }
}