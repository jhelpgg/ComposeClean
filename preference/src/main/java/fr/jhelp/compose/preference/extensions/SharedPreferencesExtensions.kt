package fr.jhelp.compose.preference.extensions

import android.content.SharedPreferences
import fr.jhelp.compose.text.Text

fun SharedPreferences.getText(key: String, defaultValue: Text): Text =
    try
    {
        Text.parse(key, this)
    }
    catch (_: Exception)
    {
        defaultValue
    }

fun SharedPreferences.getDouble(key: String, defaultValue: Double): Double =
    Double.fromBits(this.getLong(key, defaultValue.toBits()))
