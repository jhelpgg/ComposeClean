package fr.jhelp.compose.preference.storage

import android.content.SharedPreferences

object PreferenceStorageLong : PreferenceStorage<Long>
{
    override fun getPreferences(key: String,
                                defaultValue: Long,
                                preferences: SharedPreferences): Long =
        preferences.getLong(key, defaultValue)

    override fun putPreferences(key: String, value: Long, editor: SharedPreferences.Editor)
    {
        editor.putLong(key, value)
    }
}