package fr.jhelp.composeclean.ui.composables

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import fr.jhelp.compose.provider.provided
import fr.jhelp.composeclean.models.shared.NavigationModel
import fr.jhelp.composeclean.models.shared.Screens
import fr.jhelp.composeclean.ui.ImageInvertColorsComposable
import fr.jhelp.composeclean.ui.composables.bumpChooser.BumpImageChooserComposable
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
    private val bumpImageChooser: BumpImageChooserComposable by lazy { BumpImageChooserComposable() }
    private val contactList: ContactListComposable by lazy { ContactListComposable() }
    private val soundManager: SoundManagerExampleComposable by lazy { SoundManagerExampleComposable() }
    private val imageGrey: ImageGreyComposable by lazy { ImageGreyComposable() }
    private val imageTint: ImageTintComposable by lazy { ImageTintComposable() }
    private val imageMask: ImageMaskComposable by lazy { ImageMaskComposable() }
    private val imageShift: ImageShiftComposable by lazy { ImageShiftComposable() }
    private val imageContrast: ImageContrastComposable by lazy { ImageContrastComposable() }
    private val imageMultiply: ImageMultiplyComposable by lazy { ImageMultiplyComposable() }
    private val imageAdd: ImageAddComposable by lazy { ImageAddComposable() }
    private val imageDarker: ImageDarkerComposable by lazy { ImageDarkerComposable() }
    private val imageInvertColors: ImageInvertColorsComposable by lazy { ImageInvertColorsComposable() }
    private val imageBumpMap: ImageBumpMapComposable by lazy { ImageBumpMapComposable() }

    private val engineInterpolation: EngineInterpolationComposable by lazy { EngineInterpolationComposable() }

    /**
     * Show the main activity
     */
    @Composable
    fun Show()
    {
        val screen: Screens = this.navigationModel.screen.value

        when (screen)
        {
            Screens.MAIN_PRESENTATION          -> this.mainPresentation.Show()
            Screens.IMAGE_CHOOSER              -> this.imageChooser.Show()
            Screens.COLOR_CHOOSER              -> this.colorChooser.Show()
            Screens.MASK_CHOOSER               -> this.maskChooser.Show()
            Screens.BUMP_IMAGE_CHOOSER         -> this.bumpImageChooser.Show()
            Screens.CONTACT_LIST               -> this.contactList.Show()
            Screens.SOUNDS                     -> this.soundManager.Show()
            Screens.GREY_IMAGE                 -> this.imageGrey.Show()
            Screens.TINT_IMAGE                 -> this.imageTint.Show()
            Screens.MASK_IMAGE                 -> this.imageMask.Show()
            Screens.SHIFT_IMAGE                -> this.imageShift.Show()
            Screens.CONTRAST_IMAGE             -> this.imageContrast.Show()
            Screens.MULTIPLY_IMAGE             -> this.imageMultiply.Show()
            Screens.ADD_IMAGE                  -> this.imageAdd.Show()
            Screens.DARKER_IMAGE               -> this.imageDarker.Show()
            Screens.INVERT_COLORS_IMAGE        -> this.imageInvertColors.Show()
            Screens.BUMP_MAP_IMAGE             -> this.imageBumpMap.Show()

            Screens.ANIMATION_INTERPOLATION_3D -> this.engineInterpolation.Show()

            else                               -> Text(text = "Not implemented screen : $screen")
        }
    }
}