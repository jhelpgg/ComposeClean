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
import fr.jhelp.compose.ui.chooser.IntegerChooser
import fr.jhelp.composeclean.models.shared.ImageNeonLinesModel
import fr.jhelp.composeclean.ui.composables.colorChooser.ColorChooserButton

/**
 * Show image with neon lines
 */
class ImageNeonLinesComposable
{
    private val imageNeonLinesModel: ImageNeonLinesModel by provided<ImageNeonLinesModel>()
    private val colorChooserButton = ColorChooserButton()
    private val thinChooser = IntegerChooser(10, 32, 15)

    /**
     * Show image with neon lines
     */
    @Composable
    fun Show()
    {
        val self = this
        ConstraintLayout(modifier = Modifier.fillMaxSize()) {
            self.imageNeonLinesModel.color(self.colorChooserButton.currentColor.value)
            self.imageNeonLinesModel.thin(self.thinChooser.value.value)

            val (image, colorChooser, thinChooser) = this.createRefs()

            Image(bitmap = self.imageNeonLinesModel.image.asImageBitmap(),
                  contentDescription = "Image with neon lines",
                  modifier = Modifier.constrainAs(image) {
                      this.width = Dimension.fillToConstraints
                      this.height = Dimension.fillToConstraints
                      this.topParent
                      this.bottom near colorChooser.top
                      this.startParent
                      this.endParent
                  })

            self.colorChooserButton.Show(modifier = Modifier.constrainAs(colorChooser) {
                this.width = Dimension.wrapContent
                this.height = Dimension.wrapContent
                this.bottomParent
                this.startParent
            })

            self.thinChooser.Show(modifier = Modifier.constrainAs(thinChooser) {
                this.width = Dimension.fillToConstraints
                this.height = Dimension.wrapContent
                this.bottomParent
                this.start near colorChooser.end
                this.endParent
            })
        }
    }
}