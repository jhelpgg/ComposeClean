package fr.jhelp.composeclean.game

import android.util.Log
import fr.jhelp.compose.engine.scene.Scene3D
import fr.jhelp.compose.engine.scene.geometry.Sphere
import fr.jhelp.compose.engine.view.overlay.OverlayScreen
import fr.jhelp.compose.engine.view.overlay.OverlayScreenUI
import fr.jhelp.compose.engine.view.overlay.component.OverlayText
import fr.jhelp.compose.engine.view.overlay.component.layout.OverlayTableLayout
import fr.jhelp.compose.engine.view.overlay.component.layout.OverlayTableLayoutConstraint

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
        tableLayout.add(OverlayText("Hello to every body !", factor = 6), OverlayTableLayoutConstraint(0, 0, 30))
        tableLayout.add(OverlayText("World !", factor = 6), OverlayTableLayoutConstraint(0, 1,10))
        tableLayout.add(OverlayText("Side", factor = 6), OverlayTableLayoutConstraint(10, 1,10))
        this.overlayScreen.mainComponent = tableLayout
        this.scene.root.position.z = -3f
        this.scene.root.add(this.sphere)
    }

    override fun detached()
    {
    }
}