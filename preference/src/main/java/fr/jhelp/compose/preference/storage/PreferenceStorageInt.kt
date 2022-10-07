package fr.jhelp.compose.preference.storage

import android.content.SharedPreferences

object PreferenceStorageInt : PreferenceStorage<Int>
{
    override fun getPreferences(key: String,
                                defaultValue: Int,
                                preferences: SharedPreferences): Int =
        preferences.getInt(key, defaultValue)

    override fun putPreferences(key: String, value: Int, editor: SharedPreferences.Editor)
    {
        editor.putInt(key, value)
    }
}