package fr.jhelp.compose.mutable

import fr.jhelp.compose.preference.storage.PreferenceStorageDouble

class DoubleMutable(attributeName: String, initialValue: Double = 0.0) :
        ValueMutable<Double>(attributeName, initialValue, PreferenceStorageDouble)
