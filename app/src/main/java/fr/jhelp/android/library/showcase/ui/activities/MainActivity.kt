package fr.jhelp.android.library.showcase.ui.activities

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.addCallback
import androidx.activity.compose.setContent
import androidx.annotation.MainThread
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import fr.jhelp.android.library.provider.provided
import fr.jhelp.android.library.models.shared.NavigationModel
import fr.jhelp.android.library.showcase.ui.composables.MainActivityComposable
import fr.jhelp.android.library.showcase.ui.theme.ComposeCleanTheme

/**
 * The main activity
 */
class MainActivity : ComponentActivity()
{
    private val navigationModel: NavigationModel by provided<NavigationModel>()

    /**
     * Called on activity creation
     */
    @MainThread
    override fun onCreate(savedInstanceState: Bundle?)
    {
        super.onCreate(savedInstanceState)
        val mainActivityComposable = MainActivityComposable()
        this.setContent {
            ComposeCleanTheme {
                // A surface container using the 'background' color from the theme
                Surface(modifier = Modifier.fillMaxSize(),
                        color = MaterialTheme.colorScheme.background) {
                    mainActivityComposable.Show()
                }
            }
        }

        this.onBackPressedDispatcher.addCallback(this, true) {
            if (this@MainActivity.navigationModel.back())
            {
                this@MainActivity.finish()
            }
        }
    }

    /**
     * Called when application is paused
     */
    override fun onPause()
    {
        this.navigationModel.pause()
        super.onPause()
    }

    /**
     * Called when application resumed
     */
    override fun onResume()
    {
        super.onResume()
        this.navigationModel.resume()
    }
}
