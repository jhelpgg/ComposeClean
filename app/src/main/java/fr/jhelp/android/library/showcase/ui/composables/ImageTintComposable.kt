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
import fr.jhelp.android.library.constraint.extensions.next
import fr.jhelp.android.library.constraint.extensions.startParent
import fr.jhelp.android.library.constraint.extensions.topParent
import fr.jhelp.android.library.provider.provided
import fr.jhelp.android.library.models.shared.ImageTintModel
import fr.jhelp.android.library.showcase.ui.composables.colorChooser.ColorChooserButton
import fr.jhelp.android.library.showcase.ui.composables.imageChooser.ImageChooserButton

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
