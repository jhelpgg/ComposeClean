package fr.jhelp.android.library.showcase.ui.activities

import androidx.activity.addCallback
import fr.jhelp.android.library.engine.view.GameActivity
import fr.jhelp.android.library.engine.view.View3D
import fr.jhelp.android.library.provider.provided
import fr.jhelp.android.library.models.shared.NavigationModel
import fr.jhelp.android.library.tasks.extensions.observedBy
import fr.jhelp.android.library.showcase.game.GameManager

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