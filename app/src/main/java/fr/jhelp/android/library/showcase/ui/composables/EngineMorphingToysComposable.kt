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
import fr.jhelp.android.library.engine.scene.morphing.Morphing
import fr.jhelp.android.library.math.extensions.bounds
import fr.jhelp.android.library.showcase.R
import fr.jhelp.android.library.showcase.ui.engine.View3DComposable
import fr.jhelp.android.library.tasks.future.combine
import java.util.Stack

/**
 * Show morphing between two toys
 */
class EngineMorphingToysComposable
{
    private var morphingStored: Morphing? = null
    private lateinit var scene3D: Scene3D

    /**
     * Show morphing between  two toys
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
                val futureBear = ResourcesAccess.loadNode("toyBear",
                                                          fr.jhelp.android.library.engine.R.raw.toybear,
                                                          ObjLoader,
                                                          false)
                val futurePenguin = ResourcesAccess.loadNode("toyPenguin",
                                                             R.raw.toypenguin,
                                                             ObjLoader,
                                                             false)
                combine(futureBear, futurePenguin, self::createMorph)
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

    private fun extractObject3D(node3D: Node3D): Object3D?
    {
        val stack = Stack<Node3D>()
        stack.push(node3D)

        while (stack.isNotEmpty())
        {
            val node = stack.pop()

            if (node is Object3D)
            {
                return node
            }

            for (child in node)
            {
                stack.push(child)
            }
        }

        return null
    }

    private fun createMorph(nodeBear: Node3D, nodePenguin: Node3D)
    {
        val objectBear = this.extractObject3D(nodeBear) ?: return
        val objectPenguin = this.extractObject3D(nodePenguin) ?: return

        val textureBear =
            ResourcesAccess.obtainTexture(fr.jhelp.android.library.engine.R.drawable.toybear_default_color,
                                          sealed = false)
        val texturePenguin =
            ResourcesAccess.obtainTexture(R.drawable.toypenguin_default_color,
                                          sealed = false)

        val morphingStored = Morphing(objectBear, objectPenguin,
                                      textureBear, texturePenguin,
                                      AnimationTextureSize.LARGE,
                                      scaleStart = 0.01f,
                                      scaleEnd = 0.01f)
        this.morphingStored = morphingStored
        morphingStored.doubleFace = true
        this.scene3D.root.add(morphingStored)
    }
}
