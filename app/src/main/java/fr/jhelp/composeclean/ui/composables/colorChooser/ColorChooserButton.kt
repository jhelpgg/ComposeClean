package fr.jhelp.composeclean.ui.composables.colorChooser

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.material3.Button
import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.ColorPainter
import androidx.compose.ui.unit.dp
import fr.jhelp.compose.provider.provided
import fr.jhelp.composeclean.models.DEFAULT_COLOR
import fr.jhelp.composeclean.models.shared.ColorChooserModel
import fr.jhelp.composeclean.models.shared.NavigationModel

/**
 * Button for choose color
 */
class ColorChooserButton
{
    private val navigationModel: NavigationModel by provided<NavigationModel>()
    private val colorChooserModel: ColorChooserModel by provided<ColorChooserModel>()
    private val currentColorMutable = mutableStateOf<Int>(DEFAULT_COLOR)

    /** Current select color */
    val currentColor: State<Int> = this.currentColorMutable

    /**
     * Show the color chooser button
     */
    @Composable
    fun Show(modifier: Modifier)
    {
        Button(onClick = {
            this.colorChooserModel.colorChangeListener =
                { color -> this.currentColorMutable.value = color }

            this.colorChooserModel.color(this.currentColorMutable.value)
            this.navigationModel.chooseColor()
        },
               modifier = modifier.wrapContentSize()) {
            Image(painter = ColorPainter(Color(this@ColorChooserButton.currentColorMutable.value)),
                  contentDescription = "Color choose",
                  modifier = Modifier.size(64.dp))
        }
    }
}
