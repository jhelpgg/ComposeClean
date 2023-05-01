package fr.jhelp.composeclean.ui.resources

import androidx.annotation.RawRes
import androidx.annotation.StringRes
import fr.jhelp.composeclean.R

/**
 * Available sounds
 * @property soundResource Sound raw resource
 * @property textResource Description string resource
 */
enum class Sounds(@RawRes val soundResource: Int, @StringRes val textResource: Int)
{
    /** First background sound */
    BACKGROUND_1(R.raw.background1, R.string.soundBackground1),

    /** Second background sound */
    BACKGROUND_2(R.raw.background2, R.string.soundBackground2),

    /** Third background sound */
    BACKGROUND_3(R.raw.background3, R.string.soundBackground3),

    /** First effect sound */
    EFFECT_1(R.raw.effect1, R.string.soundEffect1),

    /** Second effect sound */
    EFFECT_2(R.raw.effect2, R.string.soundEffect2),

    /** Third effect sound */
    EFFECT_3(R.raw.effect3, R.string.soundEffect3),

    /** First controlled sound */
    CONTROLLED_1(R.raw.controlled1, R.string.soundControlled1),

    /** Second controlled sound */
    CONTROLLED_2(R.raw.controlled2, R.string.soundControlled2)
}
