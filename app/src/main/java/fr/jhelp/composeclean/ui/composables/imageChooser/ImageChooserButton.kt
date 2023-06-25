package fr.jhelp.composeclean.ui.composables.imageChooser

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
import fr.jhelp.android.library.models.shared.ImageChooserModel
import fr.jhelp.android.library.models.shared.NavigationModel

/**
 * Button for choose an image
 */
class ImageChooserButton
{
    private val navigationModel: NavigationModel by provided<NavigationModel>()
    private val imageChooserModel: ImageChooserModel by provided<ImageChooserModel>()

    private val currentSelectedImageMutable = mutableStateOf<Int>(android.R.drawable.ic_delete)

    /** Current selected image */
    val currentSelectedImage: State<Int> = this.currentSelectedImageMutable

    /**
     * Show the image chooser button
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
            this.navigationModel.chooseImage()
        },
               modifier = modifier
                   .wrapContentSize()) {
            Image(painter = painterResource(
                id = this@ImageChooserButton.currentSelectedImageMutable.value),
                  contentDescription = "Image",
                  modifier = Modifier.size(64.dp))
        }
    }
}