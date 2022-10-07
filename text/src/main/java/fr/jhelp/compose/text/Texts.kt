package fr.jhelp.compose.text

import android.content.Context
import androidx.annotation.StringRes
import fr.jhelp.compose.provider.isProvided
import fr.jhelp.compose.provider.provided

internal object Texts
{
    private val applicationContext: Context by provided<Context>()

    fun string(@StringRes string: Int): String =
        if (isProvided<Context>())
        {
            this.applicationContext.resources.getString(string)
        }
        else
        {
            "No applicationContext provided, so can't resolve resource $string"
        }

    fun string(@StringRes string: Int, vararg arguments: String): String =
        if (isProvided<Context>())
        {
            this.applicationContext.resources.getString(string, *arguments)
        }
        else
        {
            "No applicationContext provided, so can't resolve resource $string"
        }
}
