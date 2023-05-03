package fr.jhelp.composeclean.ui.composables.imageChooser

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.material3.Button
import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import fr.jhelp.compose.provider.provided
import fr.jhelp.composeclean.models.shared.ImageChooserModel
import fr.jhelp.composeclean.models.shared.NavigationModel

/**
 * Button for choose an image
 */
class ImageChooserButton
{
    private val navigationModel: NavigationModel by provided<NavigationModel>()
    private val imageChooserModel: ImageChooserModel by provided<ImageChooserModel>()

    /** Current selected image */
    val currentSelectedImage: State<Int> = this.imageChooserModel.currentSelectedImage

    /**
     * Show the image chooser button
     */
    @Composable
    fun Show(modifier: Modifier)
    {
        Button(onClick = this.navigationModel::chooseImage,
               modifier = modifier
                   .wrapContentSize()) {
            Image(painter = painterResource(
                id = this@ImageChooserButton.imageChooserModel.currentSelectedImage.value),
                  contentDescription = "Image",
                  modifier = Modifier.size(64.dp))
        }
    }
}