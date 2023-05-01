package fr.jhelp.composeclean.ui.composables.presentation

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import fr.jhelp.composeclean.models.presentation.PresentationElement

/**
 * Draw a presentation element in presentation list
 */
@Composable
fun DrawPresentationElement(presentationElement: PresentationElement,
                            onClick: (PresentationElement) -> Unit)
{
    Text(text = "> ${presentationElement.screen} : ${presentationElement.presentationType}",
         modifier = Modifier
             .fillMaxWidth()
             .wrapContentHeight()
             .clickable { onClick(presentationElement) },
         textAlign = TextAlign.Left)
}