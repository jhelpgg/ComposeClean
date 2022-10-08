package fr.jhelp.composeclean.ui.activities

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import fr.jhelp.composeclean.ui.composables.MainActivityComposable
import fr.jhelp.composeclean.ui.theme.ComposeCleanTheme

class MainActivity : ComponentActivity()
{
    override fun onCreate(savedInstanceState: Bundle?)
    {
        super.onCreate(savedInstanceState)
        val mainActivityComposable = MainActivityComposable()
        setContent {
            ComposeCleanTheme {
                // A surface container using the 'background' color from the theme
                Surface(modifier = Modifier.fillMaxSize(),
                        color = MaterialTheme.colorScheme.background) {
                    mainActivityComposable.Show()
                }
            }
        }
    }
}
