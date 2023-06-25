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
import fr.jhelp.compose.constraint.extensions.startParent
import fr.jhelp.compose.constraint.extensions.topParent
import fr.jhelp.android.library.provider.provided
import fr.jhelp.composeclean.models.shared.ImageMultiplyModel
import fr.jhelp.composeclean.ui.composables.imageChooser.ImageChooserButton

/**
 * Show the multiply sample
 */
class ImageMultiplyComposable
{
    private val imageMultiplyModel: ImageMultiplyModel by provided<ImageMultiplyModel>()
    private val firstImageChooserButton = ImageChooserButton()
    private val secondImageChooserButton = ImageChooserButton()

    /**
     * Show the multiply sample
     */
    @Composable
    fun Show()
    {
        ConstraintLayout(modifier = Modifier.fillMaxSize()) {
            this@ImageMultiplyComposable.imageMultiplyModel.firstImage(this@ImageMultiplyComposable.firstImageChooserButton.currentSelectedImage.value)
            this@ImageMultiplyComposable.imageMultiplyModel.secondImage(this@ImageMultiplyComposable.secondImageChooserButton.currentSelectedImage.value)
            val (imageResult, firstImageChooserButton, secondImageChooserButton) = this.createRefs()

            Image(bitmap = this@ImageMultiplyComposable.imageMultiplyModel.image.asImageBitmap(),
                  contentDescription = "Image multiply result",
                  modifier = Modifier.constrainAs(imageResult) {
                      this.width = Dimension.fillToConstraints
                      this.height = Dimension.fillToConstraints
                      this.topParent
                      this.bottom near firstImageChooserButton.top
                      this.startParent
                      this.endParent
                  })

            val buttonSeparator = this.createGuidelineFromStart(0.5f)

            this@ImageMultiplyComposable
                .firstImageChooserButton
                .Show(modifier = Modifier.constrainAs(firstImageChooserButton) {
                    this.width = Dimension.wrapContent
                    this.height = Dimension.wrapContent
                    this.bottomParent
                    this.startParent
                    this.end near buttonSeparator
                })

            this@ImageMultiplyComposable
                .secondImageChooserButton
                .Show(modifier = Modifier.constrainAs(secondImageChooserButton) {
                    this.width = Dimension.wrapContent
                    this.height = Dimension.wrapContent
                    this.bottomParent
                    this.start near buttonSeparator
                    this.endParent
                })
        }
    }
}