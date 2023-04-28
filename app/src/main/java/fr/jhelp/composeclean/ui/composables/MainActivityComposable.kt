package fr.jhelp.composeclean.ui.composables

import android.content.Context
import android.content.Intent
import android.util.Log
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import fr.jhelp.compose.animations.interpolation.AccelerationInterpolation
import fr.jhelp.compose.animations.interpolation.AnticipateOvershootInterpolation
import fr.jhelp.compose.animations.interpolation.BounceInterpolation
import fr.jhelp.compose.engine.dsl.animation.player.AnimationPlayer
import fr.jhelp.compose.engine.dsl.animationNode
import fr.jhelp.compose.engine.dsl.animationNodeReference
import fr.jhelp.compose.engine.dsl.material
import fr.jhelp.compose.engine.dsl.materialReferences
import fr.jhelp.compose.engine.dsl.nodeReference
import fr.jhelp.compose.engine.dsl.texture
import fr.jhelp.compose.engine.dsl.textureReferences
import fr.jhelp.compose.engine.extensions.children
import fr.jhelp.compose.engine.extensions.position
import fr.jhelp.compose.engine.loaders.objLoader.ObjLoader
import fr.jhelp.compose.engine.view.joystick.VirtualJoystick
import fr.jhelp.compose.engine.view.joystick.VirtualJoystickType
import fr.jhelp.compose.engine.view.touch.View3DTouchOverSensitive
import fr.jhelp.compose.math.extensions.seconds
import fr.jhelp.compose.provider.provideSingle
import fr.jhelp.compose.provider.provided
import fr.jhelp.compose.ui.engine.View3DComposable
import fr.jhelp.composeclean.R
import fr.jhelp.composeclean.models.shared.MainModel
import fr.jhelp.composeclean.models.shared.preview.MainModelPreview
import fr.jhelp.composeclean.ui.activities.ContactListActivity
import fr.jhelp.composeclean.ui.extensions.stringResource
import fr.jhelp.composeclean.ui.theme.ComposeCleanTheme
import fr.jhelp.tasks.extensions.observedBy

/**
 * Main activity composable
 */
class MainActivityComposable
{
    private val applicationContext: Context by provided<Context>()
    private val mainModel: MainModel by provided<MainModel>()
    private lateinit var animationPlayer: AnimationPlayer

    /**
     * Show the main activity
     */
    @Composable
    fun Show()
    {
        val textChoice = this.mainModel.textChoiceState
        val view3DComposable = View3DComposable()

        val (textureBox, textureSphere, textureBear) = textureReferences()
        texture(textureBox, R.drawable.default_screen)
        texture(textureSphere, R.drawable.floor)
        texture(textureBear, fr.jhelp.compose.engine.R.drawable.toybear_default_color)
        val (materialBox, materialSphere, materialBear) = materialReferences()
        material(materialBox) {
            this.textureReference = textureBox
        }
        material(materialSphere) {
            this.textureReference = textureSphere
        }
        material(materialBear) {
            this.textureReference = textureBear
        }
        val centerReference = nodeReference()
        val animationCenter = animationNodeReference(centerReference)

        animationNode(animationCenter) {
            this.atTime(1.seconds, AccelerationInterpolation(2f)) {
                this.x = 0.5f
                this.y = 1f
                this.angleY = 180f
            }

            this.atTime(3.seconds, AnticipateOvershootInterpolation(5f)) {
                this.x = -0.5f
                this.y = -1f
                this.angleY = -180f
            }

            this.atTime(4.seconds + 500, BounceInterpolation) {
                this.x = 0f
                this.y = 0f
            }
        }

        Column {
            Text(text = stringResource(textChoice.value.stringResource))
            Button(onClick = {
                this@MainActivityComposable.mainModel.changeText()
                this@MainActivityComposable.animationPlayer.play()
            }) {
                Text(text = stringResource(R.string.buttonChangeText))
            }
            Button(onClick = {
                val intent = Intent(this@MainActivityComposable.applicationContext,
                                    ContactListActivity::class.java)
                intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK
                this@MainActivityComposable.applicationContext.startActivity(intent)
            }) {
                Text(text = stringResource(R.string.buttonContacts))
            }
            view3DComposable.Draw(modifier = Modifier
                .fillMaxWidth()
                .fillMaxHeight()) {
                this.scenePosition { this.z = -2f }
                this.root {
                    this.node(centerReference) {
                        this.children {
                            this.load(name = "bear",
                                      rawID = fr.jhelp.compose.engine.R.raw.toybear,
                                      loader = ObjLoader,
                                      material = materialBear) {
                                this.position {
                                    this.scale(0.01f)
                                }
                            }
                        }
                    }
                }

                this@MainActivityComposable.animationPlayer = this.player(animationCenter)
            }
        }

        val joystick = VirtualJoystick(VirtualJoystickType.GREEN)

        joystick.direction.observedBy { action -> Log.d("REMOVE_ME", "ACTION=$action") }
        joystick.click = { Log.d("REMOVE_ME", "CLICK") }
        view3DComposable.view3DTouchAction = View3DTouchOverSensitive(joystick)
    }

    /**
     * Composable preview of main activity
     */
    @Preview(showBackground = true)
    @Composable
    fun Preview()
    {
        provideSingle<MainModel> { MainModelPreview }
        ComposeCleanTheme {
            this.Show()
        }
    }
}