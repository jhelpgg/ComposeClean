package fr.jhelp.composeclean.models.shared.preview

import fr.jhelp.compose.mutable.Mutable
import fr.jhelp.composeclean.models.shared.MainModel
import fr.jhelp.composeclean.models.TextChoice

/**
 * Main activity model used for preview
 */
object MainModelPreview : MainModel
{
    override fun initialize(textChoice: Mutable<TextChoice>) = Unit

    override fun changeText() = Unit
}
