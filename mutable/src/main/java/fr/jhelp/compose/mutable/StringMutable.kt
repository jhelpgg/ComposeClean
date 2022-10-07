package fr.jhelp.compose.mutable

import fr.jhelp.compose.preference.storage.PreferenceStorageString

class StringMutable(attributeName: String, initialValue: String = "") :
        ValueMutable<String>(attributeName, initialValue, PreferenceStorageString)
