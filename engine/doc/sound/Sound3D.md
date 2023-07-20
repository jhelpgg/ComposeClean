# 3D sound

[fr.jhelp.android.library.engine.scene.Sound3D](../../src/main/java/fr/jhelp/android/library/engine/scene/Scene3D.kt)
represent a 3D sound. Tht is to say a sound that can "move" in scene, it can come form, right, left, volume change depends on distance.

To ear the 3D sound effect it needs stereo speaker or earphone plug to the device.

It can be locate alone or attached to a nodes/object, so that it's like if the node/object emits the sound.

Only one 3D sound can be play in one time to not saturate user ears. 

In standalone way, can use the methods `position` or `translate` for change sound source location.

If sound attached, the location is compute by the engine.

Control sound methods :

| Method | Description                                                             |
|:------:|:------------------------------------------------------------------------|
|  play  | Play the sound.</br>If an other sound is playing, this one is  stopped. |
| pause  | Pause the sound                                                         |
| resume | Resume the paused sound                                                 |
|  stop  | Stop the sound                                                          |

At sound creation the number of loop to do in playing can be choose. Use `-1` for "infinite" loops.

To respect the phone user, it is recommend to pause sounds while application is in pause and resume 
them when application resume. Like explains in [Sound](../../../sound/doc/SoundDocumentation.md) call
[fr.jhelp.android.library.sound.SoundManager](../../../sound/src/main/java/fr/jhelp/android/library/sound/SoundManager.kt)
`pause` method when application goes in pause and `resume` when application resume.

Example for attached sound :

Standard way :

```kotlin
import fr.jhelp.android.library.engine.scene.Sound3D
import fr.jhelp.android.library.engine.scene.geometry.Box
import fr.jhelp.android.library.engine.view.View3D

    // ...
    val sound = Sound3D(R.raw.sound_to_play)
    val box = Box()
    box.sound3D = sound

    // ...
    sound.play()
```

DSL way :

```kotlin
import fr.jhelp.android.library.engine.dsl.nodeReference
import fr.jhelp.android.library.engine.extensions.tree
import fr.jhelp.android.library.engine.scene.Sound3D
import fr.jhelp.android.library.engine.view.View3D

    // ...
    val sound = Sound3D(R.raw.sound_to_play)

    val boxReference = nodeReference()

    view3D.tree {
        this.root {
            this.box(boxReference) {
                this.sound3D = sound
                // ...
            }
        }
    }

    // ...
    sound.play()
```
