package fr.jhelp.composeclean.ui.composables

import android.content.Context
import android.content.Intent
import androidx.compose.runtime.Composable
import fr.jhelp.compose.provider.provided
import fr.jhelp.composeclean.ui.activities.MiniRPGActivity

/**
 * Launch the game
 */
class GameComposable
{
    private val applicationContext: Context by provided<Context>()

    /**
     * Launch the game
     */
    @Composable
    fun Show()
    {
        val intent = Intent(this.applicationContext, MiniRPGActivity::class.java)
        intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK
        this.applicationContext.startActivity(intent)
    }
}