package fr.jhelp.android.library.showcase.ui.composables.presentation

import androidx.compose.runtime.Composable
import fr.jhelp.android.library.models.presentation.Presentation
import fr.jhelp.android.library.models.presentation.PresentationElement
import fr.jhelp.android.library.models.presentation.PresentationSeparator

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
