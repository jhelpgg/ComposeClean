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
import fr.jhelp.compose.constraint.extensions.near
import fr.jhelp.compose.constraint.extensions.next
import fr.jhelp.compose.constraint.extensions.startParent
import fr.jhelp.compose.constraint.extensions.topParent
import fr.jhelp.compose.provider.provided
import fr.jhelp.composeclean.models.shared.ImageTintModel
import fr.jhelp.composeclean.ui.composables.colorChooser.ColorChooserButton
import fr.jhelp.composeclean.ui.composables.imageChooser.ImageChooserButton

/**
 * Tint image sample
 */
class ImageTintComposable
{
    private val imageTintModel: ImageTintModel by provided<ImageTintModel>()
    private val imageChooserButton = ImageChooserButton()
    private val colorChooserButton = ColorChooserButton()

    /**
     * Show the image in grey interface
     */
    @Composable
    fun Show()
    {
        ConstraintLayout(modifier = Modifier.fillMaxSize()) {
            this@ImageTintComposable.imageTintModel.source(this@ImageTintComposable.imageChooserButton.currentSelectedImage.value)
            this@ImageTintComposable.imageTintModel.tintColor(this@ImageTintComposable.colorChooserButton.currentColor.value)
            val (image, imageChooser, colorChooser) = this.createRefs()

            Image(bitmap = this@ImageTintComposable.imageTintModel.image.asImageBitmap(),
                  contentDescription = "Image tinted",
                  modifier = Modifier.constrainAs(image) {
                      this.width = Dimension.fillToConstraints
                      this.height = Dimension.fillToConstraints
                      this.topParent
                      this.bottom next imageChooser.top
                      this.startParent
                      this.endParent
                  })

            val buttonSeparator = this.createGuidelineFromStart(0.5f)

            this@ImageTintComposable
                .imageChooserButton
                .Show(modifier = Modifier.constrainAs(imageChooser) {
                    this.width = Dimension.wrapContent
                    this.height = Dimension.wrapContent
                    this.bottomParent
                    this.startParent
                    this.end near buttonSeparator
                })

            this@ImageTintComposable
                .colorChooserButton
                .Show(modifier = Modifier.constrainAs(colorChooser) {
                    this.width = Dimension.wrapContent
                    this.height = Dimension.wrapContent
                    this.bottomParent
                    this.start near buttonSeparator
                    this.endParent
                })
        }
    }
}
