package fr.jhelp.compose.mutable

import fr.jhelp.compose.preference.storage.PreferenceStorageInt

class IntMutable(attributeName: String, initialValue: Int = 0) :
        ValueMutable<Int>(attributeName, initialValue, PreferenceStorageInt)
