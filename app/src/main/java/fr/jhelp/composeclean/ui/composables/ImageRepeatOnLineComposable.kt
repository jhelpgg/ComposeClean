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
import fr.jhelp.composeclean.models.shared.ImageRepeatOnLineModel
import fr.jhelp.composeclean.ui.composables.maskChooser.MaskChooserButton

/**
 * Show image with repeat on line
 */
class ImageRepeatOnLineComposable
{
    private val imageRepeatOnLineModel: ImageRepeatOnLineModel by provided<ImageRepeatOnLineModel>()
    private val maskChooserButton = MaskChooserButton()

    /**
     * Show image with repeat on line
     */
    @Composable
    fun Show()
    {
        val self = this
        ConstraintLayout(modifier = Modifier.fillMaxSize()) {
            self.imageRepeatOnLineModel.source(self.maskChooserButton.currentMask.value)

            val (image, maskChooser, stepSlider) = this.createRefs()

            Image(bitmap = self.imageRepeatOnLineModel.image.asImageBitmap(),
                  contentDescription = "Image with neon lines",
                  modifier = Modifier.constrainAs(image) {
                      this.width = Dimension.fillToConstraints
                      this.height = Dimension.fillToConstraints
                      this.topParent
                      this.bottom near maskChooser.top
                      this.startParent
                      this.endParent
                  })

            self.maskChooserButton.Show(modifier = Modifier.constrainAs(maskChooser) {
                this.width = Dimension.wrapContent
                this.height = Dimension.wrapContent
                this.bottomParent
                this.startParent
            })

            Slider(value = self.imageRepeatOnLineModel.step.value.toFloat(),
                   valueRange = 1f..64f,
                   onValueChange = { value -> self.imageRepeatOnLineModel.step(value.toInt()) },
                   modifier = Modifier.constrainAs(stepSlider) {
                       this.width = Dimension.fillToConstraints
                       this.height = Dimension.wrapContent
                       this.bottomParent
                       this.start near maskChooser.end
                       this.endParent
                   })
        }
    }
}