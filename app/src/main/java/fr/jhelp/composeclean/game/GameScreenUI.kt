package fr.jhelp.composeclean.game

import android.util.Log
import fr.jhelp.compose.engine.resources.image.ImageSourceDrawable
import fr.jhelp.compose.engine.scene.Scene3D
import fr.jhelp.compose.engine.scene.geometry.Sphere
import fr.jhelp.compose.engine.view.overlay.OverlayScreen
import fr.jhelp.compose.engine.view.overlay.OverlayScreenUI
import fr.jhelp.compose.engine.view.overlay.component.OverlayButtonGroup
import fr.jhelp.compose.engine.view.overlay.component.OverlayButtonImage
import fr.jhelp.compose.engine.view.overlay.component.layout.OverlayTableLayout
import fr.jhelp.compose.engine.view.overlay.component.layout.OverlayTableLayoutConstraint
import fr.jhelp.composeclean.R
import fr.jhelp.tasks.extensions.observedBy

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

        val button1 = OverlayButtonImage(ImageSourceDrawable(R.drawable.default_screen))
        val button2 = OverlayButtonImage(ImageSourceDrawable(R.drawable.fire_0))
        val button3 = OverlayButtonImage(ImageSourceDrawable(R.drawable.floor))
        val group = OverlayButtonGroup(0, button1, button2, button3)
        group.selected.observedBy { selected -> Log.d("REMOVE_ME", "Selected = $selected") }
        tableLayout.add(button1,
                        OverlayTableLayoutConstraint(0, 0))
        tableLayout.add(button2,
                        OverlayTableLayoutConstraint(1, 0))
        tableLayout.add(button3,
                        OverlayTableLayoutConstraint(2, 0))
        this.overlayScreen.mainComponent = tableLayout
        this.scene.root.position.z = -3f
        this.scene.root.add(this.sphere)
    }

    override fun detached()
    {
    }
}