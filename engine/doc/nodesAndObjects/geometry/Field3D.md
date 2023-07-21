# Field 3D

[fr.jhelp.android.library.engine.scene.geometry.Field3D](../../../src/main/java/fr/jhelp/android/library/engine/scene/geometry/Field3D.kt)
is a pre-build [object 3D](Object3D.md) that represents a field.

A `Field` represents an equation `Z=f(X, Y)`. 
That is to say a X, Y plane, with Z is compute by the X and Y value.

![Filed sample](engine_field.png)

The equation for compute `Z` must depends on `X` and `Y` (upper case in important).

Equation definition and pssobiliy are describe here [Formal functions](../../../../math/doc/math/formal/FormalFunction.md)

Additional to the equation to follow, have to choose `X` and `Y` limits.
Also have to give the number of part to cut `X` and `Y`.
More part are defined, more accurate is the result, but also more traingles are used.
Number triangles formula is : `Number part on X * Number part on Y * 2`.
It multiplies by two because each part is a squre and to render a square it needs two triangles.

Optionally can change the texture part draw or repetition. 
By default it use the all texture.

Standard way

```kotlin
import fr.jhelp.android.library.engine.scene.geometry.Field3D
import fr.jhelp.android.library.math.PI_FLOAT
import fr.jhelp.android.library.math.formal.X
import fr.jhelp.android.library.math.formal.Y
import fr.jhelp.android.library.math.formal.cos
import fr.jhelp.android.library.math.formal.times

// ...
    val field = Field3D(functionZ = cos(X) * cos(Y),
                        xStart = -PI_FLOAT, xEnd = PI_FLOAT, numberPartX = 10,
                        yStart = -PI_FLOAT, yEnd = PI_FLOAT, numberPartY = 10)
// ...
```

DSL way

```kotlin
import fr.jhelp.android.library.engine.extensions.tree
import fr.jhelp.android.library.engine.view.View3D
import fr.jhelp.android.library.math.PI_FLOAT
import fr.jhelp.android.library.math.formal.X
import fr.jhelp.android.library.math.formal.Y
import fr.jhelp.android.library.math.formal.cos
import fr.jhelp.android.library.math.formal.sin
import fr.jhelp.android.library.math.formal.times

// ...
    view3D.tree {
        this.root {
            this.field(
                functionZ = cos(X) * sin(Y),
                xStart = -PI_FLOAT, xEnd = PI_FLOAT, numberPartX = 10,
                yStart = -PI_FLOAT, yEnd = PI_FLOAT, numberPartY = 10) {
                // ...
            }
        }
    }
// ...
```
