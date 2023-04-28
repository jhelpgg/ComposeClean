package fr.jhelp.compose.engine.view.joystick

import android.graphics.Bitmap
import androidx.annotation.DrawableRes
import fr.jhelp.compose.engine.R
import fr.jhelp.compose.engine.resources.ResourcesAccess

enum class VirtualJoystickType(@DrawableRes val base: Int, @DrawableRes val button: Int)
{
    BLACK(R.drawable.joystick_blcak_base, R.drawable.joystick_black_button),
    BLUE(R.drawable.joystick_blue_base, R.drawable.joystick_blue_button),
    GREEN(R.drawable.joystick_green_base, R.drawable.joystick_green_button),
    PURPLE(R.drawable.joystick_purple_base, R.drawable.joystick_purple_button),
    RED(R.drawable.joystick_red_base, R.drawable.joystick_red_button),
    WHITE(R.drawable.joystick_white_base, R.drawable.joystick_white_button),
    YELLOW(R.drawable.joystick_yellow_base, R.drawable.joystick_yellow_button)
    ;

    val baseImage: Bitmap by lazy {
        ResourcesAccess.obtainBitmap(this.base,
                                     VirtualJoystick.BASE_SIZE,
                                     VirtualJoystick.BASE_SIZE)
    }

    val buttonImage: Bitmap by lazy {
        ResourcesAccess.obtainBitmap(this.button,
                                     VirtualJoystick.BUTTON_SIZE,
                                     VirtualJoystick.BUTTON_SIZE)
    }
}