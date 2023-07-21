# Plane

A [fr.jhelp.android.library.engine.scene.geometry.Plane](../../../src/main/java/fr/jhelp/android/library/engine/scene/geometry/Plane.kt)
is a pre-build [Object 3D](Object3D.md) that represents a plane.

The plane is a square in space (may look rectangle on change scale).

By default all the texture is draw on it, can change it with change UV specification.

Standard way

```kotlin
import fr.jhelp.android.library.engine.scene.geometry.Plane

// ...
// Default texture take all plane
val plane = Plane()
// ...
// Plane with repeat the width 2 times and take only the middle half of height
val plane2 = Plane(startU = 0f, endU = 2f, // Width repeat two times
                   startV = 0.25f, endV = 0.75f) // Height's middle half
// ...
```

DSL way

```kotlin
import fr.jhelp.android.library.engine.dsl.nodeReferences
import fr.jhelp.android.library.engine.extensions.tree
import fr.jhelp.android.library.engine.view.View3D

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
```
