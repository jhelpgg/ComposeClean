package fr.jhelp.composeclean.ui.composables

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
import fr.jhelp.android.library.engine.scene.WHITE
import fr.jhelp.compose.ui.engine.View3DComposable
import fr.jhelp.composeclean.R

/**
 * Show explosion particle effect
 */
class EngineParticleExplodeComposable
{
    private lateinit var explosionPlayer: ParticlePlayer

    /**
     * Show explosion particle effect
     */
    @Composable
    fun Show()
    {
        val view3DComposable = View3DComposable()

        val (textureBox, textureFire, textureSmoke) = textureReferences()
        texture(textureBox, R.drawable.floor)
        texture(textureFire, R.drawable.fire_0)
        texture(textureSmoke, R.drawable.smoke)

        val materialBox = materialReference()
        material(materialBox) {
            this.diffuse = WHITE
            this.textureReference = textureBox
        }

        val particleEffectExplosion = particleEffectReference()
        particleEffect(particleEffectExplosion) {
            // Node fire
            this.add {
                this.numberParticle = 1

                this.lifeTimeInFrame = 20f
                this.startEmissionFrame = 0f
                this.stopEmissionFrame = 0f

                this.positionFirstSecond(0f, 0f, -2f)

                this.texture = textureFire
                this.constantDiffuse(WHITE)

                this.scaleFirstSecond(0.02f)
                this.accelerationScaleFirstSecond(0.007f)
            }

            // Node fire disappear
            this.add {
                this.numberParticle = 1

                this.lifeTimeInFrame = 20f
                this.startEmissionFrame = 19f
                this.stopEmissionFrame = 19f

                this.positionFirstSecond(0f, 0f, -2f)

                this.texture = textureFire
                this.constantDiffuse(WHITE)

                this.scaleFirstSecond(2f)

                this.alphaStart = 1f
                this.alphaEnd = 0f
                this.alphaInterpolation = DecelerationInterpolation(2f)
            }

            // Node smoke
            this.add {
                this.numberParticle = 4

                this.lifeTimeInFrame = 40f
                this.startEmissionFrame = 20f
                this.stopEmissionFrame = 20f

                this.positionFirstSecond(0f, -0.1f, -2f)
                this.firstSpeedDirectionX = -0.01f
                this.firstSpeedDirectionY = 0.04f
                this.firstSpeedDirectionZ = 0f
                this.secondSpeedDirectionX = 0.01f
                this.secondSpeedDirectionY = 0.04f
                this.secondSpeedDirectionZ = 0f

                this.texture = textureSmoke
                this.constantDiffuse(WHITE)
                this.alphaStart = 1f
                this.alphaEnd = 0f
                this.alphaInterpolation = AccelerationInterpolation(2f)
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
                this@EngineParticleExplodeComposable.explosionPlayer =
                    this.player(particleEffectExplosion)
                this.scenePosition { this.z = -3f }
                this.root { this.box { this.material(materialBox) } }
            }

            Button(onClick = { this@EngineParticleExplodeComposable.explosionPlayer.play() },
                   modifier = Modifier.constrainAs(explodeButton) {
                       this.width = Dimension.wrapContent
                       this.height = Dimension.wrapContent
                       this.bottomParent
                       this.startParent
                       this.endParent
                   }) {
                Text(text = stringResource(id = R.string.playExplosion))
            }
        }
    }
}
