package fr.jhelp.android.library.models.shared

/**
 * Screens can be show to the user
 */
enum class Screens
{
    /** Presentation of show case view */
    MAIN_PRESENTATION,

    /** Image chooser */
    IMAGE_CHOOSER,

    /** Color chooser */
    COLOR_CHOOSER,

    /** Mask chooser */
    MASK_CHOOSER,

    /** Bump image chooser */
    BUMP_IMAGE_CHOOSER,

    /** Eye chooser */
    EYE_CHOOSER,

    /** Mouth chooser */
    MOUTH_CHOOSER,

    // **********
    // *** UI ***
    // **********

    /** Contact list view */
    CONTACT_LIST,

    /** Sounds */
    SOUNDS,

    // **********
    // *** 2D ***
    // **********

    /** Contact list view */
    /** Grey image view */
    GREY_IMAGE,

    /** Tint an image view */
    TINT_IMAGE,

    /** Mask apply on image view */
    MASK_IMAGE,

    /** Shift image view */
    SHIFT_IMAGE,

    /** Change contrast image view */
    CONTRAST_IMAGE,

    /** Multiply two images view */
    MULTIPLY_IMAGE,

    /** Add two images view */
    ADD_IMAGE,

    /** Change image darkness view */
    DARKER_IMAGE,

    /** Invert image colors view */
    INVERT_COLORS_IMAGE,

    /** Bump map effect on image view */
    BUMP_MAP_IMAGE,

    /** Draw neon lines on image view */
    NEON_LINES_DRAW,

    /** Repeat pattern follow line on image view */
    REPEAT_ON_LINE_DRAW,

    // **********
    // *** 3D ***
    // **********

    /** Hello world 3D */
    HELLO_WORLD_3D,

    /** Material color 3D */
    MATERIAL_COLOR_3D,

    /** Material texture 3D */
    MATERIAL_TEXTURE_3D,

    /** Cross texture 3D */
    CROSS_TEXTURE_3D,

    /** Transparency 3D */
    TRANSPARENCY_3D,

    /** Sphere 3D */
    SPHERE_3D,

    /** Plane 3D */
    PLANE_3D,

    /** Revolution 3D */
    REVOLUTION_3D,

    /** Field 3D */
    FIELD_3D,

    /** Dice */
    DICE_3D,

    /** Robot */
    ROBOT_3D,

    /** OBJ source */
    TEDDY_BEAR_3D,

    /** Animation interpolations */
    ANIMATION_INTERPOLATION_3D,

    /** Background, foreground images */
    BACKGROUND_FOREGROUND_3D,

    /** Sound 3D */
    SOUND_3D,

    /** Particle effect : explosion */
    PARTICLE_EFFECT_EXPLOSION,

    /** Particle effect : sword slash */
    PARTICLE_EFFECT_SWORD_SLASH,

    /** Over GUI */
    OVER_GUI_3D,

    /** Virtual joystick */
    VIRTUAL_JOYSTICK,

    /** Mini RPG */
    MINI_RPG
}