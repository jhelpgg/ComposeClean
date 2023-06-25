package fr.jhelp.composeclean

import android.app.Application
import android.content.Context
import androidx.annotation.MainThread
import fr.jhelp.android.library.provider.provideSingle
import fr.jhelp.composeclean.models.injectModels

/**
 * Application override to do some initialization on creation
 */
class ComposeCleanApplication: Application()
{
    /**
     * Called when application is created
     */
    @MainThread
    override fun onCreate()
    {
        super.onCreate()
        // Inject the application context
        // here a trick to be sure not embed the Application instance, but only the application context
        val applicationContext = this.applicationContext
        provideSingle<Context> { applicationContext }
        // Inject activities models
        injectModels()
    }
}