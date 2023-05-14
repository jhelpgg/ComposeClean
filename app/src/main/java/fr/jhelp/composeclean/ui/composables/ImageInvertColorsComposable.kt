package fr.jhelp.composeclean.ui.composables

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.asImageBitmap
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.Dimension
import fr.jhelp.compose.constraint.extensions.bottomParent
import fr.jhelp.compose.constraint.extensions.endParent
import fr.jhelp.compose.constraint.extensions.next
import fr.jhelp.compose.constraint.extensions.startParent
import fr.jhelp.compose.constraint.extensions.topParent
import fr.jhelp.compose.provider.provided
import fr.jhelp.composeclean.models.shared.ImageInvertColorsModel
import fr.jhelp.composeclean.ui.composables.imageChooser.ImageChooserButton

/**
 * Image with invert colors
 */
class ImageInvertColorsComposable
{
    private val imageInvertColorsModel: ImageInvertColorsModel by provided<ImageInvertColorsModel>()
    private val imageChooserButton = ImageChooserButton()

    /**
     * Show the image with invert colors interface
     */
    @Composable
    fun Show()
    {
        ConstraintLayout(modifier = Modifier.fillMaxSize()) {
            this@ImageInvertColorsComposable.imageInvertColorsModel.source(this@ImageInvertColorsComposable.imageChooserButton.currentSelectedImage.value)
            val (image, imageChooser) = this.createRefs()

            Image(bitmap = this@ImageInvertColorsComposable.imageInvertColorsModel.image.asImageBitmap(),
                  contentDescription = "Image with colors inverted",
                  modifier = Modifier.constrainAs(image) {
                      this.width = Dimension.fillToConstraints
                      this.height = Dimension.fillToConstraints
                      this.topParent
                      this.bottom next imageChooser.top
                      this.startParent
                      this.endParent
                  })

            this@ImageInvertColorsComposable
                .imageChooserButton
                .Show(modifier = Modifier.constrainAs(imageChooser) {
                    this.width = Dimension.wrapContent
                    this.height = Dimension.wrapContent
                    this.bottomParent
                    this.startParent
                    this.endParent
                })
        }
    }
}
