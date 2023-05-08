package fr.jhelp.composeclean.ui.composables.maskChooser

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.material3.Button
import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.unit.dp
import fr.jhelp.compose.provider.provided
import fr.jhelp.composeclean.models.shared.ImageMasks
import fr.jhelp.composeclean.models.shared.MaskChooserModel
import fr.jhelp.composeclean.models.shared.NavigationModel

/**
 * Button for choose a mask
 */
class MaskChooserButton
{
    private val navigationModel: NavigationModel by provided<NavigationModel>()
    private val maskChooserModel: MaskChooserModel by provided<MaskChooserModel>()

    /** Current selected mask */
    val currentMask: State<ImageMasks> = this.maskChooserModel.currentSelectedMask

    /**
     * Show the mask chooser button
     */
    @Composable
    fun Show(modifier: Modifier)
    {
        Button(onClick = this.navigationModel::chooseMask,
               modifier = modifier
                   .wrapContentSize()) {
            Image(bitmap = this@MaskChooserButton.maskChooserModel.currentSelectedMask.value.preview.asImageBitmap(),
                  contentDescription = "Mask",
                  modifier = Modifier.size(64.dp))
        }

    }
}
