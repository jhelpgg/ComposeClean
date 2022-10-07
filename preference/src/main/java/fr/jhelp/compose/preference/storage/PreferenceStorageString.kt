package fr.jhelp.compose.preference.storage

import android.content.SharedPreferences

object PreferenceStorageString : PreferenceStorage<String>
{
    override fun getPreferences(key: String,
                                defaultValue: String,
                                preferences: SharedPreferences): String =
        preferences.getString(key, defaultValue) ?: ""

    override fun putPreferences(key: String, value: String, editor: SharedPreferences.Editor)
    {
        editor.putString(key, value)
    }
}