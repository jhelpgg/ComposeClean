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
        val applicationContext = this.applicationContext
        provideSingle<Context> { applicationContext }
        injectModels()
    }
}