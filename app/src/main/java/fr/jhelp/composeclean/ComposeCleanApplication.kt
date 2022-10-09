package fr.jhelp.composeclean

import android.app.Application
import android.content.Context
import fr.jhelp.compose.provider.provideSingle
import fr.jhelp.composeclean.models.injectModels

class ComposeCleanApplication: Application()
{
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