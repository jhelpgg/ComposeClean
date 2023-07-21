import fr.jhelp.android.library.animations.Animation
import fr.jhelp.android.library.engine.extensions.tree
import fr.jhelp.android.library.engine.view.View3D

fun sdkReady(view3D: View3D, animation: Animation)
{

    view3D.tree {
        this.scene3D.play(animation)
    }
}