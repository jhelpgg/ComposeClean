import fr.jhelp.android.library.engine.dsl.nodeReferences
import fr.jhelp.android.library.engine.extensions.position
import fr.jhelp.android.library.engine.extensions.tree
import fr.jhelp.android.library.engine.view.View3D

fun sceneReady(view3D: View3D)
{
    view3D.tree {
        this.root {
            val (referenceObject, referenceClone, referenceClone2, referenceCloneClone) = nodeReferences()
            this.object3D(referenceObject) {
                // ...
            }

            this.clone(referenceClone, referenceObject) {
                this.position {
                    // ....
                }
            }

            this.clone(referenceClone2, referenceObject) {
                this.position {
                    // ....
                }
            }

            this.clone(referenceCloneClone, referenceClone) {
                this.position {
                    // ....
                }
            }
        }
    }
}
