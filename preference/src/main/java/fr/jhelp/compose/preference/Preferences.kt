package fr.jhelp.compose.preference

import android.content.Context
import android.content.SharedPreferences
import android.util.Log
import fr.jhelp.compose.collection.preferences.MapSharedPreferences
import fr.jhelp.compose.provider.isProvided
import fr.jhelp.compose.provider.provided

object Preferences
{
    private val applicationContext: Context by provided<Context>()
    private val androidSharedPreference: SharedPreferences by lazy {
        this.applicationContext
            .getSharedPreferences(this.applicationContext
                                      .packageName
                                      .replace('.',
                                               '_'),
                                  Context.MODE_PRIVATE)
    }

    val sharedPreferences: SharedPreferences
        get() =
            if (isProvided<Context>())
            {
                this.androidSharedPreference
            }
            else
            {
                Log.w("Preferences",
                      "No applicationContext so use an emulated RAM preferences. Nothing will be stored.")
                MapSharedPreferences
            }
}
