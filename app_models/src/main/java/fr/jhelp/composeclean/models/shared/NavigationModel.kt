package fr.jhelp.composeclean.models.shared


import androidx.compose.runtime.State
import fr.jhelp.composeclean.models.shared.dialogs.DialogMessageOptionButton
import fr.jhelp.composeclean.models.shared.dialogs.DialogMessageOptions
import fr.jhelp.composeclean.models.shared.dialogs.DialogModel
import fr.jhelp.composeclean.models.source.text.TextSource
import fr.jhelp.tasks.future.FutureResult

/***
 * Model for navigation between view.
 *
 * It decides witch view to show to the user and when have to change
 */
interface NavigationModel
{
    /** Observable on current screen */
    val screen: State<Screens>

    /** Observable on current dialog */
    val dialog: State<DialogModel>

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

    /**
     * Choose an image
     */
    fun chooseImage()

    /**
     * Choose a color
     */
    fun chooseColor()

    /**
     * Choose a mask
     */
    fun chooseMask()

    /**
     * Choose a bump image
     */
    fun chooseBumpImage()

    /**
     * Choose an ete
     */
    fun chooseEye()

    /**
     * Choose a mouth
     */
    fun chooseMouth()

    /**
     * Close current dialog
     */
    fun closeDialog()

    /** Show a dialog message option */
    fun dialogOption(title: TextSource,
                     message: TextSource,
                     options: DialogMessageOptions): FutureResult<DialogMessageOptionButton>
}
