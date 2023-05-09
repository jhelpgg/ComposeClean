package fr.jhelp.composeclean.extensions

import fr.jhelp.composeclean.R
import fr.jhelp.composeclean.models.presentation.PresentationElement

/**
 * PresentationElement resource text
 */
val PresentationElement.resourceText: Int
    get() =
        when (this)
        {
            PresentationElement.CONTACT_LIST                -> R.string.contactList
            PresentationElement.SOUNDS                      -> R.string.soundManager
            PresentationElement.GREY_IMAGE                  -> R.string.greyImage
            PresentationElement.TINT_IMAGE                  -> R.string.tintImage
            PresentationElement.MASK_IMAGE                  -> R.string.maskImage
            PresentationElement.SHIFT_IMAGE                 -> R.string.shiftImage
            PresentationElement.CONTRAST_IMAGE              -> R.string.contrastImage
            PresentationElement.MULTIPLY_IMAGE              -> R.string.multiplyImage
            PresentationElement.ADD_IMAGE                   -> R.string.addImage
            PresentationElement.DARKER_IMAGE                -> R.string.darkerImage
            PresentationElement.INVERT_COLORS_IMAGE         -> R.string.invertColorImage
            PresentationElement.BUMP_MAP_IMAGE              -> R.string.bumpImage
            PresentationElement.NEON_LINES_DRAW             -> R.string.neonLinesDraw
            PresentationElement.REPEAT_ON_LINE_DRAW         -> R.string.repeatOnLineDraw
            PresentationElement.HELLO_WORLD_3D              -> R.string.helloWord3D
            PresentationElement.MATERIAL_COLOR_3D           -> R.string.material3D
            PresentationElement.MATERIAL_TEXTURE_3D         -> R.string.material3D
            PresentationElement.CROSS_TEXTURE_3D            -> R.string.crossTexture
            PresentationElement.TRANSPARENCY_3D             -> R.string.transparency
            PresentationElement.SPHERE_3D                   -> R.string.sphere
            PresentationElement.PLANE_3D                    -> R.string.plane
            PresentationElement.REVOLUTION_3D               -> R.string.revolution
            PresentationElement.FIELD_3D                    -> R.string.field
            PresentationElement.DICE_3D                     -> R.string.dice
            PresentationElement.ROBOT_3D                    -> R.string.robot
            PresentationElement.TEDDY_BEAR_3D               -> R.string.teddyBear
            PresentationElement.ANIMATION_INTERPOLATION_3D  -> R.string.animationAndInterpolation
            PresentationElement.BACKGROUND_FOREGROUND_3D    -> R.string.backgroundForegroundIn3D
            PresentationElement.SOUND_3D                    -> R.string.sound3D
            PresentationElement.PARTICLE_EFFECT_EXPLOSION   -> R.string.particleEffectExplosion
            PresentationElement.PARTICLE_EFFECT_SWORD_SLASH -> R.string.particleEffectSwordSlash
            PresentationElement.OVER_GUI_3D                 -> R.string.overGUI
            PresentationElement.VIRTUAL_JOYSTICK            -> R.string.virtualJoystick
            PresentationElement.MINI_RPG                    -> R.string.miniRPG
        }

/**
 * PresentationElement resource image
 */
val PresentationElement.resourceImage: Int
    get() =
        when (this)
        {
            PresentationElement.CONTACT_LIST               -> R.drawable.ui_contact
            PresentationElement.SOUNDS                     -> R.drawable.ui_sound
            PresentationElement.GREY_IMAGE                 -> R.drawable.image_grey
            PresentationElement.TINT_IMAGE                 -> R.drawable.image_tint
            PresentationElement.MASK_IMAGE                 -> R.drawable.image_mask
            PresentationElement.SHIFT_IMAGE                -> R.drawable.image_shift
            PresentationElement.CONTRAST_IMAGE             -> R.drawable.image_contrast
            PresentationElement.MULTIPLY_IMAGE             -> R.drawable.image_multiply
            PresentationElement.ADD_IMAGE                  -> R.drawable.image_add

            PresentationElement.ANIMATION_INTERPOLATION_3D -> R.drawable.engine_interpolation

            else                                           -> android.R.drawable.ic_delete
        }