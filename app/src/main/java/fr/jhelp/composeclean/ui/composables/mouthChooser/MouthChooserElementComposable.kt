package fr.jhelp.composeclean.ui.composables.mouthChooser

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.unit.dp
import fr.jhelp.android.library.constraint.theme.NEAR
import fr.jhelp.android.library.constraint.theme.START_END
import fr.jhelp.android.library.engine.resources.Mouths

/**
 * Draw an mouth element in mouth chooser
 */
@Composable
fun DrawMouthChooserElementComposable(mouth: Mouths, click: (Mouths) -> Unit)
{
    Image(bitmap = mouth.bitmap().asImageBitmap(),
          contentDescription = "Mouth ! $mouth",
          modifier = Modifier
              .padding(top = NEAR / 2,
                       bottom = NEAR / 2,
                       start = START_END / 2,
                       end = START_END / 2)
              .border(2.dp, Color.LightGray)
              .clickable { click(mouth) }
              .background(Color.LightGray)
              .size(64.dp))
}
