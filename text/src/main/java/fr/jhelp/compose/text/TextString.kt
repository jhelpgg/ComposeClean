package fr.jhelp.compose.text

import android.content.SharedPreferences

data class TextString(private var string: String) : Text()
{
    override fun text(): String =
        this.string

    override fun serializeContent(baseKey: String, editor: SharedPreferences.Editor)
    {
        editor.putString(baseKey, this.string)
    }

    override fun parse(baseKey: String, preferences: SharedPreferences)
    {
        this.string = preferences.getString(baseKey, this.string)!!
    }
}
