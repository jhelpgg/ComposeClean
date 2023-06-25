package fr.jhelp.composeclean.ui.composables.presentation

import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import fr.jhelp.android.library.constraint.theme.NEAR
import fr.jhelp.android.library.constraint.theme.START_END
import fr.jhelp.composeclean.extensions.resourceImage
import fr.jhelp.composeclean.extensions.resourceText
import fr.jhelp.android.library.models.presentation.PresentationElement

/**
 * Draw a presentation element in presentation list
 */
@Composable
fun DrawPresentationElement(presentationElement: PresentationElement,
                            onClick: (PresentationElement) -> Unit)
{
    val text = stringResource(id = presentationElement.resourceText)
    Row(modifier = Modifier
        .padding(top = NEAR / 2, bottom = NEAR / 2, start = START_END / 2, end = START_END / 2)
        .border(2.dp, Color.LightGray)
        .fillMaxWidth()
        .wrapContentHeight()
        .clickable { onClick(presentationElement) }) {
        Image(painter = painterResource(id = presentationElement.resourceImage),
              contentDescription = text)
        Text(text = text,
             modifier = Modifier
                 .fillMaxWidth()
                 .wrapContentHeight(),
             textAlign = TextAlign.Left)
    }
}