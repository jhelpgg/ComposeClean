package fr.jhelp.android.library.models.presentation

/**
 * Presentation type of a generic element
 */
val Presentation.type: PresentationType
    get() =
        when (this)
        {
            is PresentationSeparator -> this.presentationType
            is PresentationElement   -> this.presentationType
        }