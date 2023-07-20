import fr.jhelp.android.library.engine.R
import fr.jhelp.android.library.engine.dsl.nodeReference
import fr.jhelp.android.library.engine.extensions.tree
import fr.jhelp.android.library.engine.scene.Sound3D
import fr.jhelp.android.library.engine.view.View3D

fun sceneReady(view3D: View3D)
{
    val sound = Sound3D(soundResource = R.raw.sound_to_play)

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
}


