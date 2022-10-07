package fr.jhelp.compose.mutable

import fr.jhelp.compose.preference.storage.PreferenceStorageLong

class LongMutable(attributeName: String, initialValue: Long = 0L) :
        ValueMutable<Long>(attributeName, initialValue, PreferenceStorageLong)
