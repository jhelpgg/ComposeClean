package fr.jhelp.android.library.showcase.game.models

/**
 * Castle's bridge state
 */
enum class CastleBridgeState
{
    /** State when bridge is closed **/
    CLOSED,
    /** State when bridge is opening **/
    OPENING,
    /** State when bridge is opened **/
    OPENED,
    /** State when bridge is closing **/
    CLOSING
}