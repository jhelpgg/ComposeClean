# Node hierarchy

Any object 3D extends [fr.jhelp.android.library.engine.scene.Node3D](../../src/main/java/fr/jhelp/android/library/engine/scene/Node3D.kt)
that represents a node. The class `Node3D` is not visible, be can be place in 3D Coordinate system.

Nodes (or object 3D) are organized in hierarchy, if a node (or object) is child of an other node/object is 
position (See [Position in 3D](../position/PositionIn3D.md)) is relative to the parent.
That's mean if you change parent position, you will also change also all its children. 

By example in simplified solar system with Sun, Earth and Moon, Earth will be child of Sun and Moon child of Earth.
So when Sun move, Earth and Moon move also. If Earth move, Moo,n move also.

By example see : [Solar system](https://youtu.be/k7DWdsx4Tco)

Standard way :

```kotlin
import fr.jhelp.android.library.engine.scene.LIGHT_BLUE
import fr.jhelp.android.library.engine.scene.LIGHT_GREY
import fr.jhelp.android.library.engine.scene.Scene3D
import fr.jhelp.android.library.engine.scene.YELLOW
import fr.jhelp.android.library.engine.scene.geometry.Sphere

fun sceneReady(scene3D: Scene3D)
{
    scene3D.root.position.z = -5f

    val sun = Sphere()
    sun.material.diffuse = YELLOW
    scene3D.root.add(sun)

    val earth = Sphere()
    earth.position.scale(0.4f)
    earth.position.y = 2f
    earth.material.diffuse = LIGHT_BLUE
    sun.add(earth)

    val moon = Sphere()
    moon.position.scale(0.4f)
    moon.position.y = 2f
    moon.material.diffuse = LIGHT_GREY
    earth.add(moon)
}
```

DSL way :

```kotlin
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import fr.jhelp.android.library.engine.dsl.nodeReferences
import fr.jhelp.android.library.engine.extensions.children
import fr.jhelp.android.library.engine.extensions.position
import fr.jhelp.android.library.engine.scene.BLACK
import fr.jhelp.android.library.engine.scene.LIGHT_BLUE
import fr.jhelp.android.library.engine.scene.LIGHT_GREY
import fr.jhelp.android.library.engine.scene.YELLOW
import fr.jhelp.android.library.showcase.ui.engine.View3DComposable

/**
 * Show the simplified solar system
 */
class EngineSolarSystemComposable
{
    companion object
    {
        private const val NUMBER_TURN = 32
    }

    /**
     * Show the simplified solar system
     */
    @Composable
    fun Show()
    {
        val view3D = View3DComposable()
        view3D.Draw(modifier = Modifier.fillMaxSize()) {
            this.backgroundColor = BLACK
            val (sun, earth, moon) = nodeReferences()
            this.scenePosition { this.z = -5f }
            this.root {
                this.sphere(sun, slice = 32, slack = 32) {
                    this.material.diffuse = YELLOW

                    this.children {
                        this.sphere(earth, slice = 32, slack = 32) {
                            this.material.diffuse = LIGHT_BLUE
                            this.position {
                                this.scale(0.4f)
                                this.y = 2f
                            }

                            this.children {
                                this.sphere(moon, slice = 32, slack = 32) {
                                    this.material.diffuse = LIGHT_GREY
                                    this.position {
                                        this.scale(0.4f)
                                        this.y = 2f
                                    }
                                }
                            }
                        }
                    }
                }
            }
       }
    }
}
```

The `root` is the main node for put all the scene. 
We put it in front of the scene so we can take care about the scene itself.

Remember the position is relative to parent. Earth size is 40% or Sun size and Moon size is 40% of Earth, so Moon size is 16% of Sun size.

