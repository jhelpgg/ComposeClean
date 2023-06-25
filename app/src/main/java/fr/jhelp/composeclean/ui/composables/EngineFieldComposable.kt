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
import fr.jhelp.android.library.engine.dsl.material
import fr.jhelp.android.library.engine.dsl.materialReference
import fr.jhelp.android.library.engine.dsl.texture
import fr.jhelp.android.library.engine.dsl.textureReference
import fr.jhelp.android.library.engine.extensions.material
import fr.jhelp.android.library.engine.scene.WHITE
import fr.jhelp.android.library.math.PI_FLOAT
import fr.jhelp.android.library.math.formal.X
import fr.jhelp.android.library.math.formal.Y
import fr.jhelp.android.library.math.formal.cos
import fr.jhelp.android.library.math.formal.sin
import fr.jhelp.android.library.math.formal.times
import fr.jhelp.compose.ui.engine.View3DComposable
import fr.jhelp.composeclean.ui.composables.imageChooser.ImageChooserButton

/**
 * Show the engine 3d with material texture change
 */
class EngineFieldComposable
{
    private val imageChooserButton = ImageChooserButton()

    /**
     * Show the engine 3d with material texture change
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
                this.diffuse = WHITE
                this.textureReference = texture
            }

            val (view3D, imageChooser) = this.createRefs()

            view3DComposable.Draw(modifier = Modifier.constrainAs(view3D) {
                this.width = Dimension.fillToConstraints
                this.height = Dimension.fillToConstraints
                this.topParent
                this.bottom near imageChooser.top
                this.startParent
                this.endParent
            }) {
                this.scenePosition {
                    this.angleX = -32f
                    this.z = -6f
                }
                this.root {
                    this.field(
                        functionZ = cos(X) * sin(Y),
                        xStart = -PI_FLOAT, xEnd = PI_FLOAT, numberPartX = 10,
                        yStart = -PI_FLOAT, yEnd = PI_FLOAT, numberPartY = 10) {
                        this.material(material)
                    }
                }
            }

            self.imageChooserButton.Show(modifier = Modifier.constrainAs(imageChooser) {
                this.width = Dimension.wrapContent
                this.height = Dimension.wrapContent
                this.bottomParent
                this.startParent
                this.endParent
            })
        }
    }
}