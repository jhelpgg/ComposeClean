package fr.jhelp.compose.animations.bitmap

/**
 * Animation bitmap transition type
 */
enum class AnimationBitmapTransition
{
    /** Caver the first image by the second on going left side to right side */
    COVER_LEFT_TO_RIGHT,

    /** Caver the first image by the second on going right side to left side */
    COVER_RIGHT_TO_LEFT,

    /** Caver the first image by the second on going top side to bottom side */
    COVER_TOP_TO_BOTTOM,

    /** Caver the first image by the second on going bottom side to top side */
    COVER_BOTTOM_TO_TOP,

    /** First image is melt to second */
    MELT,

    /** Random pixels change from first image to second one */
    RANDOM
}