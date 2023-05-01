package fr.jhelp.composeclean.ui.composables.presentation

import androidx.compose.runtime.Composable
import fr.jhelp.composeclean.models.presentation.Presentation
import fr.jhelp.composeclean.models.presentation.PresentationElement
import fr.jhelp.composeclean.models.presentation.PresentationSeparator

/**
 * Draw a generic presentation in presentation list
 */
@Composable
fun DrawPresentation(presentation: Presentation, onClick: (PresentationElement) -> Unit)
{
    when (presentation)
    {
        is PresentationSeparator -> DrawPresentationSeparator(presentation)
        is PresentationElement   -> DrawPresentationElement(presentation, onClick)
    }
}
