package fr.jhelp.compose.text

import android.content.SharedPreferences
import androidx.annotation.StringRes

data class TextResources(@StringRes private var string: Int) : Text()
{
    override fun text(): String =
        Texts.string(this.string)

    override fun serializeContent(baseKey: String, editor: SharedPreferences.Editor)
    {
        editor.putInt(baseKey, this.string)
    }

    override fun parse(baseKey: String, preferences: SharedPreferences)
    {
        this.string = preferences.getInt(baseKey, this.string)
    }
}
