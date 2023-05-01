package fr.jhelp.composeclean.models.implementation

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import fr.jhelp.composeclean.models.shared.NavigationModel
import fr.jhelp.composeclean.models.shared.Screens

internal class NavigationModelImplementation : NavigationModel
{
    private val screenMutable = mutableStateOf<Screens>(Screens.MAIN_PRESENTATION)
    override val screen: State<Screens> = this.screenMutable

    override fun back(): Boolean
    {
        if (this.screenMutable.value == Screens.MAIN_PRESENTATION)
        {
            return true
        }

        this.screenMutable.value = Screens.MAIN_PRESENTATION
        return false
    }

    internal fun chooseScreen(screen: Screens)
    {
        if (screen != Screens.MAIN_PRESENTATION && this.screenMutable.value == Screens.MAIN_PRESENTATION)
        {
            this.screenMutable.value = screen
        }
    }
}