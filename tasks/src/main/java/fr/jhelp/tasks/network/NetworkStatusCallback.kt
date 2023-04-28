package fr.jhelp.tasks.network

import android.net.ConnectivityManager
import android.net.Network
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

/**
 * React to Network status changed
 */
internal object NetworkStatusCallback : ConnectivityManager.NetworkCallback()
{
    private val availableMutableFlow = MutableStateFlow<Boolean>(false)
    val availableFlow: StateFlow<Boolean> = this.availableMutableFlow.asStateFlow()

    override fun onAvailable(network: Network)
    {
        this.availableMutableFlow.value = true
    }

    override fun onLost(network: Network)
    {
        this.availableMutableFlow.value = false
    }
}