package fr.jhelp.composeclean.ui.composables

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Slider
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
import fr.jhelp.composeclean.models.shared.ImageShiftModel
import fr.jhelp.composeclean.ui.composables.imageChooser.ImageChooserButton

/**
 * Image with shift effect sample
 */
class ImageShiftComposable
{
    private val imageShiftModel: ImageShiftModel by provided<ImageShiftModel>()
    private val imageChooserButton = ImageChooserButton()

    /**
     * Show the image with shift effect sample
     */
    @Composable
    fun Show()
    {
        ConstraintLayout(modifier = Modifier.fillMaxSize()) {
            this@ImageShiftComposable.imageShiftModel.source(this@ImageShiftComposable.imageChooserButton.currentSelectedImage.value)
            val (imageResult, imageChooser, sliderShiftX, sliderShiftY) = this.createRefs()

            Image(bitmap = this@ImageShiftComposable.imageShiftModel.image.asImageBitmap(),
                  contentDescription = "Shifted image",
                  modifier = Modifier.constrainAs(imageResult) {
                      this.width = Dimension.fillToConstraints
                      this.height = Dimension.fillToConstraints
                      this.topParent
                      this.bottom near imageChooser.top
                      this.startParent
                      this.endParent
                  })

            this@ImageShiftComposable
                .imageChooserButton
                .Show(modifier = Modifier.constrainAs(imageChooser) {
                    this.width = Dimension.wrapContent
                    this.height = Dimension.wrapContent
                    this.bottom near sliderShiftX.top
                    this.startParent
                    this.endParent
                })

            Slider(value = this@ImageShiftComposable.imageShiftModel.shiftX.value.toFloat() / ImageShiftModel.SIZE.toFloat(),
                   onValueChange = { value -> this@ImageShiftComposable.imageShiftModel.shiftX((value * ImageShiftModel.SIZE).toInt()) },
                   valueRange = -1f..1f,
                   modifier = Modifier.constrainAs(sliderShiftX) {
                       this.width = Dimension.fillToConstraints
                       this.height = Dimension.wrapContent
                       this.bottom near sliderShiftY.top
                       this.startParent
                       this.endParent
                   })

            Slider(value = this@ImageShiftComposable.imageShiftModel.shiftY.value.toFloat() / ImageShiftModel.SIZE.toFloat(),
                   onValueChange = { value -> this@ImageShiftComposable.imageShiftModel.shiftY((value * ImageShiftModel.SIZE).toInt()) },
                   valueRange = -1f..1f,
                   modifier = Modifier.constrainAs(sliderShiftY) {
                       this.width = Dimension.fillToConstraints
                       this.height = Dimension.wrapContent
                       this.bottomParent
                       this.startParent
                       this.endParent
                   })
        }
    }
}
