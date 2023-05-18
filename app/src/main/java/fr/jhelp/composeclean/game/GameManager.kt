package fr.jhelp.composeclean.game

import fr.jhelp.compose.engine.scene.Scene3D
import fr.jhelp.compose.engine.view.overlay.OverlayScreen
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

/**
 * Manage the game
 */
class GameManager(private val scene: Scene3D)
{
    private val gameScreenMove = GameScreenUI(this.scene)
    private var currentScreen: GameScreen = this.gameScreenMove
    private val overlayScreenMutable = MutableStateFlow<OverlayScreen>(this.currentScreen.overlay)

    /** Current overlay screen */
    val overlayScreen: StateFlow<OverlayScreen> = this.overlayScreenMutable.asStateFlow()

    /**
     * Start the manager
     */
    fun start()
    {
        this.currentScreen.attached()
    }

    private fun changeScreen(gameScreen: GameScreen)
    {
        this.currentScreen.detached()
        this.currentScreen = gameScreen
        this.currentScreen.attached()
        this.overlayScreenMutable.value = this.currentScreen.overlay
    }
}
