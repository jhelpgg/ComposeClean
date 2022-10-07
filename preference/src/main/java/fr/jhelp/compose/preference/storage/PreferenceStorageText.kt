package fr.jhelp.compose.preference.storage

import android.content.SharedPreferences
import fr.jhelp.compose.preference.extensions.getText
import fr.jhelp.compose.preference.extensions.putText
import fr.jhelp.compose.text.Text

object PreferenceStorageText : PreferenceStorage<Text>
{
    override fun getPreferences(key: String,
                                defaultValue: Text,
                                preferences: SharedPreferences): Text =
        preferences.getText(key, defaultValue)

    override fun putPreferences(key: String, value: Text, editor: SharedPreferences.Editor)
    {
        editor.putText(key, value)
    }
}