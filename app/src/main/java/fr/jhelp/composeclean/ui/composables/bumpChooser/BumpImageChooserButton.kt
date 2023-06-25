package fr.jhelp.composeclean.ui.composables.bumpChooser

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.material3.Button
import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import fr.jhelp.android.library.provider.provided
import fr.jhelp.composeclean.R
import fr.jhelp.android.library.models.shared.ImageChooserModel
import fr.jhelp.android.library.models.shared.NavigationModel

/**
 * Button for choose bump image
 */
class BumpImageChooserButton
{
    private val navigationModel: NavigationModel by provided<NavigationModel>()
    private val imageChooserModel: ImageChooserModel by provided<ImageChooserModel>()

    private val currentSelectedImageMutable = mutableStateOf<Int>(R.drawable.bump1)

    /** Current selected image */
    val currentSelectedImage: State<Int> = this.currentSelectedImageMutable

    /**
     * Show the bump image chooser button
     */
    @Composable
    fun Show(modifier: Modifier)
    {
        Button(onClick = {
            val previousListener = this.imageChooserModel.selectImageListener
            this.imageChooserModel.selectImageListener = { image ->
                this.currentSelectedImageMutable.value = image
                this.imageChooserModel.selectImageListener = previousListener
            }
            this.navigationModel.chooseBumpImage()
        },
               modifier = modifier
                   .wrapContentSize()) {
            Image(painter = painterResource(
                id = this@BumpImageChooserButton.currentSelectedImageMutable.value),
                  contentDescription = "Bump image",
                  modifier = Modifier.size(64.dp))
        }
    }
}
