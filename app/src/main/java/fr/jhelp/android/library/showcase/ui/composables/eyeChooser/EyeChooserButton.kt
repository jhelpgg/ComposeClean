package fr.jhelp.android.library.showcase.ui.composables.eyeChooser

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
import fr.jhelp.android.library.engine.resources.Eyes
import fr.jhelp.android.library.provider.provided
import fr.jhelp.android.library.models.shared.EyeChooserModel
import fr.jhelp.android.library.models.shared.NavigationModel

/**
 * Button for choose an eye
 */
class EyeChooserButton
{
    private val navigationModel: NavigationModel by provided<NavigationModel>()
    private val eyeChooserModel: EyeChooserModel by provided<EyeChooserModel>()
    private val eyeMutable = mutableStateOf(Eyes.GREEN)

    /**
     * Current selected eye
     */
    val eye: State<Eyes> = this.eyeMutable

    /**
     * Show the eye chooser button
     */
    @Composable
    fun Show(modifier: Modifier = Modifier)
    {
        Button(onClick = {
            val previousListener = this.eyeChooserModel.selectEyeListener
            this.eyeChooserModel.selectEyeListener = { eye ->
                this.eyeMutable.value = eye
                this.eyeChooserModel.selectEyeListener = previousListener
            }
            this.navigationModel.chooseEye()
        },
               modifier = modifier
                   .wrapContentSize()) {
            Image(bitmap = this@EyeChooserButton.eyeMutable.value.bitmap().asImageBitmap(),
                  contentDescription = "Eye",
                  modifier = Modifier.size(64.dp))
        }
    }
}