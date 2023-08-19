package fr.jhelp.android.library.showcase.ui.composables

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Slider
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
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
import fr.jhelp.android.library.engine.animation.keyFrame.AnimationTextureSize
import fr.jhelp.android.library.engine.dsl.nodeReference
import fr.jhelp.android.library.engine.dsl.texture
import fr.jhelp.android.library.engine.dsl.textureReferences
import fr.jhelp.android.library.engine.scene.geometry.Box
import fr.jhelp.android.library.engine.scene.geometry.Sphere
import fr.jhelp.android.library.engine.scene.morphing.Morphing
import fr.jhelp.android.library.math.extensions.bounds
import fr.jhelp.android.library.showcase.R
import fr.jhelp.android.library.showcase.ui.engine.View3DComposable

/**
 * Show morphing between box and sphere
 */
class EngineMorphingBoxSphereComposable
{
    private lateinit var morphingStored: Morphing

    /**
     * Show morphing between box and sphere
     */
    @Composable
    fun Show()
    {
        val self = this
        val view3DComposable = View3DComposable()
        var percent: Float by remember { mutableFloatStateOf(0f) }

        ConstraintLayout(modifier = Modifier.fillMaxSize()) {
            val (textureStart, textureEnd) = textureReferences()
            texture(textureStart, R.drawable.default_screen, seal = false)
            texture(textureEnd, R.drawable.floor, seal = false)
            val morphingReference = nodeReference()

            val (view3D, slider) = this.createRefs()
            view3DComposable.Draw(modifier = Modifier.constrainAs(view3D) {
                this.width = Dimension.fillToConstraints
                this.height = Dimension.fillToConstraints

                this.topParent
                this.bottom near slider.top
                this.startParent
                this.endParent
            }) {
                this.scenePosition { this.z = -2f }
                this.root {
                    this.morphing(morphingReference,
                                  Box(seal = false), Sphere(seal = false),
                                  textureStart, textureEnd,
                                  AnimationTextureSize.LARGE) { self.morphingStored = this }
                }
            }

            Slider(value = percent,
                   onValueChange = { value ->
                       percent = value.bounds(0f, 1f)
                       self.morphingStored.percent = percent
                   },
                   valueRange = 0f..1f,
                   modifier = Modifier.constrainAs(slider) {
                       this.width = Dimension.fillToConstraints
                       this.height = Dimension.wrapContent

                       // top free
                       this.bottomParent
                       this.startParent
                       this.endParent
                   })
        }
    }
}
