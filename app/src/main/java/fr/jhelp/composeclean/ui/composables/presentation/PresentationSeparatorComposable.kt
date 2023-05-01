package fr.jhelp.composeclean.ui.composables.presentation

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import fr.jhelp.composeclean.models.presentation.PresentationSeparator

/**
 * Draw a presentation separator in presentation list
 */
@Composable
fun DrawPresentationSeparator(presentationSeparator: PresentationSeparator)
{
    Text(text = "--- ${presentationSeparator.presentationType} ---",
         modifier = Modifier
             .fillMaxWidth()
             .wrapContentHeight(),
         textAlign = TextAlign.Center)
}