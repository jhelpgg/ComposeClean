package fr.jhelp.android.library.showcase.ui.composables

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.Dimension
import fr.jhelp.compose.animations.interpolation.AccelerationInterpolation
import fr.jhelp.compose.animations.interpolation.DecelerationInterpolation
import fr.jhelp.android.library.constraint.extensions.bottomParent
import fr.jhelp.android.library.constraint.extensions.endParent
import fr.jhelp.android.library.constraint.extensions.near
import fr.jhelp.android.library.constraint.extensions.startParent
import fr.jhelp.android.library.constraint.extensions.topParent
import fr.jhelp.android.library.engine.dsl.animation.effect.ParticlePlayer
import fr.jhelp.android.library.engine.dsl.material
import fr.jhelp.android.library.engine.dsl.materialReference
import fr.jhelp.android.library.engine.dsl.particleEffect
import fr.jhelp.android.library.engine.dsl.particleEffectReference
import fr.jhelp.android.library.engine.dsl.texture
import fr.jhelp.android.library.engine.dsl.textureReferences
import fr.jhelp.android.library.engine.extensions.material
import fr.jhelp.android.library.engine.scene.Color3D
import fr.jhelp.android.library.engine.scene.LIGHT_GREEN
import fr.jhelp.android.library.engine.scene.WHITE
import fr.jhelp.android.library.math.COLOR_LIGHT_GREEN_0300
import fr.jhelp.android.library.showcase.ui.engine.View3DComposable
import fr.jhelp.android.library.showcase.R

/**
 * Show sword particle effect
 */
class EngineParticleSwordComposable
{
    private lateinit var swordPlayer: ParticlePlayer

    /**
     * Show sword particle effect
     */
    @Composable
    fun Show()
    {
        val view3DComposable = View3DComposable()

        val (textureBox, textureParticle) = textureReferences()
        texture(textureBox, R.drawable.floor)
        texture(textureParticle, R.drawable.particle_clear_hard)

        val materialBox = materialReference()
        material(materialBox) {
            this.diffuse = WHITE
            this.textureReference = textureBox
        }

        val colorStart = LIGHT_GREEN
        val colorEnd = Color3D(COLOR_LIGHT_GREEN_0300)

        val particleEffectSword = particleEffectReference()
        particleEffect(particleEffectSword) {
            // Node sword line
            this.add {
                this.numberParticle = 1

                this.lifeTimeInFrame = 20f
                this.startEmissionFrame = 0f
                this.stopEmissionFrame = 0f

                this.positionFirstSecond(0f, 0f, -2f)
                this.firstStartAngle = -45f
                this.secondStartAngle = -45f
                this.scaleFirstSecond(0.05f, 0.2f, 0.05f)
                this.accelerationScaleFirstSecond(0f, 0.012f, 0f)

                this.texture = textureParticle
                this.firstStartDiffuseColor = colorStart
                this.secondStartDiffuseColor = colorStart
                this.firstEndDiffuseColor = colorEnd
                this.secondEndDiffuseColor = colorEnd
            }

            // Node star line
            this.add {
                this.numberParticle = 10

                this.lifeTimeInFrame = 20f
                this.startEmissionFrame = 0f
                this.stopEmissionFrame = 0f

                this.positionFirstSecond(0f, 0f, -2f)
                this.firstStartAngle = 0f
                this.secondStartAngle = 180f
                this.scaleFirstSecond(0.04f)
                this.accelerationScaleFirstSecond(0f, 0.007f, 0f)

                this.texture = textureParticle
                this.firstStartDiffuseColor = colorStart
                this.secondStartDiffuseColor = colorStart
                this.firstEndDiffuseColor = colorEnd
                this.secondEndDiffuseColor = colorEnd
                this.diffuseInterpolation = DecelerationInterpolation(1.25f)
            }

            // Node circle effect
            this.add {
                this.numberParticle = 1

                this.lifeTimeInFrame = 20f
                this.startEmissionFrame = 0f
                this.stopEmissionFrame = 0f

                this.positionFirstSecond(0f, -0.1f, -2f)
                this.alphaStart = 0.5f
                this.alphaEnd = 0.5f
                this.firstStartAngle = -45f
                this.secondStartAngle = -45f
                this.scaleFirstSecond(0.02f)
                this.accelerationScaleFirstSecond(0.005f, 0.006f, 0.05f)

                this.texture = textureParticle
                this.firstStartDiffuseColor = colorStart
                this.secondStartDiffuseColor = colorStart
                this.firstEndDiffuseColor = colorEnd
                this.secondEndDiffuseColor = colorEnd
                this.diffuseInterpolation = AccelerationInterpolation(1.25f)
            }
        }

        ConstraintLayout(modifier = Modifier.fillMaxSize()) {
            val (view3D, explodeButton) = this.createRefs()

            view3DComposable.Draw(modifier = Modifier.constrainAs(view3D) {
                this.width = Dimension.fillToConstraints
                this.height = Dimension.fillToConstraints
                this.topParent
                this.bottom near explodeButton.top
                this.startParent
                this.endParent
            }) {
                this@EngineParticleSwordComposable.swordPlayer =
                    this.player(particleEffectSword)
                this.scenePosition { this.z = -3f }
                this.root { this.box { this.material(materialBox) } }
            }

            Button(onClick = { this@EngineParticleSwordComposable.swordPlayer.play() },
                   modifier = Modifier.constrainAs(explodeButton) {
                       this.width = Dimension.wrapContent
                       this.height = Dimension.wrapContent
                       this.bottomParent
                       this.startParent
                       this.endParent
                   }) {
                Text(text = stringResource(id = R.string.playSword))
            }
        }
    }
}
