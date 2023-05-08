package fr.jhelp.composeclean.models.shared

import androidx.compose.runtime.State

/**
 * Color chooser model
 */
interface ColorChooserModel
{
    /** Current color */
    val color: State<Int>

    /** Red color part */
    val red: State<Int>

    /** Red color part */
    val green: State<Int>

    /** Red color part */
    val blue: State<Int>

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
