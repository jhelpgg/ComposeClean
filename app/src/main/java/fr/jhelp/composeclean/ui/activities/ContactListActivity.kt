package fr.jhelp.composeclean.ui.activities

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import fr.jhelp.composeclean.ui.composables.ContactListActivityComposable
import fr.jhelp.composeclean.ui.theme.ComposeCleanTheme

class ContactListActivity : ComponentActivity()
{
    override fun onCreate(savedInstanceState: Bundle?)
    {
        super.onCreate(savedInstanceState)
        val contactListActivityComposable = ContactListActivityComposable()
        setContent {
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