package fr.jhelp.android.library.showcase.game

import fr.jhelp.android.library.engine.scene.Scene3D
import fr.jhelp.android.library.engine.view.overlay.OverlayScreen
import fr.jhelp.android.library.showcase.game.event.ScreenEvent
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch

/**
 * A game screen type
 * @property scene : Scene where draw
 */
abstract class GameScreen(protected val scene: Scene3D) {
    private val eventsMutableFlow = MutableSharedFlow<ScreenEvent>()
    val eventsFlow = this.eventsMutableFlow.asSharedFlow()

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

    protected fun publish(screenEvent: ScreenEvent) {
        CoroutineScope(Dispatchers.Default).launch { this@GameScreen.eventsMutableFlow.emit(screenEvent) }
    }
}

