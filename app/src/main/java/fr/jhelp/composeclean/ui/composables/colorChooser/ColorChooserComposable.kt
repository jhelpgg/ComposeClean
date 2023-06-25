package fr.jhelp.composeclean.ui.composables.colorChooser

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Slider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.ColorPainter
import androidx.compose.ui.res.stringResource
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.Dimension
import fr.jhelp.android.library.constraint.extensions.bottomParent
import fr.jhelp.android.library.constraint.extensions.endParent
import fr.jhelp.android.library.constraint.extensions.near
import fr.jhelp.android.library.constraint.extensions.next
import fr.jhelp.android.library.constraint.extensions.startParent
import fr.jhelp.android.library.constraint.extensions.topParent
import fr.jhelp.android.library.provider.provided
import fr.jhelp.composeclean.R
import fr.jhelp.android.library.models.shared.ColorChooserModel

/**
 * Choose a color
 */
class ColorChooserComposable
{
    private val colorChooserModel: ColorChooserModel by provided<ColorChooserModel>()

    /**
     * Show th color chooser
     */
    @Composable
    fun Show()
    {
        ConstraintLayout(modifier = Modifier.fillMaxSize()) {
            val (colorPreview,
                redPartText, redPartSlider,
                greenPartText, greenPartSlider,
                bluePartText, bluePartSlider) = this.createRefs()

            Image(painter = ColorPainter(Color(this@ColorChooserComposable.colorChooserModel.color.value)),
                  contentDescription = "Color preview",
                  modifier = Modifier.constrainAs(colorPreview) {
                      this.width = Dimension.fillToConstraints
                      this.height = Dimension.fillToConstraints
                      this.topParent
                      this.bottom next redPartText.top
                      this.startParent
                      this.endParent
                  })

            Text(text = stringResource(id = R.string.red),
                 modifier = Modifier.constrainAs(redPartText) {
                     this.width = Dimension.wrapContent
                     this.height = Dimension.wrapContent
                     this.startParent
                     this.bottom near greenPartText.top
                 })

            Slider(value = this@ColorChooserComposable.colorChooserModel.red.value.toFloat() / 255f,
                   onValueChange = { value -> this@ColorChooserComposable.colorChooserModel.red((value * 255f).toInt()) },
                   modifier = Modifier.constrainAs(redPartSlider) {
                       this.width = Dimension.fillToConstraints
                       this.height = Dimension.fillToConstraints
                       this.top.linkTo(redPartText.top)
                       this.bottom.linkTo(redPartText.bottom)
                       this.start near redPartText.end
                       this.endParent
                   })


            Text(text = stringResource(id = R.string.green),
                 modifier = Modifier.constrainAs(greenPartText) {
                     this.width = Dimension.wrapContent
                     this.height = Dimension.wrapContent
                     this.startParent
                     this.bottom near bluePartText.top
                 })

            Slider(value = this@ColorChooserComposable.colorChooserModel.green.value.toFloat() / 255f,
                   onValueChange = { value -> this@ColorChooserComposable.colorChooserModel.green((value * 255f).toInt()) },
                   modifier = Modifier.constrainAs(greenPartSlider) {
                       this.width = Dimension.fillToConstraints
                       this.height = Dimension.fillToConstraints
                       this.top.linkTo(greenPartText.top)
                       this.bottom.linkTo(greenPartText.bottom)
                       this.start near greenPartText.end
                       this.endParent
                   })

            Text(text = stringResource(id = R.string.blue),
                 modifier = Modifier.constrainAs(bluePartText) {
                     this.width = Dimension.wrapContent
                     this.height = Dimension.wrapContent
                     this.startParent
                     this.bottomParent
                 })

            Slider(value = this@ColorChooserComposable.colorChooserModel.blue.value.toFloat() / 255f,
                   onValueChange = { value -> this@ColorChooserComposable.colorChooserModel.blue((value * 255f).toInt()) },
                   modifier = Modifier.constrainAs(bluePartSlider) {
                       this.width = Dimension.fillToConstraints
                       this.height = Dimension.fillToConstraints
                       this.top.linkTo(bluePartText.top)
                       this.bottom.linkTo(bluePartText.bottom)
                       this.start near bluePartText.end
                       this.endParent
                   })
        }
    }
}
