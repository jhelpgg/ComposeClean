package fr.jhelp.compose.mutable

import fr.jhelp.compose.preference.storage.PreferenceStorageFloat

class FloatMutable(attributeName: String, initialValue: Float = 0f) :
        ValueMutable<Float>(attributeName, initialValue, PreferenceStorageFloat)
