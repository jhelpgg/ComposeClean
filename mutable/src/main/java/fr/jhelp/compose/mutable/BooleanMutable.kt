package fr.jhelp.compose.mutable

import fr.jhelp.compose.preference.storage.PreferenceStorageBoolean

class BooleanMutable(attributeName: String, initialValue: Boolean = false) :
        ValueMutable<Boolean>(attributeName, initialValue, PreferenceStorageBoolean)
