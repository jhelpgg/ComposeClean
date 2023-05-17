package fr.jhelp.composeclean.game

import fr.jhelp.compose.engine.scene.Scene3D
import fr.jhelp.compose.engine.view.overlay.OverlayScreen

/**
 * A game screen type
 * @property scene : Scene where draw
 */
abstract class GameScreen(protected val scene: Scene3D)
{
    /**
     * Overlay screen to use
     */
    abstract val overlay: OverlayScreen

    /**
     * Called to pu the screen is attached and activate
     */
    abstract fun attached()

    /**
     * Call when screen is detach and deactivate
     */
    abstract fun detached()
}

