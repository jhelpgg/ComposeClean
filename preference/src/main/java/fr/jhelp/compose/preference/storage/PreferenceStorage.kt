package fr.jhelp.compose.preference.storage

import android.content.SharedPreferences

interface PreferenceStorage<T : Any>
{
    fun getPreferences(key: String, defaultValue: T, preferences: SharedPreferences): T

    fun putPreferences(key: String, value: T, editor: SharedPreferences.Editor)
}