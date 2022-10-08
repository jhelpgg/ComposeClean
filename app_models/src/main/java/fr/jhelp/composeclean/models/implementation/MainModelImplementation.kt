package fr.jhelp.composeclean.models.implementation

import fr.jhelp.compose.mutable.Mutable
import fr.jhelp.composeclean.models.shared.MainModel
import fr.jhelp.composeclean.models.TextChoice

internal class MainModelImplementation : MainModel
{
    private lateinit var textChoice: Mutable<TextChoice>

    override fun initialize(textChoice: Mutable<TextChoice>)
    {
        this.textChoice = textChoice
    }

    override fun changeText()
    {
        this.textChoice.set(
            when (this.textChoice.get())
            {
                TextChoice.CHOICE1 -> TextChoice.CHOICE2
                TextChoice.CHOICE2 -> TextChoice.CHOICE1
            }
                           )
    }
}