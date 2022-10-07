package fr.jhelp.compose.preference.extensions

import android.content.SharedPreferences
import fr.jhelp.compose.text.Text

fun SharedPreferences.Editor.putText(key: String, text: Text)
{
    text.serialize(key, this)
}

fun SharedPreferences.Editor.putDouble(key: String, value: Double)
{
    this.putLong(key, value.toBits())
}
