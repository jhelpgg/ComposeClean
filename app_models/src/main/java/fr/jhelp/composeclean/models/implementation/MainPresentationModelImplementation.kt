package fr.jhelp.composeclean.models.implementation

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import fr.jhelp.composeclean.models.TextChoice
import fr.jhelp.composeclean.models.shared.MainPresentationModel

/**
 * Main activity model implementation
 */
internal class MainPresentationModelImplementation : MainPresentationModel
{
    /** Holder for get/change the text choice */
    private val textChoiceMutableState: MutableState<TextChoice> =
        mutableStateOf(TextChoice.CHOICE1)
    override val textChoiceState: State<TextChoice> get() = this.textChoiceMutableState

    /**
     * Change the text action
     */
    override fun changeText()
    {
        // Use in this order work because we get before we set
        // See issue described in contact list model implementation for the other order
        this.textChoiceMutableState.value =
            when (this.textChoiceMutableState.value)
            {
                TextChoice.CHOICE1 -> TextChoice.CHOICE2
                TextChoice.CHOICE2 -> TextChoice.CHOICE1
            }
    }
}