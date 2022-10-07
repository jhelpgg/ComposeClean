package fr.jhelp.compose.collection.preferences

import android.content.SharedPreferences

object MapSharedPreferences : SharedPreferences
{
    private val map = HashMap<String, Any>()
    private val editor = MapEditor(this.map)

    override fun getAll(): MutableMap<String, *> = this.map

    override fun getString(key: String, defValue: String?): String? =
        this.map[key] as? String ?: defValue

    override fun getStringSet(key: String, defValues: MutableSet<String>?): MutableSet<String>? =
        (this.map[key] as? MutableSet<*> ?: defValues) as MutableSet<String>?

    override fun getInt(key: String, defValue: Int): Int =
        this.map[key] as? Int ?: defValue

    override fun getLong(key: String, defValue: Long): Long =
        this.map[key] as? Long ?: defValue

    override fun getFloat(key: String, defValue: Float): Float =
        this.map[key] as? Float ?: defValue

    override fun getBoolean(key: String, defValue: Boolean): Boolean =
        this.map[key] as? Boolean ?: defValue

    override fun contains(key: String): Boolean = key in this.map

    override fun edit(): SharedPreferences.Editor = this.editor

    override fun registerOnSharedPreferenceChangeListener(listener: SharedPreferences.OnSharedPreferenceChangeListener) =
        Unit

    override fun unregisterOnSharedPreferenceChangeListener(listener: SharedPreferences.OnSharedPreferenceChangeListener) =
        Unit
}
