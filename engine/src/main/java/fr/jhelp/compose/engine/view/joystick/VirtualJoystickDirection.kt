package fr.jhelp.compose.engine.view.joystick

/**
 * Virtual joystick direction
 */
enum class VirtualJoystickDirection
{
    /** No direction. Returned to it when no more touch */
    NONE,

    /** Joystick point to right direction */
    RIGHT,

    /** Joystick point to down right direction */
    DOWN_RIGHT,

    /** Joystick point to down direction */
    DOWN,

    /** Joystick point to down left direction */
    DOWN_LEFT,

    /** Joystick point to left direction */
    LEFT,

    /** Joystick point to up left direction */
    UP_LEFT,

    /** Joystick point to up direction */
    UP,

    /** Joystick point to up right direction */
    UP_RIGHT
}