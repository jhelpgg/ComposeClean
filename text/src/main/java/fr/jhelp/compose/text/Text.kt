package fr.jhelp.compose.text

import android.content.SharedPreferences

sealed class Text
{
    companion object
    {
        const val CLASS_NAME_KEY = "_ClassName"
        const val CONTENT_NAME_KEY = "_Content"

        fun parse(baseKey: String, preferences: SharedPreferences): Text
        {
            val className = preferences.getString(baseKey + Text.CLASS_NAME_KEY, "")!!

            val text = when (className)
            {
                TextResources::class.java.name       -> TextResources(0)
                TextString::class.java.name          -> TextString("")
                TextWithReplacement::class.java.name -> TextWithReplacement(0)
                else                                 -> throw IllegalArgumentException("No text defined in shared preference key : $baseKey")
            }

            text.parse(baseKey + Text.CONTENT_NAME_KEY, preferences)
            return text
        }
    }

    abstract fun text(): String

    final override fun toString(): String =
        "${this.javaClass.name} : ${this.text()}"

    fun serialize(baseKey: String, editor: SharedPreferences.Editor)
    {
        editor.putString(baseKey + Text.CLASS_NAME_KEY, this.javaClass.name)
        this.serializeContent(baseKey + Text.CONTENT_NAME_KEY, editor)
    }

    protected abstract fun serializeContent(baseKey: String, editor: SharedPreferences.Editor)

    protected abstract fun parse(baseKey: String, preferences: SharedPreferences)
}
