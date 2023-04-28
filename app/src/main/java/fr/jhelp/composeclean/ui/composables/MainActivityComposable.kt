package fr.jhelp.composeclean.ui.composables

import android.content.Context
import android.content.Intent
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
import fr.jhelp.compose.engine.extensions.material
import fr.jhelp.compose.engine.extensions.position
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

/**
 * Main activity composable
 */
class MainActivityComposable
{
    private val applicationContext: Context by provided<Context>()
    private val mainModel: MainModel by provided<MainModel>()
    private lateinit var animationPlayer: AnimationPlayer

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
            textureReference = textureBox
        }
        material(materialSphere) {
            textureReference = textureSphere
        }
        material(materialBear) {
            textureReference = textureBear
        }
        val centerReference = nodeReference()
        val animationCenter = animationNodeReference(centerReference)

        animationNode(animationCenter) {
            atTime(1.seconds, AccelerationInterpolation(2f)) {
                x = 0.5f
                y = 1f
                angleY = 180f
            }

            atTime(3.seconds, AnticipateOvershootInterpolation(5f)) {
                x = -0.5f
                y = -1f
                angleY = -180f
            }

            atTime(4.seconds + 500, BounceInterpolation) {
                x = 0f
                y = 0f
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
                scenePosition { z = -2f }
                root {
                    node(centerReference) {
                        children {
                            load(name = "bear",
                                 rawID = fr.jhelp.compose.engine.R.raw.toybear,
                                 loader = ObjLoader,
                                 material = materialBear) {
                                position {
                                    scale(0.01f)
                                }
                            }
                        }
                    }
                }

                this@MainActivityComposable.animationPlayer = player(animationCenter)
            }
        }
    }

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