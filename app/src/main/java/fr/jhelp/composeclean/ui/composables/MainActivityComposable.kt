package fr.jhelp.composeclean.ui.composables

import android.content.Context
import android.content.Intent
import androidx.compose.foundation.layout.Column
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import fr.jhelp.compose.mutable
import fr.jhelp.compose.provider.provideSingle
import fr.jhelp.compose.provider.provided
import fr.jhelp.composeclean.R
import fr.jhelp.composeclean.models.TextChoice
import fr.jhelp.composeclean.models.shared.MainModel
import fr.jhelp.composeclean.models.shared.preview.MainModelPreview
import fr.jhelp.composeclean.ui.activities.ContactListActivity
import fr.jhelp.composeclean.ui.extensions.stringResource
import fr.jhelp.composeclean.ui.theme.ComposeCleanTheme

/**
 * Main activity composable
 */
class MainActivityComposable
{
    private val applicationContext: Context by provided<Context>()
    private val mainModel: MainModel by provided<MainModel>()

    @Composable
    fun Show()
    {
        // Create holders and initialize the model
        val textChoice = mutable<TextChoice>(TextChoice.CHOICE1)
        this.mainModel.initialize(textChoice)

        Column {
            Text(text = stringResource(textChoice.get().stringResource))
            Button(onClick = { this@MainActivityComposable.mainModel.changeText() }) {
                Text(text = stringResource(R.string.buttonChangeText))
            }
            Button(onClick = {
                val intent = Intent(this@MainActivityComposable.applicationContext,
                                    ContactListActivity::class.java)
                intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK
                this@MainActivityComposable.applicationContext.startActivity(intent)
            }) {
                Text(text = stringResource(R.string.buttonContacts))
            }
        }
    }

    @Preview(showBackground = true)
    @Composable
    fun Preview()
    {
        provideSingle<MainModel> { MainModelPreview }
        ComposeCleanTheme {
            this.Show()
        }
    }
}