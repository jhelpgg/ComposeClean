package fr.jhelp.android.library.models.source.text

/**
 * Source of text
 */
sealed interface TextSource
{
    /** Resolved text */
    val text: String
}
