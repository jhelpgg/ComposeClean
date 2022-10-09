package fr.jhelp.composeclean.models.implementation

import fr.jhelp.compose.mutable.Mutable
import fr.jhelp.composeclean.models.TextChoice
import fr.jhelp.composeclean.models.shared.MainModel

/**
 * Main activity model implementation
 */
internal class MainModelImplementation : MainModel
{
    /** Holder for get/change the text choice */
    private lateinit var textChoice: Mutable<TextChoice>

    /**
     * Initialize the model
     * @param textChoice Holder for get/change the text choice
     */
    override fun initialize(textChoice: Mutable<TextChoice>)
    {
        this.textChoice = textChoice
    }

    /**
     * Change the text action
     */
    override fun changeText()
    {
        // Use in this order work because we get before we set
        // See issue described in contact list model implementation for the other order
        this.textChoice.set(
            when (this.textChoice.get())
            {
                TextChoice.CHOICE1 -> TextChoice.CHOICE2
                TextChoice.CHOICE2 -> TextChoice.CHOICE1
            }
                           )
    }
}