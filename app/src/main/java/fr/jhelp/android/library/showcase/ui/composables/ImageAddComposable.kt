package fr.jhelp.android.library.showcase.ui.composables

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
import fr.jhelp.android.library.models.shared.ImageAddModel
import fr.jhelp.android.library.showcase.ui.composables.imageChooser.ImageChooserButton

/**
 * Show the add sample
 */
class ImageAddComposable
{
    private val imageAddModel: ImageAddModel by provided<ImageAddModel>()
    private val firstImageChooserButton = ImageChooserButton()
    private val secondImageChooserButton = ImageChooserButton()

    /**
     * Show the add sample
     */
    @Composable
    fun Show()
    {
        ConstraintLayout(modifier = Modifier.fillMaxSize()) {
            this@ImageAddComposable.imageAddModel.firstImage(this@ImageAddComposable.firstImageChooserButton.currentSelectedImage.value)
            this@ImageAddComposable.imageAddModel.secondImage(this@ImageAddComposable.secondImageChooserButton.currentSelectedImage.value)
            val (imageResult, firstImageChooserButton, secondImageChooserButton) = this.createRefs()

            Image(bitmap = this@ImageAddComposable.imageAddModel.image.asImageBitmap(),
                  contentDescription = "Image add result",
                  modifier = Modifier.constrainAs(imageResult) {
                      this.width = Dimension.fillToConstraints
                      this.height = Dimension.fillToConstraints
                      this.topParent
                      this.bottom near firstImageChooserButton.top
                      this.startParent
                      this.endParent
                  })

            val buttonSeparator = this.createGuidelineFromStart(0.5f)

            this@ImageAddComposable
                .firstImageChooserButton
                .Show(modifier = Modifier.constrainAs(firstImageChooserButton) {
                    this.width = Dimension.wrapContent
                    this.height = Dimension.wrapContent
                    this.bottomParent
                    this.startParent
                    this.end near buttonSeparator
                })

            this@ImageAddComposable
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