package fr.jhelp.android.library.showcase.ui.composables.maskChooser

import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.unit.dp
import fr.jhelp.android.library.constraint.theme.NEAR
import fr.jhelp.android.library.constraint.theme.START_END
import fr.jhelp.android.library.models.shared.ImageMasks

/**
 * Draw an element of mask chooser
 */
@Composable
fun DrawMaskChooserElement(image: ImageMasks, click: (ImageMasks) -> Unit)
{
    Image(bitmap = image.bitmap.asImageBitmap(),
          contentDescription = "Mask",
          modifier = Modifier
              .padding(top = NEAR / 2,
                       bottom = NEAR / 2,
                       start = START_END / 2,
                       end = START_END / 2)
              .border(2.dp, Color.LightGray)
              .clickable { click(image) })
}
