package fr.jhelp.composeclean.ui.extensions

import fr.jhelp.composeclean.R
import fr.jhelp.composeclean.models.TextChoice

val TextChoice.stringResource: Int
    get() =
        when (this)
        {
            TextChoice.CHOICE1 -> R.string.text1
            TextChoice.CHOICE2 -> R.string.text2
        }