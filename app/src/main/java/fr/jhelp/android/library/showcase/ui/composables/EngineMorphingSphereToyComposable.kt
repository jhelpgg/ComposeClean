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
import fr.jhelp.android.library.engine.loaders.objLoader.ObjLoader
import fr.jhelp.android.library.engine.resources.ResourcesAccess
import fr.jhelp.android.library.engine.scene.Node3D
import fr.jhelp.android.library.engine.scene.Object3D
import fr.jhelp.android.library.engine.scene.Scene3D
import fr.jhelp.android.library.engine.scene.geometry.Sphere
import fr.jhelp.android.library.engine.scene.morphing.Morphing
import fr.jhelp.android.library.math.extensions.bounds
import fr.jhelp.android.library.showcase.R
import fr.jhelp.android.library.showcase.ui.engine.View3DComposable
import java.util.Stack

/**
 * Show morphing between box and sphere
 */
class EngineMorphingSphereToyComposable
{
    private var morphingStored: Morphing? = null
    private lateinit var scene3D: Scene3D

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
                self.scene3D = this.scene3D
                ResourcesAccess.loadNode("toy",
                                         fr.jhelp.android.library.engine.R.raw.toybear,
                                         ObjLoader,
                                         false)
                    .and { node -> self.createMorph(node) }
            }

            Slider(value = percent,
                   onValueChange = { value ->
                       percent = value.bounds(0f, 1f)
                       self.morphingStored?.percent = percent
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

    private fun createMorph(node3D: Node3D)
    {
        var object3DNullable: Object3D? = null
        val stack = Stack<Node3D>()
        stack.push(node3D)

        while (stack.isNotEmpty())
        {
            val node = stack.pop()

            if (node is Object3D)
            {
                object3DNullable = node
                break
            }

            for (child in node)
            {
                stack.push(child)
            }
        }

        val object3D = object3DNullable ?: return
        val textureStart = ResourcesAccess.obtainTexture(R.drawable.floor, sealed = false)
        val textureEnd =
            ResourcesAccess.obtainTexture(fr.jhelp.android.library.engine.R.drawable.toybear_default_color,
                                          sealed = false)

        val morphingStored = Morphing(Sphere(seal = false), object3D,
                                      textureStart, textureEnd,
                                      AnimationTextureSize.LARGE,
                                      scaleStart = 1f,
                                      scaleEnd = 0.01f)
        this.morphingStored = morphingStored
        morphingStored.doubleFace = true
        this.scene3D.root.add(morphingStored)
    }
}
