package fr.jhelp.composeclean.models.shared

import fr.jhelp.compose.mutable.Mutable
import fr.jhelp.composeclean.models.TextChoice

/**
 * Main activity model
 */
interface MainModel
{
    /**
     * Initialize the model
     * @param textChoice Holder for get/change the text choice
     */
    fun initialize(textChoice: Mutable<TextChoice>)

    /**
     * Change the text action
     */
    fun changeText()
}