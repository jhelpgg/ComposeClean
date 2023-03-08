package fr.jhelp.composeclean.models.shared

import androidx.compose.runtime.State
import fr.jhelp.composeclean.models.TextChoice

/**
 * Main activity model
 */
interface MainModel
{
    val textChoiceState: State<TextChoice>

    /**
     * Change the text action
     */
    fun changeText()
}