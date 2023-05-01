package fr.jhelp.composeclean.models.implementation

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import fr.jhelp.compose.sound.SoundManager
import fr.jhelp.composeclean.models.shared.NavigationModel
import fr.jhelp.composeclean.models.shared.Screens

internal class NavigationModelImplementation : NavigationModel
{
    private val screenMutable = mutableStateOf<Screens>(Screens.MAIN_PRESENTATION)
    override val screen: State<Screens> = this.screenMutable

    override fun back(): Boolean
    {
        SoundManager.stopSounds()

        if (this.screenMutable.value == Screens.MAIN_PRESENTATION)
        {
            return true
        }

        this.screenMutable.value = Screens.MAIN_PRESENTATION
        return false
    }

    internal fun chooseScreen(screen: Screens)
    {
        if (screen != Screens.MAIN_PRESENTATION
            && this.screenMutable.value == Screens.MAIN_PRESENTATION)
        {
            this.screenMutable.value = screen
        }
    }

    override fun pause()
    {
        SoundManager.pause()
    }

    override fun resume()
    {
        if (this.screenMutable.value == Screens.SOUNDS
            || this.screenMutable.value == Screens.SOUND_3D
            || this.screenMutable.value == Screens.MINI_RPG)
        {
            SoundManager.resume()
        }
    }
}