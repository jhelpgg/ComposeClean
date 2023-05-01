package fr.jhelp.composeclean.models.shared

import androidx.compose.runtime.State
import fr.jhelp.composeclean.models.TextChoice

/**
 * Main activity model
 */
interface MainPresentationModel
{
    /** Observable of current text choice */
    val textChoiceState: State<TextChoice>

    /**
     * Change the text action
     */
    fun changeText()
}