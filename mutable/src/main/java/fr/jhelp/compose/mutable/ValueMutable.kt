package fr.jhelp.compose.mutable

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.core.content.edit
import fr.jhelp.compose.preference.Preferences
import fr.jhelp.compose.preference.storage.PreferenceStorage
import java.util.concurrent.atomic.AtomicBoolean

abstract class ValueMutable<V : Any>(attributeName: String,
                                     private var defaultValue: V,
                                     private val preferenceStorage: PreferenceStorage<V>)
{
    private val initialized = AtomicBoolean(false)

    private var getValue: () -> V =
        {
            this.preferenceStorage.getPreferences(this.key,
                                                  this.defaultValue,
                                                  Preferences.sharedPreferences)
        }

    private var setValue: (V) -> Unit =
        { newValue ->
            this.defaultValue = newValue
            Preferences.sharedPreferences.edit(commit = true) {
                this@ValueMutable.preferenceStorage.putPreferences(this@ValueMutable.key,
                                                                   newValue,
                                                                   this)
            }
        }

    private val key: String

    init
    {
        val stackElement = Throwable().stackTrace[2]
        this.key = "${stackElement.className}.$attributeName".replace('.', '_')
    }

    var value: V
        get() = this.getValue()
        set(value)
        {
            this.setValue(value)
        }


    @Composable
    fun LinkToComposable()
    {
        if (this.initialized.compareAndSet(false, true))
        {
            var value: V by remember { mutableStateOf(this.getValue()) }

            this.getValue = { value }

            this.setValue =
                { newValue ->
                    value = newValue
                    this.defaultValue = newValue
                    Preferences.sharedPreferences.edit(commit = true) {
                        this@ValueMutable.preferenceStorage.putPreferences(this@ValueMutable.key,
                                                                           newValue,
                                                                           this)
                    }
                }
        }
    }

    fun destroy()
    {
        if (this.initialized.compareAndSet(true, false))
        {
            this.getValue = { this.defaultValue }
            this.setValue = { newValue -> this.defaultValue = newValue }
        }
    }
}
