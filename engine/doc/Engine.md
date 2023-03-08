# 3D engine for Android

`fr.jhelp.compose.engine.view.View3D` is a view where 3D scene can be draw on it.

It can be used and placed as any other graphics component like `Button`, `TextView`, `ImageView`,
...

For composable, `fr.jhelp.compose.ui.engine.View3DComposable` from `app` module can be used.

For create a scene you can use traditional way or our **DSL** layer.

**Note** : For `fr.jhelp.compose.ui.engine.View3DComposable` only the **DSL** way is available

To add element oon the 3D it it recommended to do i outside the Main thread.

Traditional way to manipulate the scene embed :

`````kotlin
import fr.jhelp.compose.engine.view.View3D
import fr.jhelp.compose.engine.scene.Scene3D

// ...

class MyActivity : AppCaompatActivity()
{
    private val scope = CoroutineScope(Dispatchers.Default)

    override fun onCreate(bundle: Bundle?)
    {
        // ...
        val view3D : View3D = this.findViewById<View3D>(R.id.view3D)
        this.scope.launch { this@MyActivity.drawScene(view3D.scene3D) }
    }

    private fun drawScene(scene: Scene3D)
    {
        // Fill the scene
    }
}
`````

**DSL** way : 

`````kotlin
import fr.jhelp.compose.engine.view.View3D
import fr.jhelp.compose.engine.scene.Scene3D
import fr.jhelp.compose.engine.extensions.tree
// ...

class MyActivity : AppCaompatActivity()
{
    override fun onCreate(bundle: Bundle?)
    {
        // ...
        val view3D: View3D = this.findViewById<View3D>(R.id.view3D)
        view3D.tree {
            // Describe scene here           
        }
    }
}

`````

**Note** : The `tree` extension call the code in the lamda in a separate thread, so no need to use a scope explicitly.

## The scene

`fr.jhelp.compose.engine.scene.Scene3D` describes the scene. 

The background color cn be changed with the `bckground`field.

