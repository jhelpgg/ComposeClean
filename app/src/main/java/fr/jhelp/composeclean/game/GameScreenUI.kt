package fr.jhelp.composeclean.game

import android.util.Log
import fr.jhelp.compose.engine.resources.image.ImageSourceDrawable
import fr.jhelp.compose.engine.scene.Scene3D
import fr.jhelp.compose.engine.scene.geometry.Sphere
import fr.jhelp.compose.engine.view.overlay.OverlayScreen
import fr.jhelp.compose.engine.view.overlay.OverlayScreenUI
import fr.jhelp.compose.engine.view.overlay.component.OverlayButtonImage
import fr.jhelp.compose.engine.view.overlay.component.layout.OverlayTableLayout
import fr.jhelp.compose.engine.view.overlay.component.layout.OverlayTableLayoutConstraint
import fr.jhelp.composeclean.R

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

        val buttonNormal = OverlayButtonImage(ImageSourceDrawable(R.drawable.default_screen))
        buttonNormal.click = { _, _, _ -> Log.d("REMOVE_ME", "Normal button") }

        val buttonToggle = OverlayButtonImage(ImageSourceDrawable(R.drawable.floor),
                                              toggleBehavior = true)
        buttonToggle.selectListener = { selected ->
            Log.d("REMOVE_ME", "Toggle button : selected = $selected")
            buttonNormal.enabled = !selected
        }

        tableLayout.add(buttonNormal,
                        OverlayTableLayoutConstraint(0, 0))
        tableLayout.add(buttonToggle,
                        OverlayTableLayoutConstraint(1, 0))
        this.overlayScreen.mainComponent = tableLayout
        this.scene.root.position.z = -3f
        this.scene.root.add(this.sphere)
    }

    override fun detached()
    {
    }
}