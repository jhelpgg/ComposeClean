package fr.jhelp.compose.preference.storage

import android.content.SharedPreferences
import fr.jhelp.compose.preference.extensions.getDouble
import fr.jhelp.compose.preference.extensions.putDouble

object PreferenceStorageDouble : PreferenceStorage<Double>
{
    override fun getPreferences(key: String,
                                defaultValue: Double,
                                preferences: SharedPreferences): Double =
        preferences.getDouble(key, defaultValue)

    override fun putPreferences(key: String, value: Double, editor: SharedPreferences.Editor)
    {
        editor.putDouble(key, value)
    }
}