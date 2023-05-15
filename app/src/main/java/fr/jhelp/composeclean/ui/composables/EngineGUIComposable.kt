package fr.jhelp.composeclean.ui.composables

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import fr.jhelp.compose.engine.extensions.position
import fr.jhelp.compose.engine.scene.BLUE
import fr.jhelp.compose.engine.scene.GREEN
import fr.jhelp.compose.engine.scene.RED
import fr.jhelp.compose.engine.scene.geometry.dice.Dice
import fr.jhelp.compose.engine.view.gui.ButtonGUI
import fr.jhelp.compose.engine.view.gui.GUI
import fr.jhelp.compose.engine.view.touch.View3DTouchOverSensitive
import fr.jhelp.compose.math.COLOR_BLUE_0300
import fr.jhelp.compose.math.COLOR_BLUE_0700
import fr.jhelp.compose.math.COLOR_GREEN_0300
import fr.jhelp.compose.math.COLOR_GREEN_0700
import fr.jhelp.compose.math.COLOR_RED_0300
import fr.jhelp.compose.math.COLOR_RED_0700
import fr.jhelp.compose.ui.engine.View3DComposable

/**
 * Show the engine 3d with a little GUI over
 */
class EngineGUIComposable
{
    /**
     * Show the engine 3d with a little GUI over
     */
    @Composable
    fun Show()
    {
        val dice = Dice()
        val gui = GUI()

        val redButton = ButtonGUI(COLOR_RED_0300, COLOR_RED_0700)
        redButton.click = { dice.color(RED) }
        redButton.x = 16
        redButton.y = 16
        redButton.width = 64
        redButton.height = 64
        gui.add(redButton)

        val greenButton = ButtonGUI(COLOR_GREEN_0300, COLOR_GREEN_0700)
        greenButton.click = { dice.color(GREEN) }
        greenButton.x = 16
        greenButton.y = 128
        greenButton.width = 64
        greenButton.height = 64
        gui.add(greenButton)

        val blueButton = ButtonGUI(COLOR_BLUE_0300, COLOR_BLUE_0700)
        blueButton.click = { dice.color(BLUE) }
        blueButton.x = 128
        blueButton.y = 128
        blueButton.width = 64
        blueButton.height = 64
        gui.add(blueButton)

        val view3D = View3DComposable()
        view3D.view3DTouchAction = View3DTouchOverSensitive(gui)
        view3D.Draw(modifier = Modifier.fillMaxSize()) {
            this.scenePosition { this.z = -2f }
            this.root {
                this.node { this.add(dice) }
            }
            this.scene3D.play { frame ->
                dice.position {
                    this.angleX = frame / 2f
                    this.angleY = frame / 32f
                }
                true
            }
        }
    }
}