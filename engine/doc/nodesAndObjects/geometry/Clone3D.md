# Clone 3D

The aim
of [fr.jhelp.android.library.engine.scene.Clone3D](../../../src/main/java/fr/jhelp/android/library/engine/scene/Clone3D.kt)
is to save memory. If you need to use an object with same mesh two times (or more), use a clone.

The clone have it's own material (See [Material](../../material/Material.md))
and position (See [Position in 3D](../../position/PositionIn3D.md)).

At construction just specify the object to clone.
Its position to clone a clone. In reality it will clone the object referenced by the cloned clone

Standard way

```kotlin
import fr.jhelp.android.library.engine.scene.Object3D
import fr.jhelp.android.library.engine.scene.Clone3D

// ...
val object3D = Object3D()
// ...
val clone = Clone3D(object3D)
// ...
val clone2 = Clone3D(object3D)
// ...
val cloneClone = Clone3D(clone)
// ...
```

DSL way

```kotlin
import fr.jhelp.android.library.engine.dsl.nodeReferences
import fr.jhelp.android.library.engine.extensions.position
import fr.jhelp.android.library.engine.extensions.tree
import fr.jhelp.android.library.engine.view.View3D

// ...
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
// ...
```
