package fr.jhelp.composeclean.ui.composables.imageChooser

import androidx.annotation.DrawableRes
import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import fr.jhelp.android.library.constraint.theme.NEAR
import fr.jhelp.android.library.constraint.theme.START_END

/**
 * Draw an element of image chooser
 */
@Composable
fun DrawImageChooserElement(@DrawableRes image: Int, click: (Int) -> Unit)
{
    Image(painter = painterResource(id = image),
          contentDescription = "Image",
          modifier = Modifier
              .padding(top = NEAR / 2,
                       bottom = NEAR / 2,
                       start = START_END / 2,
                       end = START_END / 2)
              .size(256.dp)
              .border(2.dp, Color.LightGray)
              .clickable { click(image) })
}
