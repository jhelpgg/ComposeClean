package fr.jhelp.android.library.showcase.ui.composables

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.ColorPainter
import fr.jhelp.android.library.models.shared.NavigationModel
import fr.jhelp.android.library.models.shared.Screens
import fr.jhelp.android.library.models.shared.dialogs.DialogMessage
import fr.jhelp.android.library.models.shared.dialogs.NoDialog
import fr.jhelp.android.library.provider.provided
import fr.jhelp.android.library.showcase.ui.composables.bumpChooser.BumpImageChooserComposable
import fr.jhelp.android.library.showcase.ui.composables.colorChooser.ColorChooserComposable
import fr.jhelp.android.library.showcase.ui.composables.eyeChooser.EyeChooserComposable
import fr.jhelp.android.library.showcase.ui.composables.imageChooser.ImageChooserComposable
import fr.jhelp.android.library.showcase.ui.composables.maskChooser.MaskChooserComposable
import fr.jhelp.android.library.showcase.ui.composables.mouthChooser.MouthChooserComposable
import fr.jhelp.android.library.showcase.ui.extensions.dialog

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
    private val eyeChooser: EyeChooserComposable by lazy { EyeChooserComposable() }
    private val mouthChooser: MouthChooserComposable by lazy { MouthChooserComposable() }
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
    private val imageNeonLines: ImageNeonLinesComposable by lazy { ImageNeonLinesComposable() }
    private val imageRepeatOnLine: ImageRepeatOnLineComposable by lazy { ImageRepeatOnLineComposable() }
    private val engineHelloWorld: EngineHelloWorldComposable by lazy { EngineHelloWorldComposable() }
    private val engineMaterialColor: EngineMaterialColorComposable by lazy { EngineMaterialColorComposable() }
    private val engineMaterialTexture: EngineMaterialTextureComposable by lazy { EngineMaterialTextureComposable() }
    private val engineCrossTexture: EngineTextureCrossComposable by lazy { EngineTextureCrossComposable() }
    private val engineTransparency: EngineTransparencyComposable by lazy { EngineTransparencyComposable() }
    private val engineSphere: EngineSphereComposable by lazy { EngineSphereComposable() }
    private val enginePlane: EnginePlaneComposable by lazy { EnginePlaneComposable() }
    private val engineRevolution: EngineRevolutionComposable by lazy { EngineRevolutionComposable() }
    private val engineField: EngineFieldComposable by lazy { EngineFieldComposable() }
    private val engineWireFrame: EngineShowWireComposable by lazy { EngineShowWireComposable() }
    private val engineDice: EngineDiceComposable by lazy { EngineDiceComposable() }
    private val engineRobot: EngineRobotComposable by lazy { EngineRobotComposable() }
    private val engineLoadObj: EngineLoadObjComposable by lazy { EngineLoadObjComposable() }
    private val engineInterpolation: EngineInterpolationComposable by lazy { EngineInterpolationComposable() }
    private val engineBackgroundForeground: EngineBackgroundForegroundComposable by lazy { EngineBackgroundForegroundComposable() }
    private val engineSound3D: EngineSound3DComposable by lazy { EngineSound3DComposable() }
    private val engineExplosion: EngineParticleExplodeComposable by lazy { EngineParticleExplodeComposable() }
    private val engineSword: EngineParticleSwordComposable by lazy { EngineParticleSwordComposable() }
    private val engineFirework: EngineParticleFireworkComposable by lazy { EngineParticleFireworkComposable() }
    private val engineGUI: EngineGUIComposable by lazy { EngineGUIComposable() }
    private val engineVirtualJoystick: EngineVirtualJoystickComposable by lazy { EngineVirtualJoystickComposable() }
    private val solarSystem: EngineSolarSystemComposable by lazy { EngineSolarSystemComposable() }
    private val morphingBoxSphere: EngineMorphingBoxSphereComposable by lazy { EngineMorphingBoxSphereComposable() }
    private val morphingSphereToyComposable: EngineMorphingSphereToyComposable by lazy { EngineMorphingSphereToyComposable() }
    private val morphingToysComposable: EngineMorphingToysComposable by lazy { EngineMorphingToysComposable() }
    private val game: GameComposable by lazy { GameComposable() }

    // Dialogs

    private val dialogMessage: DialogMessageComposable by lazy { DialogMessageComposable() }

    /**
     * Show the main activity
     */
    @Composable
    fun Show()
    {
        val screen: Screens = this.navigationModel.screen.value

        when (screen)
        {
            Screens.MAIN_PRESENTATION           -> this.mainPresentation.Show()
            Screens.IMAGE_CHOOSER               -> this.imageChooser.Show()
            Screens.COLOR_CHOOSER               -> this.colorChooser.Show()
            Screens.MASK_CHOOSER                -> this.maskChooser.Show()
            Screens.BUMP_IMAGE_CHOOSER          -> this.bumpImageChooser.Show()
            Screens.EYE_CHOOSER                 -> this.eyeChooser.Show()
            Screens.MOUTH_CHOOSER               -> this.mouthChooser.Show()
            Screens.CONTACT_LIST                -> this.contactList.Show()
            Screens.SOUNDS                      -> this.soundManager.Show()
            Screens.GREY_IMAGE                  -> this.imageGrey.Show()
            Screens.TINT_IMAGE                  -> this.imageTint.Show()
            Screens.MASK_IMAGE                  -> this.imageMask.Show()
            Screens.SHIFT_IMAGE                 -> this.imageShift.Show()
            Screens.CONTRAST_IMAGE              -> this.imageContrast.Show()
            Screens.MULTIPLY_IMAGE              -> this.imageMultiply.Show()
            Screens.ADD_IMAGE                   -> this.imageAdd.Show()
            Screens.DARKER_IMAGE                -> this.imageDarker.Show()
            Screens.INVERT_COLORS_IMAGE         -> this.imageInvertColors.Show()
            Screens.BUMP_MAP_IMAGE              -> this.imageBumpMap.Show()
            Screens.NEON_LINES_DRAW             -> this.imageNeonLines.Show()
            Screens.REPEAT_ON_LINE_DRAW         -> this.imageRepeatOnLine.Show()
            Screens.HELLO_WORLD_3D              -> this.engineHelloWorld.Show()
            Screens.MATERIAL_COLOR_3D           -> this.engineMaterialColor.Show()
            Screens.MATERIAL_TEXTURE_3D         -> this.engineMaterialTexture.Show()
            Screens.CROSS_TEXTURE_3D            -> this.engineCrossTexture.Show()
            Screens.TRANSPARENCY_3D             -> this.engineTransparency.Show()
            Screens.SPHERE_3D                   -> this.engineSphere.Show()
            Screens.PLANE_3D                    -> this.enginePlane.Show()
            Screens.REVOLUTION_3D               -> this.engineRevolution.Show()
            Screens.FIELD_3D                    -> this.engineField.Show()
            Screens.WIRE_FRAME_3D               -> this.engineWireFrame.Show()
            Screens.DICE_3D                     -> this.engineDice.Show()
            Screens.ROBOT_3D                    -> this.engineRobot.Show()
            Screens.TEDDY_BEAR_3D               -> this.engineLoadObj.Show()
            Screens.ANIMATION_INTERPOLATION_3D  -> this.engineInterpolation.Show()
            Screens.BACKGROUND_FOREGROUND_3D    -> this.engineBackgroundForeground.Show()
            Screens.SOUND_3D                    -> this.engineSound3D.Show()
            Screens.PARTICLE_EFFECT_EXPLOSION   -> this.engineExplosion.Show()
            Screens.PARTICLE_EFFECT_SWORD_SLASH -> this.engineSword.Show()
            Screens.PARTICLE_EFFECT_FIREWORK    -> this.engineFirework.Show()
            Screens.OVER_GUI_3D                 -> this.engineGUI.Show()
            Screens.VIRTUAL_JOYSTICK            -> this.engineVirtualJoystick.Show()
            Screens.MINI_RPG                    -> this.game.Show()
            Screens.SOLAR_SYSTEM                -> this.solarSystem.Show()
            Screens.MORPHING_BOX_SPHERE         -> this.morphingBoxSphere.Show()
            Screens.MORPHING_SPHERE_TOY         -> this.morphingSphereToyComposable.Show()
            Screens.MORPHING_TOYS               -> this.morphingToysComposable.Show()
            else                                -> Text(text = "Not implemented screen : $screen")
        }

        val dialog = this.navigationModel.dialog.value

        if (dialog == NoDialog)
        {
            return
        }

        Image(painter = ColorPainter(Color(64, 64, 64, 192)),
              contentDescription = "",
              modifier = Modifier
                  .fillMaxSize()
                  .clickable(enabled = dialog.closeWhenClickOutside) { this.navigationModel.closeDialog() })

        when (dialog)
        {
            is DialogMessage -> this.dialogMessage.Show(dialogMessage = dialog,
                                                        modifier = Modifier.dialog(align = dialog.alignment))

            else             -> Unit
        }
    }
}