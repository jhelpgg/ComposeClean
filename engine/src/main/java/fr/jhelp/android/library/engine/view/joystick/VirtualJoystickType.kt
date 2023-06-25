package fr.jhelp.android.library.engine.view.joystick

import android.graphics.Bitmap
import androidx.annotation.DrawableRes
import fr.jhelp.android.library.engine.R
import fr.jhelp.android.library.engine.resources.ResourcesAccess

/**
 * Joystick type/color
 */
enum class VirtualJoystickType(@DrawableRes private val base: Int,
                               @DrawableRes private val button: Int)
{
    /** Black joystick */
    BLACK(R.drawable.joystick_blcak_base, R.drawable.joystick_black_button),

    /** Blue joystick */
    BLUE(R.drawable.joystick_blue_base, R.drawable.joystick_blue_button),

    /** Green joystick */
    GREEN(R.drawable.joystick_green_base, R.drawable.joystick_green_button),

    /** Purple joystick */
    PURPLE(R.drawable.joystick_purple_base, R.drawable.joystick_purple_button),

    /** Red joystick */
    RED(R.drawable.joystick_red_base, R.drawable.joystick_red_button),

    /** White joystick */
    WHITE(R.drawable.joystick_white_base, R.drawable.joystick_white_button),

    /** Yellow joystick */
    YELLOW(R.drawable.joystick_yellow_base, R.drawable.joystick_yellow_button)
    ;

    /** Joystick base image */
    val baseImage: Bitmap by lazy {
        ResourcesAccess.obtainBitmap(this.base,
                                     VirtualJoystick.BASE_SIZE,
                                     VirtualJoystick.BASE_SIZE)
    }

    /** Joystick button image */
    val buttonImage: Bitmap by lazy {
        ResourcesAccess.obtainBitmap(this.button,
                                     VirtualJoystick.BUTTON_SIZE,
                                     VirtualJoystick.BUTTON_SIZE)
    }
}