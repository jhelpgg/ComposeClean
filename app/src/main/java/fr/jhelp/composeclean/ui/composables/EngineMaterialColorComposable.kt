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
import fr.jhelp.android.library.engine.extensions.material
import fr.jhelp.android.library.engine.scene.Color3D
import fr.jhelp.compose.ui.engine.View3DComposable
import fr.jhelp.composeclean.ui.composables.colorChooser.ColorChooserButton

/**
 * Show the engine 3d with material color change
 */
class EngineMaterialColorComposable
{
    private val colorChooserButton = ColorChooserButton()

    /**
     * Show the engine 3d with material color change
     */
    @Composable
    fun Show()
    {
        val self = this
        val view3DComposable = View3DComposable()

        ConstraintLayout(modifier = Modifier.fillMaxSize()) {
            val material = materialReference()
            material(material) {
                this.diffuse = Color3D(self.colorChooserButton.currentColor.value)
            }

            val (view3D, colorChooser) = this.createRefs()

            view3DComposable.Draw(modifier = Modifier.constrainAs(view3D) {
                this.width = Dimension.fillToConstraints
                this.height = Dimension.fillToConstraints
                this.topParent
                this.bottom near colorChooser.top
                this.startParent
                this.endParent
            }) {
                this.scenePosition { this.z = -2f }
                this.root {
                    this.box {
                        this.material(material)
                    }
                }
            }

            self.colorChooserButton.Show(modifier = Modifier.constrainAs(colorChooser) {
                this.width = Dimension.wrapContent
                this.height = Dimension.wrapContent
                this.bottomParent
                this.startParent
                this.endParent
            })
        }
    }
}