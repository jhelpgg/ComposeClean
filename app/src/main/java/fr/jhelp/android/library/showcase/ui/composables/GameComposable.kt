package fr.jhelp.android.library.showcase.ui.composables

import android.content.Context
import android.content.Intent
import androidx.compose.runtime.Composable
import fr.jhelp.android.library.provider.provided
import fr.jhelp.android.library.showcase.ui.activities.MiniRPGActivity

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