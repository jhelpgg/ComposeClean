package fr.jhelp.composeclean.models.shared

import fr.jhelp.compose.mutable.Mutable
import fr.jhelp.composeclean.models.TextChoice

interface MainModel
{
    fun initialize(textChoice:Mutable<TextChoice>)

    fun changeText()
}