package fr.jhelp.android.library.showcase.ui.composables

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.Dimension
import fr.jhelp.android.library.animations.interpolation.DecelerationInterpolation
import fr.jhelp.android.library.constraint.extensions.bottomParent
import fr.jhelp.android.library.constraint.extensions.endParent
import fr.jhelp.android.library.constraint.extensions.near
import fr.jhelp.android.library.constraint.extensions.startParent
import fr.jhelp.android.library.constraint.extensions.topParent
import fr.jhelp.android.library.engine.dsl.animation.effect.ParticlePlayer
import fr.jhelp.android.library.engine.dsl.particleEffect
import fr.jhelp.android.library.engine.dsl.particleEffectReference
import fr.jhelp.android.library.engine.dsl.texture
import fr.jhelp.android.library.engine.dsl.textureReference
import fr.jhelp.android.library.engine.scene.BLACK
import fr.jhelp.android.library.engine.scene.DARK_CYAN
import fr.jhelp.android.library.engine.scene.DARK_GREEN
import fr.jhelp.android.library.engine.scene.LIGHT_GREEN
import fr.jhelp.android.library.engine.scene.LIGHT_RED
import fr.jhelp.android.library.engine.scene.WHITE
import fr.jhelp.android.library.showcase.R
import fr.jhelp.android.library.showcase.ui.engine.View3DComposable

/**
 * Show firework particle effect
 */
class EngineParticleFireworkComposable
{
    private lateinit var fireworkPlayer: ParticlePlayer

    /**
     * Show firework particle effect
     */
    @Composable
    fun Show()
    {
        val view3DComposable = View3DComposable()
        val textureParticle = textureReference()
        texture(textureParticle, R.drawable.particle_clear_hard)

        val particleEffectFirework = particleEffectReference()
        particleEffect(particleEffectFirework) {
            // Node for moment climb in sky
            this.add {
                this.numberParticle = 1

                this.lifeTimeInFrame = 20f
                this.startEmissionFrame = 0f
                this.stopEmissionFrame = 0f

                this.positionFirstSecond(0f, -1f, -2f)

                this.texture = textureParticle
                this.constantDiffuse(WHITE)

                this.scaleFirstSecond(0.2f)
                this.speedFirstSecond(0f, 0.1f, 0f)
            }

            // Node for explode firework
            this.add {
                this.numberParticle = 100

                this.lifeTimeInFrame = 20f
                // We wait climb sky almost finished to start the emission
                this.startEmissionFrame = 19f
                this.stopEmissionFrame = 19f

                this.positionFirstSecond(0f, 1f, -2f)

                this.firstSpeedDirectionX = -0.05f
                this.firstSpeedDirectionY = -0.05f
                this.firstSpeedDirectionZ = 0f

                this.secondSpeedDirectionX = 0.05f
                this.secondSpeedDirectionY = 0.05f
                this.secondSpeedDirectionZ = 0f

                this.texture = textureParticle
                this.firstStartDiffuseColor = LIGHT_RED
                this.secondStartDiffuseColor = LIGHT_GREEN
                this.firstEndDiffuseColor = DARK_CYAN
                this.secondEndDiffuseColor = DARK_GREEN

                this.scaleFirstSecond(0.1f)

                this.alphaStart = 1f
                this.alphaEnd = 0f
                this.alphaInterpolation = DecelerationInterpolation(2f)
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
                this.backgroundColor = BLACK
                this@EngineParticleFireworkComposable.fireworkPlayer =
                    this.player(particleEffectFirework)
                this.scenePosition { this.z = -3f }
            }

            Button(onClick = { this@EngineParticleFireworkComposable.fireworkPlayer.play() },
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
