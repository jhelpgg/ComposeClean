package fr.jhelp.android.library.showcase.ui.composables

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import fr.jhelp.android.library.engine.dsl.animationNode
import fr.jhelp.android.library.engine.dsl.animationNodeReferences
import fr.jhelp.android.library.engine.dsl.nodeReferences
import fr.jhelp.android.library.engine.extensions.children
import fr.jhelp.android.library.engine.extensions.position
import fr.jhelp.android.library.engine.scene.BLACK
import fr.jhelp.android.library.engine.scene.LIGHT_BLUE
import fr.jhelp.android.library.engine.scene.LIGHT_GREY
import fr.jhelp.android.library.engine.scene.YELLOW
import fr.jhelp.android.library.showcase.ui.engine.View3DComposable

/**
 * Show the simplified solar system
 */
class EngineSolarSystemComposable
{
    companion object
    {
        private const val NUMBER_TURN = 32
    }

    /**
     * Show the simplified solar system
     */
    @Composable
    fun Show()
    {
        val view3D = View3DComposable()
        view3D.Draw(modifier = Modifier.fillMaxSize()) {
            this.backgroundColor = BLACK
            val (sun, earth, moon) = nodeReferences()
            this.scenePosition { this.z = -5f }
            this.root {
                this.sphere(sun, slice = 32, slack = 32) {
                    this.material.diffuse = YELLOW

                    this.children {
                        this.sphere(earth, slice = 32, slack = 32) {
                            this.material.diffuse = LIGHT_BLUE
                            this.position {
                                this.scale(0.4f)
                                this.y = 2f
                            }

                            this.children {
                                this.sphere(moon, slice = 32, slack = 32) {
                                    this.material.diffuse = LIGHT_GREY
                                    this.position {
                                        this.scale(0.4f)
                                        this.y = 2f
                                    }
                                }
                            }
                        }
                    }
                }
            }

            val (aroundSun, aroundEarth) = animationNodeReferences(sun, earth)
            animationNode(aroundSun) {
                this.atTime(4096 * EngineSolarSystemComposable.NUMBER_TURN) {
                    this.angleZ = 360f * EngineSolarSystemComposable.NUMBER_TURN
                }
            }
            animationNode(aroundEarth) {
                this.atTime(4096 * EngineSolarSystemComposable.NUMBER_TURN) {
                    this.angleZ = 360f * EngineSolarSystemComposable.NUMBER_TURN * 3.456789f
                    this.y = 2f
                    this.scale(0.4f)
                }
            }

            this.player(aroundSun).play()
            this.player(aroundEarth).play()
        }
    }
}