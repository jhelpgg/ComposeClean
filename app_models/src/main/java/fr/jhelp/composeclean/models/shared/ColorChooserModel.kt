package fr.jhelp.composeclean.models.shared

import androidx.annotation.ColorInt
import androidx.compose.runtime.State

/**
 * Color chooser model
 */
interface ColorChooserModel
{
    /** Called when current color changed */
    var colorChangeListener: (Int) -> Unit

    /** Current color */
    val color: State<Int>

    /** Red color part */
    val red: State<Int>

    /** Red color part */
    val green: State<Int>

    /** Red color part */
    val blue: State<Int>

    /** Change the color */
    fun color(@ColorInt color: Int)

    /**
     * Change red part.
     * Bounds to [0, 255]]
     */
    fun red(red: Int)

    /**
     * Change green part.
     * Bounds to [0, 255]]
     */
    fun green(green: Int)

    /**
     * Change blue part.
     * Bounds to [0, 255]]
     */
    fun blue(blue: Int)
}
