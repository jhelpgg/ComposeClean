package fr.jhelp.composeclean.ui.activities

import androidx.activity.addCallback
import fr.jhelp.compose.engine.view.GameActivity
import fr.jhelp.compose.engine.view.View3D
import fr.jhelp.compose.provider.provided
import fr.jhelp.composeclean.game.GameManager
import fr.jhelp.composeclean.models.shared.NavigationModel
import fr.jhelp.tasks.extensions.observedBy

/**
 * Mini RPG game sample
 */
class MiniRPGActivity : GameActivity()
{
    private val navigationModel: NavigationModel by provided<NavigationModel>()
    private lateinit var gameManager: GameManager

    override fun startGame(view: View3D)
    {
        this.gameManager = GameManager(view.scene3D)
        this.gameManager.start()
        this.gameManager.overlayScreen.observedBy(action = this::screen)
    }

    override fun createGameActivity()
    {
        this.onBackPressedDispatcher.addCallback(this, true) {
            this@MiniRPGActivity.navigationModel.back()
            this@MiniRPGActivity.finish()
        }
    }
}