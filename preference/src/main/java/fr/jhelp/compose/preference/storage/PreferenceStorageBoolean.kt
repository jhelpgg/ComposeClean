package fr.jhelp.compose.preference.storage

import android.content.SharedPreferences

object PreferenceStorageBoolean : PreferenceStorage<Boolean>
{
    override fun getPreferences(key: String,
                                defaultValue: Boolean,
                                preferences: SharedPreferences): Boolean =
        preferences.getBoolean(key, defaultValue)

    override fun putPreferences(key: String, value: Boolean, editor: SharedPreferences.Editor)
    {
        editor.putBoolean(key, value)
    }
}