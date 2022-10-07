package fr.jhelp.compose.mutable

import fr.jhelp.compose.preference.storage.PreferenceStorageText
import fr.jhelp.compose.text.Text
import fr.jhelp.compose.text.TextString

class TextMutable(attributeName: String, initialValue: Text = TextString("")) :
        ValueMutable<Text>(attributeName, initialValue, PreferenceStorageText)
