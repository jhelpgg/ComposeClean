package fr.jhelp.compose.collection.preferences

import android.content.SharedPreferences

internal class MapEditor(private val map: HashMap<String, Any>) : SharedPreferences.Editor {
    override fun putString(key: String, value: String?): SharedPreferences.Editor {
        if (value == null) {
            this.map.remove(key)
        } else {
            this.map[key] = value
        }

        return this
    }

    override fun putStringSet(key: String, values: MutableSet<String>?): SharedPreferences.Editor {
        if (values == null) {
            this.map.remove(key)
        } else {
            this.map[key] = values
        }

        return this
    }

    override fun putInt(key: String, value: Int): SharedPreferences.Editor {
        this.map[key] = value
        return this
    }

    override fun putLong(key: String, value: Long): SharedPreferences.Editor {
        this.map[key] = value
        return this
    }

    override fun putFloat(key: String, value: Float): SharedPreferences.Editor {
        this.map[key] = value
        return this
    }

    override fun putBoolean(key: String, value: Boolean): SharedPreferences.Editor {
        this.map[key] = value
        return this
    }

    override fun remove(key: String): SharedPreferences.Editor {
        this.map.remove(key)
        return this
    }

    override fun clear(): SharedPreferences.Editor {
        this.map.clear()
        return this
    }

    override fun commit(): Boolean = true

    override fun apply() = Unit
}