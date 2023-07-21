# Network management

To
make  [fr.jhelp.android.library.tasks.TaskType.NETWORK](../../../src/main/java/fr/jhelp/android/library/tasks/TaskType.kt)
work,
it is mandatory to initialize the network management.
See [TaskType](../TaskType.md) to know more about `TaskType`.

To initialize the network, the application mus manage the permissions:

* `android.Manifest.permission.INTERNET`
* `android.Manifest.permission.ACCESS_NETWORK_STATE`

The application should have provide the application context. More about providing
in [Provider](../../../../provider/doc/Provider.md)

Example for providing application context (Copy
of [fr.jhelp.android.library.showcase.ComposeCleanApplication](../../../../app/src/main/java/fr/jhelp/android/library/showcase/ComposeCleanApplication.kt)) :

```kotlin
package fr.jhelp.android.library.showcase

import android.app.Application
import android.content.Context
import androidx.annotation.MainThread
import fr.jhelp.android.library.provider.provideSingle
import fr.jhelp.android.library.models.injectModels

/**
 * Application override to do some initialization on creation
 */
class ComposeCleanApplication : Application()
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
```

After application context is provided, its possible to initialize by call `initialize` method
of [fr.jhelp.android.library.tasks.network.NetworkStatusManager](../../../src/main/java/fr/jhelp/android/library/tasks/network/NetworkStatusManager.kt)

```kotlin
import fr.jhelp.android.library.tasks.network.NetworkStatusManager

// ....
// Must be called after permissions managed (If they become "dangerous") and after providing the application context
NetworkStatusManager.initialize()
```

After that the `TaskType.NETWORK` can be use
