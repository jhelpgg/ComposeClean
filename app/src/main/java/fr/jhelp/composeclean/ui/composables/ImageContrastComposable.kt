package fr.jhelp.composeclean.ui.composables

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Slider
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.asImageBitmap
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.Dimension
import fr.jhelp.compose.constraint.extensions.bottomParent
import fr.jhelp.compose.constraint.extensions.endParent
import fr.jhelp.compose.constraint.extensions.near
import fr.jhelp.compose.constraint.extensions.startParent
import fr.jhelp.compose.constraint.extensions.topParent
import fr.jhelp.android.library.provider.provided
import fr.jhelp.composeclean.models.shared.ImageContrastModel
import fr.jhelp.composeclean.ui.composables.imageChooser.ImageChooserButton

/**
 * Show the image contrast sample
 */
class ImageContrastComposable
{
    private val imageContrastModel: ImageContrastModel by provided<ImageContrastModel>()
    private val imageChooserButton = ImageChooserButton()

    /**
     * Show the image contrast sample
     */
    @Composable
    fun Show()
    {
        ConstraintLayout(modifier = Modifier.fillMaxSize()) {
            this@ImageContrastComposable.imageContrastModel.source(this@ImageContrastComposable.imageChooserButton.currentSelectedImage.value)
            val (imageResult, imageChooser, contrastSlider) = this.createRefs()

            Image(bitmap = this@ImageContrastComposable.imageContrastModel.image.asImageBitmap(),
                  contentDescription = "Image with contrast",
                  modifier = Modifier.constrainAs(imageResult) {
                      this.width = Dimension.fillToConstraints
                      this.height = Dimension.fillToConstraints
                      this.topParent
                      this.bottom near imageChooser.top
                      this.startParent
                      this.endParent
                  })

            this@ImageContrastComposable
                .imageChooserButton
                .Show(modifier = Modifier.constrainAs(imageChooser) {
                    this.width = Dimension.wrapContent
                    this.height = Dimension.wrapContent
                    this.bottom near contrastSlider.top
                    this.startParent
                    this.endParent
                })

            Slider(value = this@ImageContrastComposable.imageContrastModel.contrast.value.toFloat(),
                   onValueChange = { value ->
                       this@ImageContrastComposable.imageContrastModel.contrast(value.toDouble())
                   },
                   valueRange = 0f..2f,
                   modifier = Modifier.constrainAs(contrastSlider) {
                       this.width = Dimension.fillToConstraints
                       this.height = Dimension.wrapContent
                       this.bottomParent
                       this.startParent
                       this.endParent
                   })
        }
    }
}
