package fr.jhelp.composeclean.ui.composables

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.asImageBitmap
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.Dimension
import fr.jhelp.android.library.constraint.extensions.bottomParent
import fr.jhelp.android.library.constraint.extensions.endParent
import fr.jhelp.android.library.constraint.extensions.near
import fr.jhelp.android.library.constraint.extensions.startParent
import fr.jhelp.android.library.constraint.extensions.topParent
import fr.jhelp.android.library.provider.provided
import fr.jhelp.android.library.models.shared.ImageBumpMapModel
import fr.jhelp.composeclean.ui.composables.bumpChooser.BumpImageChooserButton
import fr.jhelp.composeclean.ui.composables.imageChooser.ImageChooserButton

/**
 * Show bump image effect
 */
class ImageBumpMapComposable
{
    private val imageBumpMapModel: ImageBumpMapModel by provided<ImageBumpMapModel>()
    private val imageChooserButton = ImageChooserButton()
    private val bumpImageChooserButton = BumpImageChooserButton()

    /**
     * Show bump image effect
     */
    @Composable
    fun Show()
    {
        ConstraintLayout(modifier = Modifier.fillMaxSize()) {
            this@ImageBumpMapComposable.imageBumpMapModel.source(this@ImageBumpMapComposable.imageChooserButton.currentSelectedImage.value)
            this@ImageBumpMapComposable.imageBumpMapModel.bumpImage(this@ImageBumpMapComposable.bumpImageChooserButton.currentSelectedImage.value)
            val (imageResult, firstImageChooserButton, secondImageChooserButton) = this.createRefs()

            Image(bitmap = this@ImageBumpMapComposable.imageBumpMapModel.image.asImageBitmap(),
                  contentDescription = "Image with bump image",
                  modifier = Modifier.constrainAs(imageResult) {
                      this.width = Dimension.fillToConstraints
                      this.height = Dimension.fillToConstraints
                      this.topParent
                      this.bottom near firstImageChooserButton.top
                      this.startParent
                      this.endParent
                  })

            val buttonSeparator = this.createGuidelineFromStart(0.5f)

            this@ImageBumpMapComposable
                .imageChooserButton
                .Show(modifier = Modifier.constrainAs(firstImageChooserButton) {
                    this.width = Dimension.wrapContent
                    this.height = Dimension.wrapContent
                    this.bottomParent
                    this.startParent
                    this.end near buttonSeparator
                })

            this@ImageBumpMapComposable
                .bumpImageChooserButton
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
