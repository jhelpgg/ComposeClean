package fr.jhelp.composeclean.game

import fr.jhelp.compose.engine.resources.image.ImageSourceDrawable
import fr.jhelp.compose.engine.scene.Scene3D
import fr.jhelp.compose.engine.scene.geometry.Sphere
import fr.jhelp.compose.engine.view.overlay.OverlayScreen
import fr.jhelp.compose.engine.view.overlay.OverlayScreenUI
import fr.jhelp.compose.engine.view.overlay.component.OverlayButtonImage
import fr.jhelp.compose.engine.view.overlay.component.OverlayText
import fr.jhelp.compose.engine.view.overlay.component.layout.OverlayTableLayout
import fr.jhelp.compose.engine.view.overlay.component.layout.OverlayTableLayoutConstraint
import fr.jhelp.composeclean.R
import fr.jhelp.composeclean.game.ui.BUTTON_FIRE
import fr.jhelp.composeclean.game.ui.BUTTON_ROCK
import fr.jhelp.composeclean.game.ui.BUTTON_SWORD
import fr.jhelp.composeclean.game.ui.BUTTON_TWO_SWORDS
import fr.jhelp.composeclean.game.ui.BUTTON_WAND
import fr.jhelp.composeclean.game.ui.BUTTON_WATER
import fr.jhelp.composeclean.game.ui.BUTTON_WIND

/**
 * Game screen with over UI
 */
class GameScreenUI(scene3D: Scene3D) : GameScreen(scene3D)
{
    private val sphere = Sphere()
    private val overlayScreen = OverlayScreenUI()
    override val overlay: OverlayScreen = this.overlayScreen

    override fun attached()
    {
        val tableLayout = OverlayTableLayout()

        tableLayout.add(BUTTON_FIRE,
                        OverlayTableLayoutConstraint(0, 0))
        tableLayout.add(BUTTON_ROCK,
                        OverlayTableLayoutConstraint(1, 0))
        tableLayout.add(BUTTON_SWORD,
                        OverlayTableLayoutConstraint(2, 0))
        tableLayout.add(BUTTON_TWO_SWORDS,
                        OverlayTableLayoutConstraint(3, 0))
        tableLayout.add(BUTTON_WAND,
                        OverlayTableLayoutConstraint(0, 1))
        tableLayout.add(BUTTON_WATER,
                        OverlayTableLayoutConstraint(1, 1))
        tableLayout.add(BUTTON_WIND,
                        OverlayTableLayoutConstraint(2, 1))
        val toggle = OverlayButtonImage(ImageSourceDrawable(R.drawable.floor))
        toggle.toggleBehavior = true
        toggle.selectListener = { selected ->
            BUTTON_FIRE.enabled = !selected
            BUTTON_ROCK.enabled = !selected
            BUTTON_SWORD.enabled = !selected
            BUTTON_TWO_SWORDS.enabled = !selected
            BUTTON_WAND.enabled = !selected
            BUTTON_WATER.enabled = !selected
            BUTTON_WIND.enabled = !selected
        }
        tableLayout.add(toggle,
                        OverlayTableLayoutConstraint(3, 1))

        tableLayout.add(OverlayText(""),
                        OverlayTableLayoutConstraint(7, 3))

        this.overlayScreen.mainComponent = tableLayout
        this.scene.root.position.z = -3f
        this.scene.root.add(this.sphere)
    }

    override fun detached()
    {
    }
}