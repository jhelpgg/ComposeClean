package fr.jhelp.composeclean.ui.composables.contact

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import fr.jhelp.composeclean.models.contact.ContactSeparator

/**
 * Draw contact element represents a separator
 */
@Composable
fun DrawContactSeparator(contactSeparator: ContactSeparator)
{
    Text(text = "--- ${contactSeparator.separator} ---",
         modifier = Modifier
             .fillMaxWidth()
             .wrapContentHeight(),
         textAlign = TextAlign.Center)
}
