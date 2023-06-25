package fr.jhelp.android.library.models.shared.dialogs

import androidx.annotation.StringRes
import fr.jhelp.android.library.models.R

/**
 * Dialog messages option buttons
 * @property string : String resource
 */
enum class DialogMessageOptionButton(@StringRes val string: Int)
{
    /** OK button */
    OK(R.string.ok),

    /** Cancel button */
    CANCEL(R.string.cancel),

    /** Yes button */
    YES(R.string.yes),

    /** No button */
    NO(R.string.no)
}
