package fr.jhelp.composeclean.ui.composables.maskChooser

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.material3.Button
import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.unit.dp
import fr.jhelp.android.library.provider.provided
import fr.jhelp.android.library.models.shared.ImageMasks
import fr.jhelp.android.library.models.shared.MaskChooserModel
import fr.jhelp.android.library.models.shared.NavigationModel

/**
 * Button for choose a mask
 */
class MaskChooserButton
{
    private val navigationModel: NavigationModel by provided<NavigationModel>()
    private val maskChooserModel: MaskChooserModel by provided<MaskChooserModel>()
    private val currentMaskMutable = mutableStateOf<ImageMasks>(ImageMasks.SMALL_CIRCLE)

    /** Current selected mask */
    val currentMask: State<ImageMasks> = this.currentMaskMutable

    /**
     * Show the mask chooser button
     */
    @Composable
    fun Show(modifier: Modifier)
    {
        Button(onClick = {
            val previousListener = this.maskChooserModel.selectedMaskListener
            this.maskChooserModel.selectedMaskListener = { mask ->
                this.currentMaskMutable.value = mask
                this.maskChooserModel.selectedMaskListener = previousListener
            }
            this.navigationModel.chooseMask()
        },
               modifier = modifier
                   .wrapContentSize()) {
            Image(bitmap = this@MaskChooserButton.currentMaskMutable.value.preview.asImageBitmap(),
                  contentDescription = "Mask",
                  modifier = Modifier.size(64.dp))
        }

    }
}
