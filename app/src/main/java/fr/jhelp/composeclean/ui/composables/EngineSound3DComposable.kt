package fr.jhelp.composeclean.ui.composables

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import fr.jhelp.compose.animations.interpolation.AccelerationInterpolation
import fr.jhelp.compose.animations.interpolation.AnticipateOvershootInterpolation
import fr.jhelp.compose.animations.interpolation.DecelerationInterpolation
import fr.jhelp.compose.animations.interpolation.HesitateInterpolation
import fr.jhelp.compose.animations.interpolation.SinusInterpolation
import fr.jhelp.compose.engine.dsl.animationNode
import fr.jhelp.compose.engine.dsl.animationNodeReference
import fr.jhelp.compose.engine.dsl.material
import fr.jhelp.compose.engine.dsl.materialReference
import fr.jhelp.compose.engine.dsl.nodeReference
import fr.jhelp.compose.engine.dsl.texture
import fr.jhelp.compose.engine.dsl.textureReference
import fr.jhelp.compose.engine.extensions.material
import fr.jhelp.compose.engine.scene.Sound3D
import fr.jhelp.compose.engine.scene.WHITE
import fr.jhelp.compose.engine.view.touch.View3DTouchMove
import fr.jhelp.compose.ui.engine.View3DComposable
import fr.jhelp.composeclean.R

/**
 * Show the 3D sound effect.
 *
 * User should have stereo spear/headphone to ear the effect
 */
class EngineSound3DComposable
{
    /**
     * Show the 3D sound effect.
     *
     * User should have stereo spear/headphone to ear the effect
     */
    @Composable
    fun Show()
    {
        val view3D = View3DComposable()
        val texture = textureReference()
        texture(texture, R.drawable.floor)
        val material = materialReference()
        material(material) {
            this.diffuse = WHITE
            this.textureReference = texture
        }
        val sound = Sound3D(R.raw.controlled1, loop = -1)
        sound.play()
        val box = nodeReference()
        val animation = animationNodeReference(box)
        animationNode(animation) {
            var time = 0

            for (repeat in 0 until 5)
            {
                time += 3_000
                this.atTime(time, AccelerationInterpolation(2f)) {
                    this.x = -2f
                    this.y = -2f
                }

                time += 4_000
                this.atTime(time, HesitateInterpolation) {
                    this.x = 2f
                    this.y = 0f
                }

                time += 3_000
                this.atTime(time, AnticipateOvershootInterpolation(2f)) {
                    this.x = 0f
                    this.y = 0f
                }

                time += 4_000
                this.atTime(time, SinusInterpolation) {
                    this.x = -5f
                    this.y = 5f
                }

                time += 5_000
                this.atTime(time, DecelerationInterpolation(2f)) {
                    this.x = 0f
                    this.y = 0f
                }
            }
        }

        view3D.view3DTouchAction = View3DTouchMove
        view3D.Draw(modifier = Modifier.fillMaxSize()) {
            this.scenePosition { this.z = -4f }
            this.root {
                this.box(box) {
                    this.sound3D = sound
                    this.material(material)
                }
            }
            this.player(animation).play()
        }
    }
}