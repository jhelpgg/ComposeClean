package fr.jhelp.compose.text

import android.content.SharedPreferences
import androidx.annotation.StringRes
import java.util.Objects

class TextWithReplacement(@StringRes private var string: Int,
                          private vararg var replacements: Text) : Text()
{
    companion object
    {
        const val BASE_KEY = "_Base"
        const val REPLACEMENTS_SIZE_KEY = "_Size"
        const val REPLACEMENTS_KEY = "_Replacements_"
    }

    override fun text(): String
    {
        val arguments =
            Array<String>(this.replacements.size) { index -> this.replacements[index].text() }
        return Texts.string(this.string, *arguments)
    }

    override fun equals(other: Any?): Boolean =
        when
        {
            this === other                                                                   -> true
            null == other || other !is TextWithReplacement                                   -> false
            this.string != other.string || this.replacements.size != other.replacements.size -> false
            else                                                                             ->
            {
                var result = true

                for (index in 0 until this.replacements.size)
                {
                    if (this.replacements[index] != other.replacements[index])
                    {
                        result = false
                        break
                    }
                }

                result
            }
        }

    override fun hashCode(): Int
    {
        var hashCode = Objects.hashCode(this.string)

        for (replacement in this.replacements)
        {
            hashCode = 31 * hashCode + replacement.hashCode()
        }

        return hashCode
    }

    override fun serializeContent(baseKey: String, editor: SharedPreferences.Editor)
    {
        editor.putInt(baseKey + TextWithReplacement.BASE_KEY, this.string)
        editor.putInt(baseKey + TextWithReplacement.REPLACEMENTS_SIZE_KEY, this.replacements.size)

        for ((index, replacement) in this.replacements.withIndex())
        {
            replacement.serialize(baseKey + TextWithReplacement.REPLACEMENTS_KEY + index, editor)
        }
    }

    override fun parse(baseKey: String, preferences: SharedPreferences)
    {
        this.string = preferences.getInt(baseKey + TextWithReplacement.BASE_KEY, this.string)
        val size = preferences.getInt(baseKey + TextWithReplacement.REPLACEMENTS_SIZE_KEY, 0)

        this.replacements = Array<Text>(size) { index ->
            Text.parse(baseKey + TextWithReplacement.REPLACEMENTS_KEY + index,
                       preferences)
        }
    }
}