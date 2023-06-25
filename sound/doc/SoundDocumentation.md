# Playing sounds

The aim is to play sounds easily. 
We manage 3 type of sounds :
* Sound play in background on loop
* Sound play once like effect in video game or alert in application
* Sound can control volume at any time

Each time can play one sound in a time, but the three can be played in same times.

They are managed by [fr.jhelp.android.library.sound.SoundManager](../src/main/java/fr/jhelp/android/library/sound/SoundManager.kt).

The manager work if application is provided.

More about providing in [Provider](../../provider/doc/Provider.md)

Example for providing application context (Copy
of [fr.jhelp.android.library.showcase.ComposeCleanApplication](../../app/src/main/java/fr/jhelp/android/library/showcase/ComposeCleanApplication.kt)) :

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

It is highly recommended to pause all sounds when application is pause (to not disturb a call, for example).
And resume the sound when application comes back on foreground.

To do this on your activity `onPause` method, call the `pause()` methode and in `onResume` call `resume()` method.

```kotlin
import fr.jhelp.android.library.sound.SoundManager
// ...

   /**
     * Called when application is paused
     */
    override fun onPause()
    {
        SoundManager.pause()
        // ...
        super.onPause()
    }

    /**
     * Called when application resumed
     */
    override fun onResume()
    {
        super.onResume()
        SoundManager.resume()
        // ...
    }

```

To stop all sounds properly, use `stopSounds` method.

To play a sound in `background` mode, that is to say repeat in "infinite" loop, call the `background` method.
If a sound actually played in `background` mode, it stops and replaces by the given one.

To play a sound in `effect` mode, that is to say play once, call the `effect` method.
If a sound actually played in `effect` mode, it stops and replaces by the given one.

The third sound type is managed by [fr.jhelp.android.library.sound.Sound](../src/main/java/fr/jhelp/android/library/sound/Sound.kt).

At creation its possible to choose the left and right side volume factor and the number of loop.
By default, the volume rate are at 100% of the current volume defines on device. and no loop, that is to say the sound play only once.

The left and right volume rate can b change at any time with `setVolume` method.

The sound can be play, pause resume and stop with respective methods.

Just remember only one `Sound` can be play in same time, that means if a sound is playing while `play`is called, 
the current sound stops and replace by the new one.
