package fr.jhelp.android.library.showcase.ui.extensions

import fr.jhelp.android.library.showcase.R
import fr.jhelp.android.library.models.TextChoice

/** Give string resource associated to text choice */
val TextChoice.stringResource: Int
    get() =
        when (this)
        {
            TextChoice.CHOICE1 -> R.string.text1
            TextChoice.CHOICE2 -> R.string.text2
        }