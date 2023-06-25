package fr.jhelp.composeclean.models.implementation

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import fr.jhelp.compose.provider.provided
import fr.jhelp.compose.sound.SoundManager
import fr.jhelp.composeclean.models.shared.EngineInterpolationModel
import fr.jhelp.composeclean.models.shared.NavigationModel
import fr.jhelp.composeclean.models.shared.Screens
import fr.jhelp.composeclean.models.shared.dialogs.DialogMessage
import fr.jhelp.composeclean.models.shared.dialogs.DialogMessageOptionButton
import fr.jhelp.composeclean.models.shared.dialogs.DialogMessageOptions
import fr.jhelp.composeclean.models.shared.dialogs.DialogModel
import fr.jhelp.composeclean.models.shared.dialogs.NoDialog
import fr.jhelp.composeclean.models.source.text.TextResource
import fr.jhelp.composeclean.models.source.text.TextSource
import fr.jhelp.android.library.tasks.future.FutureResult
import fr.jhelp.android.library.tasks.future.Promise

internal class NavigationModelImplementation : NavigationModel
{
    private val screenMutable = mutableStateOf<Screens>(Screens.MAIN_PRESENTATION)
    private val dialogMutable = mutableStateOf<DialogModel>(NoDialog)
    private val engineInterpolationModel: EngineInterpolationModel by provided<EngineInterpolationModel>()
    private var previousScreen = Screens.MAIN_PRESENTATION
    override val screen: State<Screens> = this.screenMutable
    override val dialog: State<DialogModel> = this.dialogMutable

    override fun back(): Boolean
    {
        SoundManager.stopSounds()

        if (this.screenMutable.value == Screens.ANIMATION_INTERPOLATION_3D)
        {
            this.engineInterpolationModel.stopAnimation()
        }

        if (this.screenMutable.value == Screens.MAIN_PRESENTATION)
        {
            return true
        }

        this.screenMutable.value = this.previousScreen
        this.previousScreen = Screens.MAIN_PRESENTATION
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

    override fun chooseImage()
    {
        this.previousScreen = this.screenMutable.value
        this.screenMutable.value = Screens.IMAGE_CHOOSER
    }

    override fun chooseColor()
    {
        this.previousScreen = this.screenMutable.value
        this.screenMutable.value = Screens.COLOR_CHOOSER
    }

    override fun chooseMask()
    {
        this.previousScreen = this.screenMutable.value
        this.screenMutable.value = Screens.MASK_CHOOSER
    }

    override fun chooseBumpImage()
    {
        this.previousScreen = this.screenMutable.value
        this.screenMutable.value = Screens.BUMP_IMAGE_CHOOSER
    }

    override fun chooseEye()
    {
        this.previousScreen = this.screenMutable.value
        this.screenMutable.value = Screens.EYE_CHOOSER
    }

    override fun chooseMouth()
    {
        this.previousScreen = this.screenMutable.value
        this.screenMutable.value = Screens.MOUTH_CHOOSER
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

    override fun closeDialog()
    {
        this.dialogMutable.value = NoDialog
    }

    override fun dialogOption(title: TextSource,
                              message: TextSource,
                              options: DialogMessageOptions): FutureResult<DialogMessageOptionButton>
    {
        val promise = Promise<DialogMessageOptionButton>()
        promise.onCancel { this.closeDialog() }
        val messageButtons =
            Array<TextSource>(options.options.size) { index -> TextResource(options.options[index].string) }
        this.dialogMutable.value =
            DialogMessage(title, message, messageButtons, horizontalButtons = true) { index ->
                this.closeDialog()
                promise.result(options.options[index])
            }
        return promise.future
    }
}