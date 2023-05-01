package fr.jhelp.composeclean.models.shared

import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

/***
 * Model for navigation between view.
 *
 * It decides witch view to show to the user and when have to change
 */
interface NavigationModel
{
   /** Observable on current screen */
   val screen : StateFlow<Screens>

    /**
     * Called when user press back button
     * @return `true` if have to exit application
     */
   fun back() : Boolean
}
