import fr.jhelp.android.library.engine.extensions.tree
import fr.jhelp.android.library.engine.view.View3D

fun sceneReady(view3D: View3D)
{
    view3D.tree {
        this.root {
            this.object3D {
                this.addTriangle(0.2f, 5f, 3f,
                                 0.1f, 0.2f,
                                 8f, 4.1f, -5.2f,
                                 0.2f, 0.3f,
                                 7f, 8f, 3f,
                                 .5f, 0.9f)
            }
        }
    }
}
