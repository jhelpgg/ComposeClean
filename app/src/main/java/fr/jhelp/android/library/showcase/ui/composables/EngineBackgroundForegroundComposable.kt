package fr.jhelp.android.library.showcase.ui.composables

import android.graphics.Paint
import android.os.SystemClock
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Checkbox
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.Dimension
import fr.jhelp.android.library.engine.dsl.material
import fr.jhelp.android.library.engine.dsl.materialReference
import fr.jhelp.android.library.engine.dsl.texture
import fr.jhelp.android.library.engine.dsl.textureReference
import fr.jhelp.android.library.engine.extensions.material
import fr.jhelp.android.library.engine.resources.ResourcesAccess
import fr.jhelp.android.library.engine.resources.draw
import fr.jhelp.android.library.engine.resources.texture
import fr.jhelp.android.library.engine.scene.Color3D
import fr.jhelp.android.library.engine.scene.Scene3D
import fr.jhelp.android.library.engine.scene.WHITE
import fr.jhelp.android.library.images.clear
import fr.jhelp.android.library.constraint.extensions.bottomParent
import fr.jhelp.android.library.constraint.extensions.endParent
import fr.jhelp.android.library.constraint.extensions.far
import fr.jhelp.android.library.constraint.extensions.near
import fr.jhelp.android.library.constraint.extensions.startParent
import fr.jhelp.android.library.constraint.extensions.topParent
import fr.jhelp.android.library.showcase.ui.engine.View3DComposable
import fr.jhelp.android.library.showcase.R
import fr.jhelp.android.library.showcase.ui.composables.colorChooser.ColorChooserButton
import fr.jhelp.android.library.showcase.ui.composables.imageChooser.ImageChooserButton
import kotlin.random.Random

/**
 * Show the background and foreground change
 */
class EngineBackgroundForegroundComposable
{
    private val backgroundColorChooser = ColorChooserButton()
    private val backgroundImageChooser = ImageChooserButton()
    private lateinit var scene: Scene3D
    private val backgroundCheckState = mutableStateOf(false)
    private val foregroundCheckState = mutableStateOf(false)

    /**
     * Show the background and foreground change
     */
    @Composable
    fun Show()
    {
        val rain = texture(512, 512)
        rain.draw { bitmap, canvas, paint ->
            bitmap.clear(0)
            paint.color = 0x88_12_34_56.toInt()
            paint.style = Paint.Style.FILL
            val random = Random(SystemClock.elapsedRealtime())

            for (y in 0 until 8)
            {
                for (x in 0 until 8)
                {
                    if (random.nextBoolean())
                    {
                        canvas.drawCircle(8f + 64f * x.toFloat(),
                                          8f + 64f * y.toFloat(),
                                          16f,
                                          paint)
                    }
                }
            }
        }

        val self = this
        val view3DComposable = View3DComposable()
        val textureBox = textureReference()
        texture(textureBox, R.drawable.floor)
        val materialBox = materialReference()
        material(materialBox) {
            this.diffuse = WHITE
            this.textureReference = textureBox
        }

        ConstraintLayout(modifier = Modifier.fillMaxSize()) {
            val (view3D, backgroundColor, backgroundCheck, backgroundTexture, foregroundCheck, foregroundText) = this.createRefs()

            view3DComposable.Draw(modifier = Modifier.constrainAs(view3D) {
                this.width = Dimension.fillToConstraints
                this.height = Dimension.fillToConstraints
                this.topParent
                this.bottom near backgroundColor.top
                this.startParent
                this.endParent
            }) {
                self.scene = this.scene3D

                if (self.backgroundCheckState.value)
                {
                    self.scene.textureBackground =
                        ResourcesAccess.obtainTexture(self.backgroundImageChooser.currentSelectedImage.value)
                }
                else
                {
                    self.scene.textureBackground = null
                }


                if (self.foregroundCheckState.value)
                {
                    self.scene.textureOver3D = rain
                }
                else
                {
                    self.scene.textureOver3D = null
                }

                this.scene3D.backgroundColor =
                    Color3D(self.backgroundColorChooser.currentColor.value)
                this.scenePosition { this.z = -2f }
                this.root { this.box { this.material(materialBox) } }
            }

            self.backgroundColorChooser.Show(modifier = Modifier.constrainAs(backgroundColor) {
                this.width = Dimension.wrapContent
                this.height = Dimension.wrapContent
                this.bottom near foregroundCheck.top
                this.startParent
            })

            Checkbox(checked = self.backgroundCheckState.value,
                     onCheckedChange = { value ->
                         self.backgroundCheckState.value = value
                         if (value)
                         {
                             self.scene.textureBackground =
                                 ResourcesAccess.obtainTexture(self.backgroundImageChooser.currentSelectedImage.value)
                         }
                         else
                         {
                             self.scene.textureBackground = null
                         }
                     },
                     modifier = Modifier.constrainAs(backgroundCheck) {
                         this.width = Dimension.wrapContent
                         this.height = Dimension.wrapContent
                         this.top.linkTo(backgroundColor.top)
                         this.bottom.linkTo(backgroundColor.bottom)
                         this.start far backgroundColor.end
                     })

            self.backgroundImageChooser.Show(modifier = Modifier.constrainAs(backgroundTexture) {
                this.width = Dimension.wrapContent
                this.height = Dimension.wrapContent
                this.bottom near foregroundCheck.top
                this.start near backgroundCheck.end
            })

            Checkbox(checked = self.foregroundCheckState.value,
                     onCheckedChange = { value ->
                         self.foregroundCheckState.value = value
                         if (value)
                         {
                             self.scene.textureOver3D = rain
                         }
                         else
                         {
                             self.scene.textureOver3D = null
                         }
                     },
                     modifier = Modifier.constrainAs(foregroundCheck) {
                         this.width = Dimension.wrapContent
                         this.height = Dimension.wrapContent
                         this.top.linkTo(foregroundText.top)
                         this.bottom.linkTo(foregroundText.bottom)
                         this.startParent
                     })

            Text(text = stringResource(id = R.string.showForeground),
                 modifier = Modifier.constrainAs(foregroundText) {
                     this.width = Dimension.wrapContent
                     this.height = Dimension.wrapContent
                     this.bottomParent
                     this.start near foregroundCheck.end
                 })
        }
    }
}
