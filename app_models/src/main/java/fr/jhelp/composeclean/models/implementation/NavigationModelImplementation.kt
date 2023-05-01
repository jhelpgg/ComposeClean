package fr.jhelp.composeclean.models.implementation

import fr.jhelp.composeclean.models.shared.NavigationModel
import fr.jhelp.composeclean.models.shared.Screens
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

internal class NavigationModelImplementation : NavigationModel
{
    private val screenMutable = MutableStateFlow<Screens>(Screens.MAIN_PRESENTATION)
    override val screen: StateFlow<Screens> = this.screenMutable.asStateFlow()

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