package fr.jhelp.android.library.showcase.game

import fr.jhelp.android.library.engine.scene.Scene3D
import fr.jhelp.android.library.engine.view.overlay.OverlayScreen
import fr.jhelp.android.library.showcase.game.event.NextScreen
import fr.jhelp.android.library.showcase.game.event.ScreenEvent
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

/**
 * Manage the game
 */
class GameManager(private val scene: Scene3D) {
    private val gameScreenIntroductionScene = GameScreenIntroductionScene(this.scene)
    private val gameScreenMove = GameScreenMove(this.scene)
    private val gameScreenUI = GameScreenUI(this.scene)
    private var currentScreen: GameScreen = this.gameScreenIntroductionScene
    private val overlayScreenMutable = MutableStateFlow<OverlayScreen>(this.currentScreen.overlay)
    private var job: Job? = null

    /** Current overlay screen */
    val overlayScreen: StateFlow<OverlayScreen> = this.overlayScreenMutable.asStateFlow()

    /**
     * Start the manager
     */
    fun start() {
        this.currentScreen = this.gameScreenIntroductionScene
        this.scene.root.removeAllChildren()

        this.job = CoroutineScope(Dispatchers.Default).launch {
            this@GameManager.currentScreen.eventsFlow.collect(this@GameManager::screenEvent)
        }
        this.currentScreen.attached()
    }

    fun stop() {
        this.job?.cancel()
        this.currentScreen.detached()
        this.scene.root.removeAllChildren()
    }

    private fun changeScreen(gameScreen: GameScreen) {
        this.job?.cancel()
        this.currentScreen.detached()
        this.scene.root.removeAllChildren()
        this.currentScreen = gameScreen
        this.currentScreen.attached()
        this.overlayScreenMutable.value = this.currentScreen.overlay
        this.job = CoroutineScope(Dispatchers.Default).launch {
            this@GameManager.currentScreen.eventsFlow.collect(this@GameManager::screenEvent)
        }
    }

    private fun screenEvent(screenEvent: ScreenEvent) {
        when (screenEvent) {
            NextScreen -> this.changeScreen(this.gameScreenMove)
        }
    }
}
