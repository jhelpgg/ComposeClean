package fr.jhelp.composeclean.ui.composables.contact

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import fr.jhelp.composeclean.models.contact.ContactSeparator
import fr.jhelp.composeclean.ui.theme.ComposeCleanTheme

@Composable
fun DrawContactSeparator(contactSeparator: ContactSeparator)
{
    Text(text = "--- ${contactSeparator.separator} ---",
         modifier = Modifier
             .fillMaxWidth()
             .wrapContentHeight(),
         textAlign = TextAlign.Center)
}

@Preview(showBackground = true)
@Composable
fun DrawContactSeparatorPreview()
{
    ComposeCleanTheme {
        DrawContactSeparator(ContactSeparator("G"))
    }
}
