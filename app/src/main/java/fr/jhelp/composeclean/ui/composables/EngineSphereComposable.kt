package fr.jhelp.composeclean.ui.composables

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Slider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
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
import fr.jhelp.compose.engine.scene.Node3D
import fr.jhelp.compose.engine.scene.Object3D
import fr.jhelp.compose.engine.scene.WHITE
import fr.jhelp.compose.engine.scene.geometry.Sphere
import fr.jhelp.compose.ui.engine.View3DComposable
import fr.jhelp.composeclean.ui.composables.imageChooser.ImageChooserButton

/**
 * Show the engine 3d with material texture change
 */
class EngineSphereComposable
{
    private val imageChooserButton = ImageChooserButton()
    private var nodeRoot = Node3D()

    /**
     * Show the engine 3d with material texture change
     */
    @Composable
    fun Show()
    {
        val self = this
        val view3DComposable = View3DComposable()
        var slice: Int by remember { mutableStateOf(16) }
        var slack: Int by remember { mutableStateOf(16) }

        ConstraintLayout(modifier = Modifier.fillMaxSize()) {
            val texture = textureReference()
            texture(texture, self.imageChooserButton.currentSelectedImage.value)
            val material = materialReference()
            material(material) {
                this.diffuse = WHITE
                this.textureReference = texture
            }

            val (view3D, imageChooser,
                sliceText, sliceSlider,
                slackText, slackSlider) = this.createRefs()

            view3DComposable.Draw(modifier = Modifier.constrainAs(view3D) {
                this.width = Dimension.fillToConstraints
                this.height = Dimension.fillToConstraints
                this.topParent
                this.bottom near imageChooser.top
                this.startParent
                this.endParent
            }) {
                this@EngineSphereComposable.nodeRoot = this.scene3D.root
                this.scenePosition { this.z = -2f }
                this.root {
                    this.sphere {
                        this.material(material)
                    }
                }
            }

            self.imageChooserButton.Show(modifier = Modifier.constrainAs(imageChooser) {
                this.width = Dimension.wrapContent
                this.height = Dimension.wrapContent
                this.bottom near sliceText.top
                this.startParent
                this.endParent
            })

            Text(text = "Slice $slice",
                 modifier = Modifier.constrainAs(sliceText) {
                     this.width = Dimension.wrapContent
                     this.height = Dimension.wrapContent
                     this.bottom near slackText.top
                     this.startParent
                 })

            Slider(value = slice.toFloat(),
                   onValueChange = { value ->
                       slice = value.toInt()
                       this@EngineSphereComposable.update(slice, slack)
                   },
                   valueRange = 2f..32f,
                   modifier = Modifier.constrainAs(sliceSlider) {
                       this.width = Dimension.fillToConstraints
                       this.height = Dimension.fillToConstraints
                       this.top.linkTo(sliceText.top)
                       this.bottom.linkTo(sliceText.bottom)
                       this.start near sliceText.end
                       this.endParent
                   })

            Text(text = "Slack $slack",
                 modifier = Modifier.constrainAs(slackText) {
                     this.width = Dimension.wrapContent
                     this.height = Dimension.wrapContent
                     this.bottomParent
                     this.startParent
                 })

            Slider(value = slack.toFloat(),
                   onValueChange = { value ->
                       slack = value.toInt()
                       this@EngineSphereComposable.update(slice, slack)
                   },
                   valueRange = 2f..32f,
                   modifier = Modifier.constrainAs(slackSlider) {
                       this.width = Dimension.fillToConstraints
                       this.height = Dimension.fillToConstraints
                       this.top.linkTo(slackText.top)
                       this.bottom.linkTo(slackText.bottom)
                       this.start near slackText.end
                       this.endParent
                   })
        }
    }

    private fun update(slice: Int, slack: Int)
    {
        val previous = this.nodeRoot.children(0) as Object3D
        val sphere = Sphere(slice = slice, slack = slack)
        sphere.material = previous.material
        this.nodeRoot.add(sphere)
        this.nodeRoot.remove(previous)
    }
}