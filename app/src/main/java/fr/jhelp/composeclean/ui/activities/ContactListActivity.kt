package fr.jhelp.composeclean.ui.activities

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.annotation.MainThread
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import fr.jhelp.composeclean.ui.composables.ContactListActivityComposable
import fr.jhelp.composeclean.ui.theme.ComposeCleanTheme

/**
 * Activity with contact list sample
 */
class ContactListActivity : ComponentActivity()
{
    /**
     * Called on Activity creation
     */
    @MainThread
    override fun onCreate(savedInstanceState: Bundle?)
    {
        super.onCreate(savedInstanceState)
        val contactListActivityComposable = ContactListActivityComposable()
        this.setContent {
            ComposeCleanTheme {
                // A surface container using the 'background' color from the theme
                Surface(modifier = Modifier.fillMaxSize(),
                        color = MaterialTheme.colorScheme.background) {
                    contactListActivityComposable.Show()
                }
            }
        }
    }
}