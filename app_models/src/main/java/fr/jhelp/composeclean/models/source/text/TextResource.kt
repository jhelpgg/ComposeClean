package fr.jhelp.composeclean.models.source.text

import android.content.Context
import androidx.annotation.StringRes
import fr.jhelp.compose.provider.provided

/**
 * Text from String resource
 * @property resources String resource to use
 */
data class TextResource(@StringRes val resources: Int) : TextSource
{
    private val context: Context by provided<Context>()

    override val text: String
        get() = this.context.getString(this.resources)
}
