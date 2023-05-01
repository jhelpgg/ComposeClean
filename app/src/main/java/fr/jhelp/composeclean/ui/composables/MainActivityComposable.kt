package fr.jhelp.composeclean.ui.composables

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import fr.jhelp.compose.provider.provided
import fr.jhelp.composeclean.models.shared.NavigationModel
import fr.jhelp.composeclean.models.shared.Screens

class MainActivityComposable
{
    private val navigationModel: NavigationModel by provided<NavigationModel>()
    private val mainPresentation: MainPresentationComposable by lazy { MainPresentationComposable() }
    private val contactList: ContactListComposable by lazy { ContactListComposable() }

    /**
     * Show the main activity
     */
    @Composable
    fun Show()
    {
        val screen: Screens = this.navigationModel.screen.value

        when (screen)
        {
            Screens.MAIN_PRESENTATION -> this.mainPresentation.Show()
            Screens.CONTACT_LIST      -> this.contactList.Show()
            else                      -> Text(text = "Not implemented screen : $screen")
        }
    }
}