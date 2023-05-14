package fr.jhelp.composeclean.ui.composables

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.Dimension
import fr.jhelp.compose.constraint.extensions.bottomParent
import fr.jhelp.compose.constraint.extensions.endParent
import fr.jhelp.compose.constraint.extensions.near
import fr.jhelp.compose.constraint.extensions.startParent
import fr.jhelp.compose.constraint.extensions.topParent
import fr.jhelp.compose.engine.dsl.material
import fr.jhelp.compose.engine.dsl.materialReference
import fr.jhelp.compose.engine.dsl.texture
import fr.jhelp.compose.engine.dsl.textureReference
import fr.jhelp.compose.engine.extensions.material
import fr.jhelp.compose.engine.scene.Color3D
import fr.jhelp.compose.ui.engine.View3DComposable
import fr.jhelp.composeclean.ui.composables.colorChooser.ColorChooserButton
import fr.jhelp.composeclean.ui.composables.imageChooser.ImageChooserButton

/**
 * Show the engine 3d with material texture applied in cross UV change
 */
class EngineTextureCrossComposable
{
    private val colorChooserButton = ColorChooserButton()
    private val imageChooserButton = ImageChooserButton()

    /**
     * Show the engine 3d with material texture applied in cross UV change
     */
    @Composable
    fun Show()
    {
        val self = this
        val view3DComposable = View3DComposable()

        ConstraintLayout(modifier = Modifier.fillMaxSize()) {
            val texture = textureReference()
            texture(texture, self.imageChooserButton.currentSelectedImage.value)
            val material = materialReference()
            material(material) {
                this.diffuse = Color3D(self.colorChooserButton.currentColor.value)
                this.textureReference = texture
            }

            val (view3D, imageChooser, colorChooser) = this.createRefs()

            view3DComposable.Draw(modifier = Modifier.constrainAs(view3D) {
                this.width = Dimension.fillToConstraints
                this.height = Dimension.fillToConstraints
                this.topParent
                this.bottom near imageChooser.top
                this.startParent
                this.endParent
            }) {
                this.scenePosition { this.z = -2f }
                this.root {
                    this.box(boxUV = { this.cross() }) {
                        this.material(material)
                    }
                }
            }

            val buttonSeparator = this.createGuidelineFromStart(0.5f)

            self.imageChooserButton.Show(modifier = Modifier.constrainAs(imageChooser) {
                this.width = Dimension.wrapContent
                this.height = Dimension.wrapContent
                this.bottomParent
                this.startParent
                this.end near buttonSeparator
            })

            self.colorChooserButton.Show(modifier = Modifier.constrainAs(colorChooser) {
                this.width = Dimension.wrapContent
                this.height = Dimension.wrapContent
                this.bottomParent
                this.start near buttonSeparator
                this.endParent
            })
        }
    }
}