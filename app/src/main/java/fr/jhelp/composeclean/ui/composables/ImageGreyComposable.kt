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
import fr.jhelp.android.library.constraint.extensions.next
import fr.jhelp.android.library.constraint.extensions.startParent
import fr.jhelp.android.library.constraint.extensions.topParent
import fr.jhelp.android.library.provider.provided
import fr.jhelp.composeclean.models.shared.ImageGreyModel
import fr.jhelp.composeclean.ui.composables.imageChooser.ImageChooserButton

/**
 * Image put in grey
 */
class ImageGreyComposable
{
    private val imageGreyModel: ImageGreyModel by provided<ImageGreyModel>()
    private val imageChooserButton = ImageChooserButton()

    /**
     * Show the image in grey interface
     */
    @Composable
    fun Show()
    {
        ConstraintLayout(modifier = Modifier.fillMaxSize()) {
            this@ImageGreyComposable.imageGreyModel.source(this@ImageGreyComposable.imageChooserButton.currentSelectedImage.value)
            val (image, imageChooser) = this.createRefs()

            Image(bitmap = this@ImageGreyComposable.imageGreyModel.image.asImageBitmap(),
                  contentDescription = "Image in grey",
                  modifier = Modifier.constrainAs(image) {
                      this.width = Dimension.fillToConstraints
                      this.height = Dimension.fillToConstraints
                      this.topParent
                      this.bottom next imageChooser.top
                      this.startParent
                      this.endParent
                  })

            this@ImageGreyComposable
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
