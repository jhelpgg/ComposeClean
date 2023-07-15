import fr.jhelp.android.library.engine.extensions.tree
import fr.jhelp.android.library.engine.view.View3D

fun sceneReady(view3D: View3D)
{
    view3D.tree {
        this.root {
            this.box {
                // ...
            }
        }
    }
}
