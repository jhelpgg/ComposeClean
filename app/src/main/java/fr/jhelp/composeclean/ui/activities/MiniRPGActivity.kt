package fr.jhelp.composeclean.ui.activities

import androidx.activity.addCallback
import fr.jhelp.compose.engine.scene.geometry.Box
import fr.jhelp.compose.engine.view.GameActivity
import fr.jhelp.compose.engine.view.View3D
import fr.jhelp.compose.provider.provided
import fr.jhelp.composeclean.models.shared.NavigationModel

/**
 * Mini RPG game sample : "Nullpart" (Nowhere)
 */
class MiniRPGActivity : GameActivity()
{
    private val navigationModel: NavigationModel by provided<NavigationModel>()

    override fun startGame(view: View3D)
    {
        val scene = view.scene3D
        scene.root.position.z = -2f
        scene.root.add(Box())
    }

    override fun createGameActivity()
    {
        this.onBackPressedDispatcher.addCallback(this, true) {
            this@MiniRPGActivity.navigationModel.back()
            this@MiniRPGActivity.finish()
        }
    }
}