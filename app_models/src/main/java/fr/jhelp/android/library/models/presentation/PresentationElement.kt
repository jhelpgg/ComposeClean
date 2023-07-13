package fr.jhelp.android.library.models.presentation

import fr.jhelp.android.library.models.shared.Screens

/**
 * Presentation element
 *  @property screen Screen Associated to the type
 *  @property presentationType Presentation type
 */
enum class PresentationElement(val screen: Screens, val presentationType: PresentationType) :
        Presentation
{
    // **********
    // *** UI ***
    // **********

    /** Contact list view */
    CONTACT_LIST(Screens.CONTACT_LIST, PresentationType.UI),

    /** Sounds */
    SOUNDS(Screens.SOUNDS, PresentationType.UI),

    // **********
    // *** 2D ***
    // **********

    /** Grey image view */
    GREY_IMAGE(Screens.GREY_IMAGE, PresentationType.IMAGE_MANIPULATION),

    /** Tint an image view */
    TINT_IMAGE(Screens.TINT_IMAGE, PresentationType.IMAGE_MANIPULATION),

    /** Mask apply on image view */
    MASK_IMAGE(Screens.MASK_IMAGE, PresentationType.IMAGE_MANIPULATION),

    /** Shift image view */
    SHIFT_IMAGE(Screens.SHIFT_IMAGE, PresentationType.IMAGE_MANIPULATION),

    /** Change contrast image view */
    CONTRAST_IMAGE(Screens.CONTRAST_IMAGE, PresentationType.IMAGE_MANIPULATION),

    /** Multiply two images view */
    MULTIPLY_IMAGE(Screens.MULTIPLY_IMAGE, PresentationType.IMAGE_MANIPULATION),

    /** Add two images view */
    ADD_IMAGE(Screens.ADD_IMAGE, PresentationType.IMAGE_MANIPULATION),

    /** Change image darkness view */
    DARKER_IMAGE(Screens.DARKER_IMAGE, PresentationType.IMAGE_MANIPULATION),

    /** Invert image colors view */
    INVERT_COLORS_IMAGE(Screens.INVERT_COLORS_IMAGE, PresentationType.IMAGE_MANIPULATION),

    /** Bump map effect on image view */
    BUMP_MAP_IMAGE(Screens.BUMP_MAP_IMAGE, PresentationType.IMAGE_MANIPULATION),

    /** Draw neon lines on image view */
    NEON_LINES_DRAW(Screens.NEON_LINES_DRAW, PresentationType.IMAGE_MANIPULATION),

    /** Repeat pattern follow line on image view */
    REPEAT_ON_LINE_DRAW(Screens.REPEAT_ON_LINE_DRAW, PresentationType.IMAGE_MANIPULATION),

    // **********
    // *** 3D ***
    // **********

    /** Hello world 3D */
    HELLO_WORLD_3D(Screens.HELLO_WORLD_3D, PresentationType.ENGINE_3D),

    /** Material color 3D */
    MATERIAL_COLOR_3D(Screens.MATERIAL_COLOR_3D, PresentationType.ENGINE_3D),

    /** Material texture 3D */
    MATERIAL_TEXTURE_3D(Screens.MATERIAL_TEXTURE_3D, PresentationType.ENGINE_3D),

    /** Cross texture 3D */
    CROSS_TEXTURE_3D(Screens.CROSS_TEXTURE_3D, PresentationType.ENGINE_3D),

    /** Transparency 3D */
    TRANSPARENCY_3D(Screens.TRANSPARENCY_3D, PresentationType.ENGINE_3D),

    /** Sphere 3D */
    SPHERE_3D(Screens.SPHERE_3D, PresentationType.ENGINE_3D),

    /** Plane 3D */
    PLANE_3D(Screens.PLANE_3D, PresentationType.ENGINE_3D),

    /** Revolution 3D */
    REVOLUTION_3D(Screens.REVOLUTION_3D, PresentationType.ENGINE_3D),

    /** Field 3D */
    FIELD_3D(Screens.FIELD_3D, PresentationType.ENGINE_3D),

    /** Wire frame 3D */
    WIRE_FRAME_3D(Screens.WIRE_FRAME_3D, PresentationType.ENGINE_3D),

    /** Dice */
    DICE_3D(Screens.DICE_3D, PresentationType.ENGINE_3D),

    /** Robot */
    ROBOT_3D(Screens.ROBOT_3D, PresentationType.ENGINE_3D),

    /** OBJ source */
    TEDDY_BEAR_3D(Screens.TEDDY_BEAR_3D, PresentationType.ENGINE_3D),

    /** Animation interpolations */
    ANIMATION_INTERPOLATION_3D(Screens.ANIMATION_INTERPOLATION_3D, PresentationType.ENGINE_3D),

    /** Background, foreground images */
    BACKGROUND_FOREGROUND_3D(Screens.BACKGROUND_FOREGROUND_3D, PresentationType.ENGINE_3D),

    /** Sound 3D */
    SOUND_3D(Screens.SOUND_3D, PresentationType.ENGINE_3D),

    /** Particle effect : explosion */
    PARTICLE_EFFECT_EXPLOSION(Screens.PARTICLE_EFFECT_EXPLOSION, PresentationType.ENGINE_3D),

    /** Particle effect : sword slash */
    PARTICLE_EFFECT_SWORD_SLASH(Screens.PARTICLE_EFFECT_SWORD_SLASH, PresentationType.ENGINE_3D),

    /** Over GUI */
    OVER_GUI_3D(Screens.OVER_GUI_3D, PresentationType.ENGINE_3D),

    /** Virtual joystick */
    VIRTUAL_JOYSTICK(Screens.VIRTUAL_JOYSTICK, PresentationType.ENGINE_3D),

    /** Solar system */
    SOLAR_SYSTEM(Screens.SOLAR_SYSTEM, PresentationType.ENGINE_3D),

    /** Mini RPG */
    MINI_RPG(Screens.MINI_RPG, PresentationType.ENGINE_3D)
}
