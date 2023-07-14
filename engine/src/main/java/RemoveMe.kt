import fr.jhelp.android.library.engine.dsl.nodeReferences
import fr.jhelp.android.library.engine.extensions.tree
import fr.jhelp.android.library.engine.view.View3D

fun sceneReady(view3D: View3D)
{
    view3D.tree {
        this.root {
            val (plane, plane2) = nodeReferences()
            this.plane(plane) {
                // ..
            }

            this.plane(plane2,
                       startU = 0f, endU = 2f, // Width repeat two times
                       startV = 0.25f, endV = 0.75f) // Height's middle half
            {
                // ...
            }
        }
    }
}
