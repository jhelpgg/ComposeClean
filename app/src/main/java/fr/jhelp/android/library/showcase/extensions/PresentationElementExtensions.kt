package fr.jhelp.android.library.showcase.extensions

import fr.jhelp.android.library.models.presentation.PresentationElement
import fr.jhelp.android.library.showcase.R

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
            PresentationElement.CONTACT_LIST                -> R.drawable.ui_contact
            PresentationElement.SOUNDS                      -> R.drawable.ui_sound
            PresentationElement.GREY_IMAGE                  -> R.drawable.image_grey
            PresentationElement.TINT_IMAGE                  -> R.drawable.image_tint
            PresentationElement.MASK_IMAGE                  -> R.drawable.image_mask
            PresentationElement.SHIFT_IMAGE                 -> R.drawable.image_shift
            PresentationElement.CONTRAST_IMAGE              -> R.drawable.image_contrast
            PresentationElement.MULTIPLY_IMAGE              -> R.drawable.image_multiply
            PresentationElement.ADD_IMAGE                   -> R.drawable.image_add
            PresentationElement.DARKER_IMAGE                -> R.drawable.image_darker
            PresentationElement.INVERT_COLORS_IMAGE         -> R.drawable.image_invert_colors
            PresentationElement.BUMP_MAP_IMAGE              -> R.drawable.image_bump_map
            PresentationElement.NEON_LINES_DRAW             -> R.drawable.image_neon_lines
            PresentationElement.REPEAT_ON_LINE_DRAW         -> R.drawable.image_repeat_on_line
            PresentationElement.HELLO_WORLD_3D              -> R.drawable.engine_hello_world
            PresentationElement.MATERIAL_COLOR_3D           -> R.drawable.engine_material_color
            PresentationElement.MATERIAL_TEXTURE_3D         -> R.drawable.engine_material_texture
            PresentationElement.CROSS_TEXTURE_3D            -> R.drawable.engine_cross_texture
            PresentationElement.TRANSPARENCY_3D             -> R.drawable.engine_transparency
            PresentationElement.SPHERE_3D                   -> R.drawable.engine_sphere
            PresentationElement.PLANE_3D                    -> R.drawable.engine_plane
            PresentationElement.REVOLUTION_3D               -> R.drawable.engine_revolution
            PresentationElement.FIELD_3D                    -> R.drawable.engine_field
            PresentationElement.DICE_3D                     -> R.drawable.engine_dice
            PresentationElement.ROBOT_3D                    -> R.drawable.engine_robot
            PresentationElement.TEDDY_BEAR_3D               -> R.drawable.engine_load_obj
            PresentationElement.ANIMATION_INTERPOLATION_3D  -> R.drawable.engine_interpolation
            PresentationElement.BACKGROUND_FOREGROUND_3D    -> R.drawable.engine_background_foreground
            PresentationElement.SOUND_3D                    -> R.drawable.engine_sound_3d
            PresentationElement.PARTICLE_EFFECT_EXPLOSION   -> R.drawable.engine_explode
            PresentationElement.PARTICLE_EFFECT_SWORD_SLASH -> R.drawable.engine_sword
            PresentationElement.OVER_GUI_3D                 -> R.drawable.engine_gui
            PresentationElement.VIRTUAL_JOYSTICK            -> R.drawable.engine_virtual_joystick

            else                                            -> android.R.drawable.ic_delete
        }