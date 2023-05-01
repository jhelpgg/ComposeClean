package fr.jhelp.composeclean.models.shared


import androidx.compose.runtime.State

/***
 * Model for navigation between view.
 *
 * It decides witch view to show to the user and when have to change
 */
interface NavigationModel
{
    /** Observable on current screen */
    val screen: State<Screens>

    /**
     * Called when user press back button
     * @return `true` if have to exit application
     */
    fun back(): Boolean

    /**
     * Called each time application pause
     */
    fun pause()

    /**
     * Called each time application resume
     */
    fun resume()
}
