package fr.jhelp.android.library.showcase.ui.composables

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Slider
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.Dimension
import fr.jhelp.android.library.constraint.extensions.bottomParent
import fr.jhelp.android.library.constraint.extensions.endParent
import fr.jhelp.android.library.constraint.extensions.near
import fr.jhelp.android.library.constraint.extensions.startParent
import fr.jhelp.android.library.constraint.extensions.topParent
import fr.jhelp.android.library.engine.dsl.material
import fr.jhelp.android.library.engine.dsl.materialReferences
import fr.jhelp.android.library.engine.dsl.texture
import fr.jhelp.android.library.engine.dsl.textureReferences
import fr.jhelp.android.library.engine.extensions.material
import fr.jhelp.android.library.engine.extensions.position
import fr.jhelp.android.library.engine.scene.WHITE
import fr.jhelp.android.library.showcase.ui.engine.View3DComposable
import fr.jhelp.android.library.showcase.R

/**
 * Show transparency effect
 */
class EngineTransparencyComposable
{
    /**
     * Show transparency effect
     */
    @Composable
    fun Show()
    {
        var transparency: Float by remember { mutableStateOf(1f) }
        val view3DComposable = View3DComposable()
        val (textureOpaque, textureTransparency, textureBackground) = textureReferences()
        texture(textureReference = textureOpaque, resource = R.drawable.floor)
        texture(textureReference = textureTransparency, resource = R.drawable.default_screen)
        texture(textureReference = textureBackground, resource = R.drawable.bump4)
        val (materialOpaque, materialTransparency) = materialReferences()
        material(materialOpaque) {
            this.diffuse = WHITE
            this.textureReference = textureOpaque
        }
        material(materialTransparency) {
            this.alpha = 1f
            this.diffuse = WHITE
            this.textureReference = textureTransparency
        }

        ConstraintLayout(modifier = Modifier.fillMaxSize()) {
            val (view3D, transparencySlider) = this.createRefs()

            view3DComposable.Draw(modifier = Modifier.constrainAs(view3D) {
                this.width = Dimension.fillToConstraints
                this.height = Dimension.fillToConstraints
                this.topParent
                this.bottom near transparencySlider.top
                this.startParent
                this.endParent
            }) {
                this.scenePosition { this.z = -3f }
                this.backgroundTexture(textureBackground)
                this.root {
                    this.box {
                        this.material(materialOpaque)
                        this.position { this.z = -1f }
                    }

                    this.plane {
                        this.material(materialTransparency)
                        this.position { this.z = 1f }
                    }
                }
            }

            Slider(value = transparency,
                   onValueChange = { value ->
                       material(materialTransparency) {
                           this.alpha = value
                           this.diffuse = WHITE
                           this.textureReference = textureTransparency
                       }
                       transparency = value
                   },
                   modifier = Modifier.constrainAs(transparencySlider) {
                       this.width = Dimension.fillToConstraints
                       this.height = Dimension.wrapContent
                       this.bottomParent
                       this.startParent
                       this.endParent
                   })
        }
    }
}
