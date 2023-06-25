package fr.jhelp.android.library.models.source.text

/**
 * Text build with a String
 * @property string Embed text
 */
data class TextString(val string: String) : TextSource
{
    override val text: String = this.string
}
