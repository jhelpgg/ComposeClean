package fr.jhelp.android.library.models.shared.dialogs

import androidx.compose.ui.Alignment

/**
 * Dialog model
 */
sealed class DialogModel
{
    /** Dialog alignment */
    var alignment: Alignment.Vertical = Alignment.CenterVertically

    /** Indicates if dialog close if user click outside its bounds */
    var closeWhenClickOutside: Boolean = false
}
