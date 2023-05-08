package fr.jhelp.composeclean.ui.composables

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import fr.jhelp.compose.provider.provided
import fr.jhelp.composeclean.models.shared.NavigationModel
import fr.jhelp.composeclean.models.shared.Screens
import fr.jhelp.composeclean.ui.composables.colorChooser.ColorChooserComposable
import fr.jhelp.composeclean.ui.composables.imageChooser.ImageChooserComposable
import fr.jhelp.composeclean.ui.composables.maskChooser.MaskChooserComposable

/**
 * Main activity that show the current screen
 */
class MainActivityComposable
{
    private val navigationModel: NavigationModel by provided<NavigationModel>()
    private val mainPresentation: MainPresentationComposable by lazy { MainPresentationComposable() }
    private val imageChooser: ImageChooserComposable by lazy { ImageChooserComposable() }
    private val colorChooser: ColorChooserComposable by lazy { ColorChooserComposable() }
    private val maskChooser: MaskChooserComposable by lazy { MaskChooserComposable() }
    private val contactList: ContactListComposable by lazy { ContactListComposable() }
    private val soundManager: SoundManagerExampleComposable by lazy { SoundManagerExampleComposable() }
    private val imageGrey: ImageGreyComposable by lazy { ImageGreyComposable() }
    private val imageTint: ImageTintComposable by lazy { ImageTintComposable() }
    private val imageMask: ImageMaskComposable by lazy { ImageMaskComposable() }
    private val imageShift: ImageShiftComposable by lazy { ImageShiftComposable() }

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
            Screens.IMAGE_CHOOSER     -> this.imageChooser.Show()
            Screens.COLOR_CHOOSER     -> this.colorChooser.Show()
            Screens.MASK_CHOOSER      -> this.maskChooser.Show()
            Screens.CONTACT_LIST      -> this.contactList.Show()
            Screens.SOUNDS            -> this.soundManager.Show()
            Screens.GREY_IMAGE        -> this.imageGrey.Show()
            Screens.TINT_IMAGE        -> this.imageTint.Show()
            Screens.MASK_IMAGE        -> this.imageMask.Show()
            Screens.SHIFT_IMAGE       -> this.imageShift.Show()
            else                      -> Text(text = "Not implemented screen : $screen")
        }
    }
}