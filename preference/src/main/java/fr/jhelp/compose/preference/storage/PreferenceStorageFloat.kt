package fr.jhelp.compose.preference.storage

import android.content.SharedPreferences

object PreferenceStorageFloat : PreferenceStorage<Float>
{
    override fun getPreferences(key: String,
                                defaultValue: Float,
                                preferences: SharedPreferences): Float =
        preferences.getFloat(key, defaultValue)

    override fun putPreferences(key: String, value: Float, editor: SharedPreferences.Editor)
    {
        editor.putFloat(key, value)
    }
}